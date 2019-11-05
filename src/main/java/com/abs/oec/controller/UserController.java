/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

import com.abs.oec.common.URLConstants;
import com.abs.oec.exception.ResourceNotFoundException;
import com.abs.oec.model.UserDetails;
import com.abs.oec.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(URLConstants.User.API_BASE)
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;

	//=========================================================================
	
	@GetMapping(URLConstants.User.GET_ALL_USERS)
	public ResponseEntity<List<UserDetails>> getAllUsers() {
		String logTag = "getAllUsers() ";
		LOGGER.info(logTag + "START of the method");
		List<UserDetails> list = userRepository.findAll();
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<List<UserDetails>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	//=========================================================================
	
	@PostMapping(URLConstants.User.ADD_USER)
	public ResponseEntity<UserDetails> addUser(@Valid @RequestBody UserDetails userDetails) {
		UserDetails ud = userRepository.save(userDetails);
		return new ResponseEntity<UserDetails>(ud, new HttpHeaders(), HttpStatus.OK);
	}
	
	//=========================================================================

	@GetMapping(URLConstants.User.GET_USER)
	public UserDetails getUserById(@PathVariable(value = "userDetailsId") Long userDetailsId) {
		return userRepository.findById(userDetailsId).orElseThrow(() -> new ResourceNotFoundException("User", "userDetailsId", userDetailsId));
	}
	
	//=========================================================================

	@PutMapping(URLConstants.User.UPDATE_USER)
	public UserDetails updateUser(@PathVariable(value = "userDetailsId") Long userDetailsId, @Valid @RequestBody UserDetails userDetails) {
		String logTag = "updateUser() ";
		LOGGER.info(logTag + "START of the method");
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
		LOGGER.info(logTag + "END of the method");
		return updatedNote;
	}

	//=========================================================================
	
	@DeleteMapping(URLConstants.User.DELETE_USER)
	public ResponseEntity<?> deleteUser(@PathVariable(value = "userDetailsId") Long userDetailsId) {
		UserDetails note = userRepository.findById(userDetailsId).orElseThrow(() -> new ResourceNotFoundException("User", "userDetailsId", userDetailsId));
		userRepository.delete(note);
		return ResponseEntity.ok().build();
	}
	
	//=========================================================================
}
