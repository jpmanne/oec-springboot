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
import com.abs.oec.dao.model.StudentAttendanceDetails;
import com.abs.oec.dao.model.StudentDetails;
import com.abs.oec.model.AuthorizationDetails;
import com.abs.oec.model.Response;
import com.abs.oec.repository.AuthCodeRepository;
import com.abs.oec.repository.StudentAttendanceRepository;
import com.abs.oec.repository.StudentRepository;

@RestController
@RequestMapping(URLConstants.StudentAttendance.API_BASE)
public class StudentAttendanceController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentAttendanceController.class);
	
	@Autowired
	StudentAttendanceRepository studentAttendanceRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	AuthCodeRepository authCodeRepository;

	//=========================================================================
	
	@GetMapping(URLConstants.StudentAttendance.GET_STUDENTS_ATTENDANCE)
	public ResponseEntity<Response> getStudentsAttendance(@PathVariable(value = "courseDetailsId") Long courseDetailsId, @RequestParam("authCode") String authCode, @RequestParam("date") String date) {
		String logTag = "getStudentsAttendance() ";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		List<StudentAttendanceDetails> studentsAttendance = null;
		List<StudentDetails> students = null;
		Response response = null;
		
		try {
			if(date != null && !date.isEmpty()) {
				authorizationDetails = validateAuthorization(authCodeRepository, authCode);
				
				if(authorizationDetails.isValidAuthCode()) {
					if(authorizationDetails.isValidAccess()) {
						studentsAttendance = studentAttendanceRepository.getStudentsAttendanceByCourseDetailsId(courseDetailsId);
						if(studentsAttendance != null && !studentsAttendance.isEmpty()) {
							response = new Response("StudentsAttendance", studentsAttendance);
						} else {
							// As no attendance was save on this date, getting the students for the courseDetailsId
							students = studentRepository.getStudentsByCourseDetailsId(courseDetailsId);
							if(students != null && !students.isEmpty()) {
								studentsAttendance = new ArrayList<StudentAttendanceDetails>(students.size());
								for(StudentDetails studentDetails : students) {
									StudentAttendanceDetails sad = new StudentAttendanceDetails();
									sad.setStudentDetails(studentDetails);
									studentsAttendance.add(sad);
								}
								response = new Response("StudentsAttendance", studentsAttendance);
							} else {
								//Need to send a message that no students available for the courseDetailsId
							}
						}
					} else {
						response = getUnAuthorizedAccessRespose();
						LOGGER.info(logTag + "Unauthorized Access : "+authCode);
					}
				} else {
					response = getInvalidAuthCodeRespose(authCode);
					LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
				}
			} else {
				response = getInvalidInputRespose();
				LOGGER.info(logTag + "Invalid Input ");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	//=========================================================================
	
	@PostMapping(URLConstants.StudentAttendance.SAVE_STUDENTS_ATTENDANCE)
	public ResponseEntity<Response> saveStudentsAttendance(@Valid @RequestBody List<StudentAttendanceDetails> studentsAttendanceDetails, @RequestParam("authCode") String authCode) {
		String logTag = "saveStudentsAttendance() ";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCodeRepository, authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					List<StudentAttendanceDetails> students1 = studentAttendanceRepository.save(studentsAttendanceDetails);
					response = new Response("Students Attendance saved Successfully", students1);
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
