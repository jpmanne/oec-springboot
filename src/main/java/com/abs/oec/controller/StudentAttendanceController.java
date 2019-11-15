/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.abs.oec.common.Constants;
import com.abs.oec.common.URLConstants;
import com.abs.oec.dao.model.StudentAttendanceDetails;
import com.abs.oec.dao.model.StudentDetails;
import com.abs.oec.exception.OECException;
import com.abs.oec.model.AuthorizationDetails;
import com.abs.oec.model.Response;
import com.abs.oec.repository.StudentAttendanceRepository;
import com.abs.oec.repository.StudentRepository;
import com.abs.oec.response.model.WebStudentAttendanceDetails;

@RestController
@RequestMapping(URLConstants.StudentAttendance.API_BASE)
public class StudentAttendanceController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentAttendanceController.class);
	
	@Autowired
	StudentAttendanceRepository studentAttendanceRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	//=========================================================================
	
	@GetMapping(URLConstants.StudentAttendance.GET_STUDENTS_ATTENDANCE)
	public ResponseEntity<Response> getStudentsAttendance(@PathVariable(value = "courseDetailsId") Long courseDetailsId, @RequestParam("authCode") String authCode, @RequestParam("date") String date) throws OECException {
		String logTag = "getStudentsAttendance() ";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		List<StudentAttendanceDetails> studentsAttendance = null;
		List<StudentDetails> students = null;
		Response response = null;
		
		try {
			if(date != null && !date.isEmpty()) {
				authorizationDetails = validateAuthorization(authCode);
				
				if(authorizationDetails.isValidAuthCode()) {
					if(authorizationDetails.isValidAccess()) {
						students = studentRepository.getStudentsByCourseDetailsId(courseDetailsId);
						
						if(students != null && !students.isEmpty()) {
							List<Long> studentDetailsIds = new ArrayList<Long>(students.size());
							for(StudentDetails studentDetails : students) {
								studentDetailsIds.add(studentDetails.getStudentDetailsId());
							}
							Date attendanceDate = new SimpleDateFormat(Constants.DATE_FORMAT_DB).parse(date);
							studentsAttendance = studentAttendanceRepository.getStudentAttendance(studentDetailsIds, attendanceDate);
							
							if(studentsAttendance == null || studentsAttendance.isEmpty()) {
								LOGGER.info(logTag + "As the student attendance is null of empty populating the default student attendance details");
								studentsAttendance = new ArrayList<StudentAttendanceDetails>(students.size());
								for(StudentDetails studentDetails : students) {
									StudentAttendanceDetails sad = new StudentAttendanceDetails();
									sad.setStudentDetails(studentDetails);
									studentsAttendance.add(sad);
								}
							}
							List<WebStudentAttendanceDetails> webStudentsAttendance = new ArrayList<WebStudentAttendanceDetails>();
							for (StudentAttendanceDetails studentAttendanceDetails : studentsAttendance) {
								webStudentsAttendance.add(studentAttendanceDetails.getWebStudentAttendanceDetails());
							}
							response = new Response("StudentsAttendance", webStudentsAttendance);
							
						} else {
							response = new Response("No Students found for the courseDetailsId :"+courseDetailsId, null);
						}
					} else {
						LOGGER.info(logTag + "Unauthorized Access : "+authCode);
						return new ResponseEntity<Response>(getUnAuthorizedAccessRespose(), HttpStatus.UNAUTHORIZED);
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
			String exceptionMessage = logTag + "Exception while getting the students attendance ";
			handleException(LOGGER, logTag, exceptionMessage, e, authorizationDetails);
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	//=========================================================================
	
	@PostMapping(URLConstants.StudentAttendance.SAVE_STUDENTS_ATTENDANCE)
	public ResponseEntity<Response> saveStudentsAttendance(@Valid @RequestBody List<StudentAttendanceDetails> studentsAttendanceDetails, @RequestParam("authCode") String authCode) throws OECException {
		String logTag = "saveStudentsAttendance() ";
		LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					List<StudentAttendanceDetails> savedStudentsAttendance = studentAttendanceRepository.save(studentsAttendanceDetails);
					
					if(savedStudentsAttendance != null && !savedStudentsAttendance.isEmpty()) {
						List<WebStudentAttendanceDetails> webStudentsAttendance = new ArrayList<WebStudentAttendanceDetails>();
						for (StudentAttendanceDetails studentAttendanceDetails : savedStudentsAttendance) {
							webStudentsAttendance.add(studentAttendanceDetails.getWebStudentAttendanceDetails());
						}
						response = new Response("Students Attendance saved Successfully", webStudentsAttendance);
					} else {
						response = new Response("Students Attendance saving failed", null);
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
			String exceptionMessage = logTag + "Exception while adding the students attendance ";
			handleException(LOGGER, logTag, exceptionMessage, e, authorizationDetails);
		}
		LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//=========================================================================

}
