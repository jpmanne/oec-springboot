/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "auth_code_details")
@EntityListeners(AuditingEntityListener.class)
public class AuthCodeDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auth_code_details_id", length = 20, nullable = false)
	private Long authcodeDetailsId;

	@Column(name = "auth_code", length = 32, nullable = false)
	private String authCode;
	
	@Column(name = "status", length = 1, nullable = false)
	private String status;

	@Column(name = "login_time", nullable = false)
	private Date loginTime;
	
	@Column(name = "logout_time", nullable = false)
	private Date logoutTime;
	
	@OneToOne
	@JoinColumn(name = "user_details_id")
	private UserDetails userDetails;

	public Long getAuthcodeDetailsId() {
		return authcodeDetailsId;
	}

	public void setAuthcodeDetailsId(Long authcodeDetailsId) {
		this.authcodeDetailsId = authcodeDetailsId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
}