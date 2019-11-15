/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

import java.util.ArrayList;
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
import com.abs.oec.dao.model.StudentDetails;
import com.abs.oec.exception.OECException;
import com.abs.oec.model.AuthorizationDetails;
import com.abs.oec.model.Response;
import com.abs.oec.repository.StudentRepository;
import com.abs.oec.response.model.WebStudentDetails;

@RestController
@RequestMapping(URLConstants.Student.API_BASE)
public class StudentController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	StudentRepository studentRepository;
	
	//=========================================================================
	
	@GetMapping(URLConstants.Student.GET_STUDENTS)
	public ResponseEntity<Response> getStudentsByCourseDetailsId(@PathVariable(value = "courseDetailsId") Long courseDetailsId, @RequestParam("authCode") String authCode) throws OECException {
		String logTag = "getStudentsByCourseDetailsId() ";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		List<StudentDetails> students = null; 
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					students = studentRepository.getStudentsByCourseDetailsId(courseDetailsId);
					List<WebStudentDetails> webStudents = new ArrayList<WebStudentDetails>();
					if(students != null && !students.isEmpty()) {
						for (StudentDetails studentDetails : students) {
							webStudents.add(studentDetails.getWebStudentDetails());
						}
						response = new Response("Students", webStudents);
					} else {
						response = new Response("Students not found", null);
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
			String exceptionMessage = logTag + "Exception while getting the students by course details id, "+courseDetailsId;
			handleException(LOGGER, logTag, exceptionMessage, e, authorizationDetails);
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	//=========================================================================
	
	@PostMapping(URLConstants.Student.ADD_STUDENTS)
	public ResponseEntity<Response> addStudents(@Valid @RequestBody List<StudentDetails> students, @RequestParam("authCode") String authCode) throws OECException {
		String logTag = "addStudents() ";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					List<StudentDetails> savedStudents = studentRepository.save(students);
					if(savedStudents != null && !savedStudents.isEmpty()) {
						List<WebStudentDetails> webStudents = new ArrayList<WebStudentDetails>();
						for (StudentDetails studentDetails : savedStudents) {
							webStudents.add(studentDetails.getWebStudentDetails());
						}
						response = new Response("Students Added Successfully", webStudents);
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
			String exceptionMessage = logTag + "Exception while adding the students ";
			handleException(LOGGER, logTag, exceptionMessage, e, authorizationDetails);
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//=========================================================================

}
