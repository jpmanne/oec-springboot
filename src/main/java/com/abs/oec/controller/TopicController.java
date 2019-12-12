/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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
import com.abs.oec.dao.model.TopicDetails;
import com.abs.oec.exception.OECException;
import com.abs.oec.model.AuthorizationDetails;
import com.abs.oec.model.Response;
import com.abs.oec.repository.TopicRepository;
import com.abs.oec.response.model.WebTopicDetails;

@RestController
@RequestMapping(URLConstants.Topic.API_BASE)
public class TopicController extends BaseController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(TopicController.class);
	
	@Autowired
	TopicRepository topicRepository;
	
	//=========================================================================
	
	@GetMapping(URLConstants.Topic.GET_TOPICS)
	public ResponseEntity<Response> getTopicsByUnit(@PathVariable(value = "unitId") Long unitId, @RequestParam("authCode") String authCode) throws OECException {
		String logTag = "getTopicsByUnit() ";
		//LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		List<TopicDetails> topics = null; 
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					topics = topicRepository.getTopicsByUnitId(unitId);
					
					if(topics != null && !topics.isEmpty()) {
						List<WebTopicDetails> webTopics = new ArrayList<WebTopicDetails>(topics.size());
						for (TopicDetails topicDetails : topics) {
							webTopics.add(topicDetails.getWebTopicDetails());
						}
						response = new Response("Topics", webTopics);
					} else {
						response = new Response("Topics not found", null);
					}
				} else {
					//LOGGER.info(logTag + "Unauthorized Access : "+authCode);
					return new ResponseEntity<Response>(getUnAuthorizedAccessRespose(), HttpStatus.UNAUTHORIZED);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				//LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			String exceptionMessage = logTag + "Exception while getting the topics by unit, "+unitId;
			handleException(/*LOGGER,*/ logTag, exceptionMessage, e, authorizationDetails);
		}
		//LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	//=========================================================================
	
	@PostMapping(URLConstants.Topic.ADD_TOPICS)
	public ResponseEntity<Response> addTopics(@Valid @RequestBody List<TopicDetails> topics, @RequestParam("authCode") String authCode) throws OECException {
		String logTag = "addTopics() ";
		//LOGGER.info(logTag + "START of the method");
		AuthorizationDetails authorizationDetails = null;
		Response response = null;
		
		try {
			authorizationDetails = validateAuthorization(authCode);
			
			if(authorizationDetails.isValidAuthCode()) {
				if(authorizationDetails.isValidAccess()) {
					topicRepository.save(topics);
					response = new Response("Topics Added Successfully", null);
				} else {
					//LOGGER.info(logTag + "Unauthorized Access : "+authCode);
					return new ResponseEntity<Response>(getUnAuthorizedAccessRespose(), HttpStatus.UNAUTHORIZED);
				}
			} else {
				response = getInvalidAuthCodeRespose(authCode);
				//LOGGER.info(logTag + "Invalid AuthCode : "+authCode);
			}
		} catch (Exception e) {
			String exceptionMessage = logTag + "Exception while adding the Topics ";
			handleException(/*LOGGER,*/ logTag, exceptionMessage, e, authorizationDetails);
		}
		//LOGGER.info(logTag + "END of the method");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//=========================================================================

}
