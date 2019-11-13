/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abs.oec.common.URLConstants;
import com.abs.oec.dao.model.UserDetails;
import com.abs.oec.exception.OECException;
import com.abs.oec.model.AuthorizationDetails;
import com.abs.oec.model.Response;
import com.abs.oec.repository.UserRepository;
import com.abs.oec.response.model.WebUserDetails;

@RestController
@RequestMapping(URLConstants.User.API_BASE)
public class UserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;

	//=========================================================================
	
	@GetMapping(URLConstants.User.GET_ALL_USERS)
	public ResponseEntity<Response> getAllUsers(@RequestParam("authCode") String authCode) throws OECException {
		String logTag = "getAllUsers() :";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		List<UserDetails> users = null; 
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					users = userRepository.findAll();
					if(users != null && !users.isEmpty()) {
						List<WebUserDetails> webUsers = new ArrayList<WebUserDetails>(users.size());
						for (UserDetails userDetails : users) {
							webUsers.add(userDetails.getWebUser());
						}
						response = new Response("Users", webUsers);
					} else {
						response = new Response("Users not found", null);
					}
				} else {
					LOGGER.info(logTag + "Unauthorized Access : "+authCode);
					return new ResponseEntity<Response>(getUnAuthorizedAccessRespose(), HttpStatus.UNAUTHORIZED);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			String exceptionMessage = logTag + "Exception while retrieving all the users";
			handleException(LOGGER, logTag, exceptionMessage, e, authorizationDetails); 
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	//=========================================================================
	
	@PostMapping(URLConstants.User.ADD_USER)
	public ResponseEntity<Response> addUser(@Valid @RequestBody UserDetails userDetails, @RequestParam("authCode") String authCode) throws OECException {
		String logTag = "addUser() :";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					UserDetails ud = userRepository.save(userDetails);
					if(ud != null && ud.getUserDetailsId() != null) {
						response = new Response("User Added Successfully", ud.getWebUser());
					} else {
						response = new Response("User Adding Failure", null);
					}
				} else {
					LOGGER.info(logTag + "Unauthorized Access : "+authCode);
					return new ResponseEntity<Response>(getUnAuthorizedAccessRespose(), HttpStatus.UNAUTHORIZED);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			String exceptionMessage = logTag + "Exception while adding the user ";
			handleException(LOGGER, logTag, exceptionMessage, e, authorizationDetails);
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//=========================================================================

	@GetMapping(URLConstants.User.GET_USER)
	public ResponseEntity<Response> getUserById(@PathVariable(value = "userDetailsId") Long userDetailsId, @RequestParam("authCode") String authCode) throws OECException {
		String logTag = "getUserById() :";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					Optional<UserDetails> userDetails = userRepository.findById(userDetailsId);
					
					if(userDetails.isPresent()) {
					    UserDetails existingUserDetails = userDetails.get();
					    response = new Response("User Details", existingUserDetails.getWebUser());
					} else {
						response = new Response("User not found with the userDetailsId :"+userDetailsId, null);
					}
				} else {
					LOGGER.info(logTag + "Unauthorized Access : "+authCode);
					return new ResponseEntity<Response>(getUnAuthorizedAccessRespose(), HttpStatus.UNAUTHORIZED);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			String exceptionMessage = logTag + "Exception while retrieving the user, "+userDetailsId;
			handleException(LOGGER, logTag, exceptionMessage, e, authorizationDetails);
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//=========================================================================

	@PutMapping(URLConstants.User.UPDATE_USER)
	public ResponseEntity<Response> updateUser(@PathVariable(value = "userDetailsId") Long userDetailsId, @Valid @RequestBody UserDetails userDetails, @RequestParam("authCode") String authCode) throws OECException {
		String logTag = "updateUser() :";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					Optional<UserDetails> user = userRepository.findById(userDetailsId);
					
					if(user != null && user.isPresent()) {
					    UserDetails existingUser = user.get();
					    existingUser.setFirstName(userDetails.getFirstName());
					    existingUser.setLastName(userDetails.getLastName()); 
					    existingUser.setMiddleName(userDetails.getMiddleName());
					    existingUser.setEmail(userDetails.getEmail());
					    existingUser.setPhoneNumber(userDetails.getPhoneNumber());
					    existingUser.setAddressLine1(userDetails.getAddressLine1());
					    existingUser.setAddressLine2(userDetails.getAddressLine2());
					    existingUser.setCity(userDetails.getCity());
					    existingUser.setState(userDetails.getState());
					    existingUser.setCountry(userDetails.getCountry());
						UserDetails updatedUserDetails = userRepository.save(existingUser);
						response = new Response("Update User Successful", updatedUserDetails.getWebUser());
					} else {
						new Response("User not found with the userDetailsId :"+userDetailsId, null);
					}
				} else {
					LOGGER.info(logTag + "Unauthorized Access : "+authCode);
					return new ResponseEntity<Response>(getUnAuthorizedAccessRespose(), HttpStatus.UNAUTHORIZED);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			String exceptionMessage = logTag + "Exception while updating the user, "+userDetailsId;
			handleException(LOGGER, logTag, exceptionMessage, e, authorizationDetails);
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	//=========================================================================
	
	@DeleteMapping(URLConstants.User.DELETE_USER)
	public ResponseEntity<Response> deleteUser(@PathVariable(value = "userDetailsId") Long userDetailsId, @RequestParam("authCode") String authCode) throws OECException {
		String logTag = "getUserById() :";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					Optional<UserDetails> userDetails = userRepository.findById(userDetailsId);
					if(userDetails.isPresent()) {
					    UserDetails existingUserDetails = userDetails.get();
					    userRepository.delete(existingUserDetails);
					    response = new Response("User Delete Successful", null);
					} else {
						response = new Response("User not found with the userDetailsId :"+userDetailsId, null);
					}
					
				} else {
					LOGGER.info(logTag + "Unauthorized Access : "+authCode);
					return new ResponseEntity<Response>(getUnAuthorizedAccessRespose(), HttpStatus.UNAUTHORIZED);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			String exceptionMessage = logTag + "Exception while deleting the user "+userDetailsId;
			handleException(LOGGER, logTag, exceptionMessage, e, authorizationDetails);
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//=========================================================================
}
