///**    
//* @Title OpenAPIPubSub.java  
//* @Package com.sfbest.security.oauth.distribute.lock  
//* @author 徐故成   
//* @date 2017年12月20日 下午3:11:11  
//* @version V1.0    
//*/
//package com.gzbest.platform.open.api.security.dlock;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPubSub;
//
///**
// * @ClassName OpenAPIPubSub
// * @author 徐故成
// * @date 2017年12月20日 下午3:11:11
// * 
// */
//public class DLockPubSub extends JedisPubSub implements Runnable {
//	private static final String DEFAULT_HOST = "localhost";
//	private static final int DEFAULT_PORT = 26379;
//	private static final int DEFAULT_DB = 0;
//	private static final int DEFAULT_REDISTIMEOUT = 60000;
//	private static final String DEFAULT_AUTH = "";
//	private DLock lock;
//	private Jedis jedis;
//	private String key;
//
//	public DLockPubSub(DLock lock, String key) {
//		this(DEFAULT_HOST, DEFAULT_PORT, DEFAULT_REDISTIMEOUT, DEFAULT_AUTH, DEFAULT_DB, lock, key);
//	}
//
//	public DLockPubSub(String host, String auth, Integer dbIndex, DLock lock, String key) {
//		this(host, DEFAULT_PORT, DEFAULT_REDISTIMEOUT, auth, dbIndex, lock, key);
//	}
//
//	public DLockPubSub(String host, int port, int timeout, String auth, Integer dbIndex, DLock lock, String key) {
//		this.lock = lock;
//		this.key = key;
//		this.jedis = new Jedis(host, port, timeout);
//		jedis.connect();
//		jedis.auth(auth);
//		jedis.select(dbIndex);
//	}
//
//	@Override
//	public void onMessage(String channel, String message) {
//		final String tmpMsg = message;
//		if (DistributedLock.RELEASED.equals(tmpMsg)) {
//			synchronized (lock) {
//				lock.release();
//				lock.notifyAll();
//			}
//		}
//	}
//
//	@Override
//	public void run() {
//		jedis.subscribe(this, key);
//	}
//
//	/**
//	 * @return 获取 key
//	 */
//	public String getKey() {
//		return key;
//	}
//
//	/**
//	 * @param key
//	 *            设置 key
//	 */
//	public void setKey(String key) {
//		this.key = key;
//	}
//
//	/**
//	 * @return 获取 jedis
//	 */
//	public Jedis getJedis() {
//		return jedis;
//	}
//
//	/**
//	 * @param jedis
//	 *            设置 jedis
//	 */
//	public void setJedis(Jedis jedis) {
//		this.jedis = jedis;
//	}
//
//}
