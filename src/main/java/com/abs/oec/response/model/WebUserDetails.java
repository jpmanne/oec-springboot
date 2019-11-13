package com.abs.oec.response.model;

import org.springframework.util.StringUtils;

public class WebUserDetails {
	private Long userDetailsId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dateOfBirth;
	private String email;
	private String phoneNumber;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String status;
	private Long roleId;
	
	public Long getUserDetailsId() {
		return userDetailsId;
	}
	public void setUserDetailsId(Long userDetailsId) {
		this.userDetailsId = userDetailsId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public String getFullName() {
		StringBuilder fullNameBuilder = new StringBuilder();
		if (!StringUtils.isEmpty(firstName)) {
			fullNameBuilder.append(firstName);
		}
		if (!StringUtils.isEmpty(middleName)) {
			if (fullNameBuilder.length() > 0) {
				fullNameBuilder.append(" ");
			}
			fullNameBuilder.append(middleName);
		}
		if (!StringUtils.isEmpty(lastName)) {
			if (fullNameBuilder.length() > 0) {
				fullNameBuilder.append(" ");
			}
			fullNameBuilder.append(lastName);
		}
		return fullNameBuilder.toString();
	}
}
