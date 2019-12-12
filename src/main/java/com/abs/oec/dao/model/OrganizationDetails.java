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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "organization_details")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt"}, allowGetters = true)
public class OrganizationDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "organization_details_id", length = 20)
	private Long organizationDetailsId;

	@Column(name = "organization_name", length = 150, nullable = false)
	private String organizationName;
	
	@Column(name = "organization_description", length = 255, nullable = true)
	private String organizationDescription;

	@Column(name = "website", length = 150, nullable = true)
	private String website;
	
	@Column(name = "email", length = 150, nullable = false)
	private String email;

	@Column(name = "mobile_number", length = 10, nullable = false)
	private String mobileNumber;
	
	@Column(name = "landline_number", length = 10, nullable = false)
	private String landlineNumber;
	
	@Column(name = "fax_number", length = 10, nullable = true)
	private String faxeNumber;
	
	@Column(name = "address_line1", length = 150, nullable = false)
	private String addressLine1;

	@Column(name = "address_line2", length = 150, nullable = true)
	private String addressLine2;

	@Column(name = "city", length = 100, nullable = true)
	private String city;

	@Column(name = "state", length = 100, nullable = true)
	private String state;

	@Column(name = "country", length = 5, nullable = true)
	private String country;

	@Column(name = "postal_code", length = 10, nullable = true)
	private String postalCode;
	
	@Column(name = "status", length = 1, nullable = false)
	private String status = "1";
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	public Long getOrganizationDetailsId() {
		return organizationDetailsId;
	}

	public void setOrganizationDetailsId(Long organizationDetailsId) {
		this.organizationDetailsId = organizationDetailsId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationDescription() {
		return organizationDescription;
	}

	public void setOrganizationDescription(String organizationDescription) {
		this.organizationDescription = organizationDescription;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getLandlineNumber() {
		return landlineNumber;
	}

	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}

	public String getFaxeNumber() {
		return faxeNumber;
	}

	public void setFaxeNumber(String faxeNumber) {
		this.faxeNumber = faxeNumber;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}