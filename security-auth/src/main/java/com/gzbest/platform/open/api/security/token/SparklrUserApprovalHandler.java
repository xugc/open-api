///*
// * Copyright 2002-2011 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.gzbest.platform.open.api.security.token;
//
//import java.util.Collection;
//
//import org.apache.log4j.Logger;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.provider.AuthorizationRequest;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.ClientRegistrationException;
//import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
//
///**
// * @author Dave Syer
// * 
// */
//public class SparklrUserApprovalHandler extends ApprovalStoreUserApprovalHandler {
//	private static final Logger logger = Logger.getLogger(SparklrUserApprovalHandler.class);
//	private boolean useApprovalStore = true;
//
//	private ClientDetailsService clientDetailsService;
//
//	/**
//	 * Service to load client details (optional) for auto approval checks.
//	 * 
//	 * @param clientDetailsService
//	 *            a client details service
//	 */
//	@Override
//	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
//		this.clientDetailsService = clientDetailsService;
//		super.setClientDetailsService(clientDetailsService);
//	}
//
//	/**
//	 * @param useApprovalStore
//	 *            the useTokenServices to set
//	 */
//	public void setUseApprovalStore(boolean useApprovalStore) {
//		this.useApprovalStore = useApprovalStore;
//	}
//
//	/**
//	 * Allows automatic approval for a white list of clients in the implicit
//	 * grant case.
//	 * 
//	 * @param authorizationRequest
//	 *            The authorization request.
//	 * @param userAuthentication
//	 *            the current user authentication
//	 * 
//	 * @return An updated request if it has already been approved by the current
//	 *         user.
//	 */
//	@Override
//	public AuthorizationRequest checkForPreApproval(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
//		AuthorizationRequest authRequest = authorizationRequest;
//		boolean approved = false;
//		// If we are allowed to check existing approvals this will short circuit
//		// the decision
//		if (useApprovalStore) {
//			authRequest = super.checkForPreApproval(authRequest, userAuthentication);
//			approved = authRequest.isApproved();
//		} else {
//			if (clientDetailsService != null) {
//				Collection<String> requestedScopes = authRequest.getScope();
//				try {
//					ClientDetails client = clientDetailsService.loadClientByClientId(authRequest.getClientId());
//					for (String scope : requestedScopes) {
//						if (client.isAutoApprove(scope)) {
//							approved = true;
//							break;
//						}
//					}
//				} catch (ClientRegistrationException e) {
//					logger.error(e);
//				}
//			}
//		}
//		authRequest.setApproved(approved);
//		return authRequest;
//
//	}
//
//}
