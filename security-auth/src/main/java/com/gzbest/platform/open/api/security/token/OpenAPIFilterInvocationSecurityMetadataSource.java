///**    
//* @Title OpenAPIFilterInvocationSecurityMetadataSource.java  
//* @Package com.sfbest.security.oauth.security  
//* @author 徐故成   
//* @date 2017年11月29日 下午7:35:15  
//* @version V1.0    
//*/
//package com.gzbest.platform.open.api.security.token;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.util.Assert;
//
//import com.sfbest.security.oauth.entity.OauthUrlInfo;
//import com.sfbest.security.oauth.entity.OauthUrlScope;
//import com.sfbest.security.oauth.service.ResourceUrlService;
//
///**
// * @ClassName OpenAPIFilterInvocationSecurityMetadataSource
// * @author 徐故成
// * @date 2017年11月29日 下午7:35:15
// * 
// */
//public class OpenAPIFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//
//	private ResourceUrlService resourceUrlService;
//
//	private static final Map<RequestMatcher, Collection<ConfigAttribute>> authMap = new HashMap<>();
//
//	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
//	private static final Lock readLock = readWriteLock.readLock();
//	private static final Lock writeLock = readWriteLock.writeLock();
//
//	public OpenAPIFilterInvocationSecurityMetadataSource(ResourceUrlService resourceUrlService) {
//		Assert.isTrue(resourceUrlService != null, "resourceService cannot be null");
//		this.resourceUrlService = resourceUrlService;
//		initAuth();
//	}
//
//	private void initAuth() {
//		List<OauthUrlInfo> ourlInfos = resourceUrlService.getOauthUrls();
//		initAuthMap(ourlInfos);
//	}
//
//	public static void rebuildAuthMap(List<OauthUrlInfo> oauths) {
//		initAuthMap(oauths);
//	}
//
//	private static void initAuthMap(List<OauthUrlInfo> ourlInfos) {
//		writeLock.lock();
//		try {
//			authMap.clear();
//			for (OauthUrlInfo oui : ourlInfos) {
//				String path = oui.getResourceUrl();
//				AntPathRequestMatcher matcher = new AntPathRequestMatcher(path);
//				List<OauthUrlScope> scopes = oui.getScopes();
//				List<String> scopeList = new ArrayList<>();
//				for (OauthUrlScope scope : scopes) {
//					scopeList.add(scope.getOauthScope());
//				}
//				String[] configAttrs = new String[] {};
//				List<ConfigAttribute> configAttrList = SecurityConfig.createList(scopeList.toArray(configAttrs));
//				authMap.put(matcher, configAttrList);
//			}
//		} finally {
//			writeLock.unlock();
//		}
//	}
//
//	@Override
//	public Collection<ConfigAttribute> getAttributes(Object object) {
//		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
//		Collection<ConfigAttribute> requestConfigAttribute = new LinkedList<>();
//		readLock.lock();
//		try {
//			for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : authMap.entrySet()) {
//				if (entry.getKey().matches(request)) {
//					requestConfigAttribute.addAll(entry.getValue());
//				}
//			}
//		} finally {
//			readLock.unlock();
//		}
//		return requestConfigAttribute;
//	}
//
//	@Override
//	public Collection<ConfigAttribute> getAllConfigAttributes() {
//		Collection<ConfigAttribute> allConfigAttribute = new LinkedList<>();
//		readLock.lock();
//		try {
//			for (Collection<ConfigAttribute> col : authMap.values()) {
//				allConfigAttribute.addAll(col);
//			}
//		} finally {
//			readLock.unlock();
//		}
//		return allConfigAttribute;
//	}
//
//	@Override
//	public boolean supports(Class<?> clazz) {
//		return FilterInvocation.class.isAssignableFrom(clazz);
//	}
//
//}
