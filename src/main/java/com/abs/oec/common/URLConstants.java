/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.common;

public class URLConstants {

	public class User {
		public static final String API_BASE = "/api/user";
		public static final String GET_ALL_USERS = "/all";
		public static final String GET_USER = "/{userDetailsId}";
		public static final String ADD_USER = "/add";
		public static final String UPDATE_USER = "/update/{userDetailsId}";
		public static final String DELETE_USER = "/{userDetailsId}";
	}
	
	public class Login {
		public static final String API_BASE = "/api/user";
		public static final String LOGIN_USER = "/login";
		public static final String LOGOUT_USER = "/logout";
	}
	
	public class Topic {
		public static final String API_BASE = "/api/topic";
		public static final String GET_TOPICS = "/unit/{unitId}";
		public static final String ADD_TOPICS = "/add/all";
	}
	
	public class Course {
		public static final String API_BASE = "/api/course";
		public static final String GET_COURSES = "/{courseCode}";
		public static final String ADD_COURSES = "/add/all";
	}
	
	public class Student {
		public static final String API_BASE = "/api/student";
		public static final String GET_STUDENTS = "/{courseDetailsId}";
		public static final String ADD_STUDENTS = "/add/all";
	}
}
