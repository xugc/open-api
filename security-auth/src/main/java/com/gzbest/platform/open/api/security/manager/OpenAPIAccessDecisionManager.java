package com.gzbest.platform.open.api.security.manager;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * @ClassName OpenAPIAccessDecisionManager
 * @author 徐故成
 * @date 2017年11月29日 下午7:37:22
 * 
 */
public class OpenAPIAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) {
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
			Set<String> scopes = oauth2Authentication.getOAuth2Request().getScope();// client
																					// scopes
			for (ConfigAttribute ca : configAttributes) {// request scopes
				String requestScope = ca.getAttribute();
				if (scopes.contains(requestScope))
					return;
			}
			throw new AccessDeniedException("该资源没有授权给当前用户");
		}
		throw new InsufficientAuthenticationException("认证信息无效,非OAuth2认证信息");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
