/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

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
import com.abs.oec.exception.ResourceNotFoundException;
import com.abs.oec.model.AutherizationDetails;
import com.abs.oec.model.Response;
import com.abs.oec.repository.AuthCodeRepository;
import com.abs.oec.repository.UserRepository;

@RestController
@RequestMapping(URLConstants.User.API_BASE)
public class UserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthCodeRepository authCodeRepository;

	//=========================================================================
	
	@GetMapping(URLConstants.User.GET_ALL_USERS)
	public ResponseEntity<Response> getAllUsers(@RequestParam("authCode") String authCode) {
		String logTag = "getAllUsers() ";
		LOGGER.info(logTag + "START of the method");
		AutherizationDetails autherizationDetails = null;
		List<UserDetails> users = null; 
		Response response = null;
		
		try {
			autherizationDetails = validateAuthorization(authCodeRepository, authCode);
			
			if(autherizationDetails.isValidAuthCode()) {
				if(autherizationDetails.isValidAccess()) {
					users = userRepository.findAll();
					response = new Response("Users", users);
				} else {
					response = getUnAuthorizedAccessRespose();
					LOGGER.info(logTag + "Unauthorized Access : "+authCode);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	//=========================================================================
	
	@PostMapping(URLConstants.User.ADD_USER)
	public ResponseEntity<Response> addUser(@Valid @RequestBody UserDetails userDetails, @RequestParam("authCode") String authCode) {
		String logTag = "addUser() ";
		LOGGER.info(logTag + "START of the method");
		AutherizationDetails autherizationDetails = null;
		Response response = null;
		
		try {
			autherizationDetails = validateAuthorization(authCodeRepository, authCode);
			
			if(autherizationDetails.isValidAuthCode()) {
				if(autherizationDetails.isValidAccess()) {
					UserDetails ud = userRepository.save(userDetails);
					response = new Response("User Added Successfully", ud);
				} else {
					response = getUnAuthorizedAccessRespose();
					LOGGER.info(logTag + "Unauthorized Access : "+authCode);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//=========================================================================

	@GetMapping(URLConstants.User.GET_USER)
	public ResponseEntity<Response> getUserById(@PathVariable(value = "userDetailsId") Long userDetailsId, @RequestParam("authCode") String authCode) {
		String logTag = "getUserById() ";
		LOGGER.info(logTag + "START of the method");
		AutherizationDetails autherizationDetails = null;
		Response response = null;
		
		try {
			autherizationDetails = validateAuthorization(authCodeRepository, authCode);
			
			if(autherizationDetails.isValidAuthCode()) {
				if(autherizationDetails.isValidAccess()) {
					Optional<UserDetails> userDetails = userRepository.findById(userDetailsId);
					if(userDetails.isPresent()) {
					    UserDetails existingUserDetails = userDetails.get();
					    response = new Response("User Details", existingUserDetails);
					} else {
						new Response("User not found with the userDetailsId :"+userDetailsId, null);
					}
				} else {
					response = getUnAuthorizedAccessRespose();
					LOGGER.info(logTag + "Unauthorized Access : "+authCode);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.info(logTag + "END of the method");
		//return userRepository.findById(userDetailsId).orElseThrow(() -> new ResourceNotFoundException("User", "userDetailsId", userDetailsId));
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//=========================================================================

	@PutMapping(URLConstants.User.UPDATE_USER)
	public /*UserDetails*/ResponseEntity<Response> updateUser(@PathVariable(value = "userDetailsId") Long userDetailsId, @Valid @RequestBody UserDetails userDetails, @RequestParam("authCode") String authCode) {
		String logTag = "updateUser() ";
		LOGGER.info(logTag + "START of the method");
		AutherizationDetails autherizationDetails = null;
		Response response = null;
		
		try {
			autherizationDetails = validateAuthorization(authCodeRepository, authCode);
			
			if(autherizationDetails.isValidAuthCode()) {
				if(autherizationDetails.isValidAccess()) {
					UserDetails user = userRepository.findById(userDetailsId).orElseThrow(() -> new ResourceNotFoundException("User", "userDetailsId", userDetailsId));
					user.setFirstName(userDetails.getFirstName());
					user.setLastName(userDetails.getLastName()); 
					user.setMiddleName(userDetails.getMiddleName());
					user.setEmail(userDetails.getEmail());
					user.setPhoneNumber(userDetails.getPhoneNumber());
					user.setAddressLine1(userDetails.getAddressLine1());
					user.setAddressLine2(userDetails.getAddressLine2());
					user.setCity(userDetails.getCity());
					user.setState(userDetails.getState());
					user.setCountry(userDetails.getCountry());
					UserDetails updatedNote = userRepository.save(user);
					response = new Response("Update User Successful", updatedNote);
				} else {
					response = getUnAuthorizedAccessRespose();
					LOGGER.info(logTag + "Unauthorized Access : "+authCode);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.info(logTag + "END of the method");
		//return updatedNote;
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	//=========================================================================
	
	@DeleteMapping(URLConstants.User.DELETE_USER)
	public ResponseEntity<?> deleteUser(@PathVariable(value = "userDetailsId") Long userDetailsId, @RequestParam("authCode") String authCode) {
		String logTag = "getUserById() ";
		LOGGER.info(logTag + "START of the method");
		AutherizationDetails autherizationDetails = null;
		Response response = null;
		
		try {
			autherizationDetails = validateAuthorization(authCodeRepository, authCode);
			
			if(autherizationDetails.isValidAuthCode()) {
				if(autherizationDetails.isValidAccess()) {
					Optional<UserDetails> userDetails = userRepository.findById(userDetailsId);
					if(userDetails.isPresent()) {
					    UserDetails existingUserDetails = userDetails.get();
					    userRepository.delete(existingUserDetails);
					} else {
						new Response("User not found with the userDetailsId :"+userDetailsId, null);
					}
					response = new Response("User Delete Successful", null);
				} else {
					response = getUnAuthorizedAccessRespose();
					LOGGER.info(logTag + "Unauthorized Access : "+authCode);
					
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
		//return ResponseEntity.ok().build();
	}
	
	//=========================================================================
}
