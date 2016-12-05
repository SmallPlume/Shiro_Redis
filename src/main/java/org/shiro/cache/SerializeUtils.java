package org.shiro.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtils {
	
private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);
	
	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		
		Object result = null;
		
		if (isEmpty(bytes)) {
			return null;
		}

		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
				try {
					result = objectInputStream.readObject();
				}
				catch (ClassNotFoundException ex) {
					throw new Exception("对象序列化失败：", ex);
				}
			}
			catch (Throwable ex) {
				throw new Exception("序列化失败：", ex);
			}
		} catch (Exception e) {
			logger.error("序列化失败：",e);
		}
		return result;
	}
	
	public static boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		
		byte[] result = null;
		
		if (object == null) {
			return new byte[0];
		}
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
			try  {
				if (!(object instanceof Serializable)) {
					throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " 需要一个可串行化的有效载荷 " +
							"但收到一个类型的对象 [" + object.getClass().getName() + "]");
				}
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
				objectOutputStream.writeObject(object);
				objectOutputStream.flush();
				result =  byteStream.toByteArray();
			}
			catch (Throwable ex) {
				throw new Exception("序列化失败：", ex);
			}
		} catch (Exception ex) {
			logger.error("序列化失败：",ex);
		}
		return result;
	}

}
