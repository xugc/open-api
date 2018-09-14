//package com.gzbest.platform.open.api.security.dlock;
//
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
//
///**
// * redis实现的分布式锁
// * 
// */
//public class DistributedLock implements DLock {
//
//	private static final Logger logger = LogManager.getLogger(DistributedLock.class);
//	/**
//	 * @Fields DEFAULT_HOST
//	 */
//	private static final String DEFAULT_HOST = "localhost";
//	private static final int DEFAULT_PORT = 26379;
//	private static final int DEFAULT_REDISTIMEOUT = 60000;
//	private static final String DEFAULT_AUTH = "";
//	private static final int DEFAULT_TIMEOUT = 10;
//	private static final int DEFAULT_DB = 0;
//	public static final String LOCKED = "1";
//	public static final String RELEASED = "0";
//	private Jedis jedis = null;
//	private int timeout = DEFAULT_TIMEOUT;
//	private String key;
//	private DLockRetry retry = new DefaultDLockRetry();
//	private DLockPubSub openApiListener;
//	private AtomicBoolean state = new AtomicBoolean(true);
//
//	public DistributedLock(String key) {
//		this(DEFAULT_HOST, DEFAULT_PORT, DEFAULT_REDISTIMEOUT, DEFAULT_AUTH, DEFAULT_DB, key);
//	}
//
//	public DistributedLock(String host, String auth, String key) {
//		this(host, DEFAULT_PORT, DEFAULT_REDISTIMEOUT, auth, DEFAULT_DB, key);
//	}
//
//	public DistributedLock(String host, String auth, Integer dbIndex, String key) {
//		this(host, DEFAULT_PORT, DEFAULT_REDISTIMEOUT, auth, dbIndex, key);
//	}
//
//	public DistributedLock(String host, Integer port, String auth, Integer dbIndex, String key) {
//		this(host, port, DEFAULT_REDISTIMEOUT, auth, dbIndex, key);
//	}
//
//	public DistributedLock(String host, int port, int timeout, String auth, Integer dbIndex, String key) {
//		this.key = key;
//		this.jedis = new Jedis(host, port, timeout);
//		jedis.connect();
//		jedis.auth(auth);
//		jedis.select(dbIndex);
//		openApiListener = new DLockPubSub(host, port, timeout, auth, dbIndex, this, key);
//		new Thread(openApiListener).start();
//	}
//
//	/**
//	 * @return 获取 retry
//	 */
//	public DLockRetry getRetry() {
//		return retry;
//	}
//
//	/**
//	 * @param retry
//	 *            设置 retry
//	 */
//	public void setRetry(DLockRetry retry) {
//		this.retry = retry;
//	}
//
//	@Override
//	public boolean release() {
//		return state.getAndSet(false);
//	}
//
//	@Override
//	public void lock() throws InterruptedException {
//		if (jedis.setnx(key, key) == 1) {
//			jedis.expire(key, timeout);
//			jedis.publish(key, LOCKED);
//		} else {
//			try {
//				synchronized (this) {
//					while (state.get())
//						wait(60000);
//				}
//				retry.retry();
//			} catch (InterruptedException e) {
//				logger.error(e);
//				throw e;
//			}
//		}
//	}
//
//	@Override
//	public void unlock() {
//		try {
//			if (openApiListener != null)
//				openApiListener.unsubscribe(key);
//		} catch (Exception e) {
//			logger.error(e);
//		} finally {
//			if (openApiListener != null) {
//				Jedis pubSubJedis = openApiListener.getJedis();
//				if (pubSubJedis != null)
//					pubSubJedis.close();
//			}
//		}
//		jedis.del(key);
//		jedis.publish(key, RELEASED);
//		jedis.close();
//	}
//
//	private class DefaultDLockRetry implements DLockRetry {
//
//		@Override
//		public void retry() throws InterruptedException {
//			state.getAndSet(true);
//			lock();
//		}
//
//	}
//
//}