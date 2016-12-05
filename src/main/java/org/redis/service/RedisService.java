package org.redis.service;

import java.util.Set;

public interface RedisService {

	/**
     * 通过key删除
     * 
     * @param key
     */
    public abstract long del(String... keys);
    
    /**
     * 通过key删除
     * @param keys
     * @return
     */
    public abstract long del(byte[] key);

    /**
     * 添加key value 并且设置存活时间(byte)
     * 
     * @param key
     * @param value
     * @param liveTime
     */
    public abstract void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加key value 并且设置存活时间
     * 
     * @param key
     * @param value
     * @param liveTime
     *            单位秒
     */
    /*public abstract void set(String key, String value, long liveTime);*/

    /**
     * 添加key value
     * 
     * @param key
     * @param value
     */
    /*public abstract void set(String key, String value);*/

    /**
     * 添加key value (字节)(序列化)
     * 
     * @param key
     * @param value
     */
    public abstract void set(byte[] key, byte[] value);
    
    /**
     * 获取系列化的 value
     * @param key
     * @return
     */
    public abstract byte[] get(byte[] key);

    /**
     * 获取redis value (String)
     * 
     * @param key
     * @return
     */
  /* public abstract String get(String key);*/
    
    /**
     * 通过key查询（模糊查询）
     * @param pattern
     * @return
     */
    public Set<byte[]> keys(String pattern);

    /**
     * 通过正则匹配keys
     * 
     * @param pattern
     * @return 
     * @return 
     * @return
     */
    public abstract void Setkeys(String pattern);

    /**
     * 检查key是否已经存在
     * 
     * @param key
     * @return
     */
    public abstract boolean exists(String key);

    /**
     * 清空redis 所有数据
     * 
     * @return
     */
    public abstract String flushDB();

    /**
     * 查看redis里有多少数据
     */
    public abstract long dbSize();

    /**
     * 检查是否连接成功
     * 
     * @return
     */
    public abstract String ping();
    
}