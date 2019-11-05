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
}
