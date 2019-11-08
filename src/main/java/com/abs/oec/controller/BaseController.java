/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abs.oec.common.Constants;
import com.abs.oec.dao.model.AuthCodeDetails;
import com.abs.oec.model.AuthorizationDetails;
import com.abs.oec.model.Response;
import com.abs.oec.repository.AuthCodeRepository;

public abstract class BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
	
	//=========================================================================
	
	public AuthorizationDetails validateAuthorization(AuthCodeRepository authCodeRepository, String authCode) {
		String logTag = "validateAuthorization() ";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = new AuthorizationDetails();
		
		try {
			List<AuthCodeDetails> authCodes = authCodeRepository.getAuthCodeDetailsByAuthCode(authCode); 
			if(authCodes != null && !authCodes.isEmpty()) { 
				if(authCodes.size() > 1) {
					//TODO: Need to throw and error as multiple entries having same authCode
				} else {
					AuthCodeDetails authCodeDetails = authCodes.get(0);
					LOGGER.info(logTag + "AuthCode: " + authCodeDetails.getAuthCode());
					authorizationDetails.setAuthCode(authCode);
					authorizationDetails.setUserDetailsId(authCodeDetails.getUserDetails().getUserDetailsId()); 
					
					if(Constants.ACTIVE.equalsIgnoreCase(authCodeDetails.getStatus())) {
						authorizationDetails.setValidAuthCode(true);
						
						//TODO: Need to check whether the user is valid authorization to access this api
						authorizationDetails.setValidAccess(true); //This is temporary
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return authorizationDetails;
	}
	
	//=========================================================================
	
	public Response getInvalidAuthCodeRespose(String authCode) {
		return new Response("Invalid AuthCode ["+authCode+"]", null);
	}
	
	//=========================================================================
	
	public Response getUnAuthorizedAccessRespose() {
		return new Response("Unauthorized Access", null);
	}
	
	//=========================================================================
	
	public Response getInvalidInputRespose() {
		return new Response("Invalid Input", null);
	}
	
	//=========================================================================
}
