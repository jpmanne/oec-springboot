/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abs.oec.common.URLConstants;
import com.abs.oec.dao.model.CourseDetails;
import com.abs.oec.model.AuthorizationDetails;
import com.abs.oec.model.Response;
import com.abs.oec.repository.AuthCodeRepository;
import com.abs.oec.repository.CourseRepository;

@RestController
@RequestMapping(URLConstants.Course.API_BASE)
public class CourseController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	AuthCodeRepository authCodeRepository;

	//=========================================================================
	
	@GetMapping(URLConstants.Course.GET_COURSES)
	public ResponseEntity<Response> getCoursesByCourseCode(@PathVariable(value = "courseCode") String courseCode, @RequestParam("authCode") String authCode) {
		String logTag = "getCoursesByCourseCode() ";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		List<CourseDetails> courses = null; 
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCodeRepository, authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					courses = courseRepository.getCoursesByCourseCode(courseCode);
					response = new Response("Courses", courses);
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
	
	@PostMapping(URLConstants.Course.ADD_COURSES)
	public ResponseEntity<Response> addCourses(@Valid @RequestBody List<CourseDetails> courses, @RequestParam("authCode") String authCode) {
		String logTag = "addCourses() ";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCodeRepository, authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					List<CourseDetails> courses1 = courseRepository.save(courses);
					response = new Response("Courses Added Successfully", courses1);
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

}
