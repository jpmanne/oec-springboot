/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abs.oec.common.Constants;
import com.abs.oec.common.URLConstants;
import com.abs.oec.model.AuthCodeDetails;
import com.abs.oec.model.UserDetails;
import com.abs.oec.repository.AuthCodeRepository;
import com.abs.oec.repository.UserRepository;
import com.abs.oec.request.model.LoginRequest;
import com.abs.oec.request.model.LogoutRequest;
import com.abs.oec.response.model.LoginResponse;
import com.abs.oec.util.AuthCodeGenerator;

@RestController
@RequestMapping(URLConstants.Login.API_BASE)
public class LoginController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	AuthCodeRepository authCodeRepository;
	
	@Autowired
	UserRepository userRepository;
	

	//=========================================================================
	
	@PostMapping(URLConstants.Login.LOGIN_USER)
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
		String logTag = "loginUser() ";
		LOGGER.info(logTag + "START of the method");
		AuthCodeDetails authCodeDetails = null;
		LoginResponse loginResponse = new LoginResponse();
		
		try {
			// Getting the user with the username and password
			List<UserDetails> users = userRepository.getUserByUserNameAndPassword(loginRequest.getUserName(), loginRequest.getPassword());
			
			if(users != null && !users.isEmpty()) {
				if(users.size() > 1) {
					//Need to throw and error as multiple users having same credentials
				} else {
					authCodeDetails = new AuthCodeDetails();
					authCodeDetails.setAuthCode(AuthCodeGenerator.getInstance().getGeneratedAuthCode());
					UserDetails userDetails = users.get(0);
					authCodeDetails.setUserDetails(userDetails);
					authCodeDetails.setLoginTime(new Date());
					authCodeDetails.setLogoutTime(new Date());
					authCodeDetails.setStatus(Constants.ACTIVE);
					authCodeDetails = authCodeRepository.save(authCodeDetails);
					
					loginResponse.setAuthCode(authCodeDetails.getAuthCode());
					loginResponse.setFistName(userDetails.getFirstName());
					loginResponse.setMiddleName(userDetails.getMiddleName());
					loginResponse.setLastName(userDetails.getLastName());
					loginResponse.setUserDetailsId(userDetails.getUserDetailsId());
					loginResponse.setRoleId(userDetails.getRoleDetails().getRoleId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<LoginResponse>(loginResponse, new HttpHeaders(), HttpStatus.OK);
	}
	
	//=========================================================================
	
	@PostMapping(URLConstants.Login.LOGOUT_USER)
	public ResponseEntity<UserDetails> logoutUser(@Valid @RequestBody LogoutRequest logoutRequest) {
		
		
		
		//UserDetails ud = userRepository.save(userDetails);
		return new ResponseEntity<UserDetails>(null, new HttpHeaders(), HttpStatus.OK);
	}
	
	//=========================================================================
}
