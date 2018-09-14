/**    
* @Title DLockRetry.java  
* @Package com.sfbest.security.oauth.distribute.lock  
* @author 徐故成   
* @date 2017年12月20日 下午4:38:11  
* @version V1.0    
*/
package com.gzbest.platform.open.api.security.dlock;

/**
 * @ClassName DLockRetry
 * @author 徐故成
 * @date 2017年12月20日 下午4:38:11
 * 
 */
public interface DLockRetry {
	public void retry() throws InterruptedException;
}
