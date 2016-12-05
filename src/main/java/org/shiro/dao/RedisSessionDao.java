package org.shiro.dao;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.redis.service.RedisService;
import org.shiro.util.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSessionDao extends CachingSessionDAO {

	private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);
	
	// 登录成功的信息存储在 session 的这个 attribute 里.
    private static final String AUTHENTICATED_SESSION_KEY = "org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY";

    private String keyPrefix = "shiro_redis_session:";
    
    private String deleteChannel = "shiro_redis_session:delete";
    
    //20分钟,单位是秒
    private long timeToLiveSeconds = 1200; 
    
    //Redis
    private RedisService template;
    
	@Override
	protected Serializable doCreate(Session session) {
		logger.debug("=> Create session with ID [{}]",session.getId());
		
		//创建一个Id并设置给session
		Serializable sessionId = this.generateSessionId(session);
		assignSessionId(session, sessionId);
		
		//session 由 Redis 缓存失效决定
		String key = SerializationUtils.sessionKey(keyPrefix, session);
		String value = SerializationUtils.sessionFromString(session);
		
		if(!template.equals(key)){
			template.set(key.getBytes(), value.getBytes(), timeToLiveSeconds);
		}
		return sessionId;
	}
	
	/**
     * 决定从本地 Cache 还是从 Redis 读取 Session.
     * @param sessionId
     * @return
     * @throws UnknownSessionException
     */
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        //Session s = getCachedSession(sessionId);
        
    	Session s = null;
        // 1. 如果本地缓存没有，则从 Redis 读取。
        // 2. ServerA 登录了，ServerB 没有登录但缓存里有此 session，所以从 Redis 读取而不是直接用缓存里的
        if (s == null || (
                s.getAttribute(AUTHENTICATED_SESSION_KEY) != null
                && !(Boolean) s.getAttribute(AUTHENTICATED_SESSION_KEY)
        )) {
            s = doReadSession(sessionId);
            if (s == null) {
                throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
            }
        }
        return s;
    }
    
    @Override
	protected Session doReadSession(Serializable sessionId) {
		logger.debug("=> Read session with ID [{}]",sessionId);
    	
		String value = new String(template.get(SerializationUtils.sessionKey(keyPrefix, sessionId).getBytes()));
		
		//例如 Redis 调用 flushdb 情况下所有的数据，读到的session就是空的
    	if(value != null){
    		Session session = (Session) SerializationUtils.sessionIdFromString(value);
    		super.cache(session, session.getId());
            return session;
    	} 
    	return null;
	}
    
	@Override
	protected void doUpdate(Session session) {
		// 如果会话过期/停止，没必要再更新了
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            logger.debug("=> Invalid session.");
            return;
        }
        logger.debug("=> Update session with ID [{}]", session.getId());

        String key = SerializationUtils.sessionKey(keyPrefix, session);
        String value = SerializationUtils.sessionFromString(session);
        template.set(key.getBytes(),value.getBytes(),timeToLiveSeconds);
	}

	@Override
	protected void doDelete(Session session) {
		logger.debug("=> Delete session with ID [{}]", session.getId());
		template.del(SerializationUtils.sessionKey(keyPrefix, session));
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public String getDeleteChannel() {
		return deleteChannel;
	}

	public void setDeleteChannel(String deleteChannel) {
		this.deleteChannel = deleteChannel;
	}

	public long getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public void setTimeToLiveSeconds(long timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	public RedisService getTemplate() {
		return template;
	}

	public void setTemplate(RedisService template) {
		this.template = template;
	}
	
}
