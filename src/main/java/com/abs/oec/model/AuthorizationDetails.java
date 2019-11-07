/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.model;

public class AuthorizationDetails {
	private Long userDetailsId;
	private String authCode;
	private boolean validAuthCode;
	private boolean validAccess;
	public Long getUserDetailsId() {
		return userDetailsId;
	}
	public void setUserDetailsId(Long userDetailsId) {
		this.userDetailsId = userDetailsId;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public boolean isValidAuthCode() {
		return validAuthCode;
	}
	public void setValidAuthCode(boolean validAuthCode) {
		this.validAuthCode = validAuthCode;
	}
	public boolean isValidAccess() {
		return validAccess;
	}
	public void setValidAccess(boolean validAccess) {
		this.validAccess = validAccess;
	}
	
}
