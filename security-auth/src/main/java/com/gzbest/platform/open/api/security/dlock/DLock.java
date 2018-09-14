/**    
* @Title OpenAPIDLock.java  
* @Package com.sfbest.security.oauth.distribute.lock  
* @author 徐故成   
* @date 2017年12月5日 上午10:16:55  
* @version V1.0    
*/
package com.gzbest.platform.open.api.security.dlock;

/**
 * @ClassName OpenAPIDLock
 * @author 徐故成
 * @date 2017年12月5日 上午10:16:55
 * 
 */
public interface DLock {
	public void lock() throws InterruptedException;

	public void unlock();

	public boolean release();
}
