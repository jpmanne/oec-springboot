/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.abs.oec.common.Constants;
import com.abs.oec.dao.model.AuthCodeDetails;
import com.abs.oec.exception.OECException;
import com.abs.oec.model.AuthorizationDetails;
import com.abs.oec.model.Response;
import com.abs.oec.repository.AuthCodeRepository;
import com.abs.oec.repository.ExceptionRepository;

@RestController
public abstract class BaseController {
	
	@Autowired
	AuthCodeRepository authCodeRepository;
	
	@Autowired
	ExceptionRepository exceptionRepository;
	
	//=========================================================================
	
	public AuthorizationDetails validateAuthorization(String authCode) {
		String logTag = "validateAuthorization() : ";
		//LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = new AuthorizationDetails();
		
		try {
			List<AuthCodeDetails> authCodes = authCodeRepository.getAuthCodeDetailsByAuthCode(authCode); 
			if(authCodes != null && !authCodes.isEmpty()) { 
				if(authCodes.size() > 1) {
					//LOGGER.error(logTag + "Found multiple entries for the authCode, "+authCode); 
					
				} else {
					AuthCodeDetails authCodeDetails = authCodes.get(0);
					//LOGGER.info(logTag + "AuthCode: " + authCodeDetails.getAuthCode());
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
			//LOGGER.error(logTag + "Exception while validating the authorization, "+authCode, e);  
		}
		
		return authorizationDetails;
	}
	
	//=========================================================================
	
	public void handleException(/*Logger LOGGER, */ String logTag, String exceptionMessage, Throwable e, AuthorizationDetails authorizationDetails) throws OECException {
		//LOGGER.error(logTag + exceptionMessage, e);  
		throw new OECException(exceptionRepository, logTag, exceptionMessage, e, authorizationDetails);
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
