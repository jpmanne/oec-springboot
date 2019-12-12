/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.dao.model;

import com.abs.oec.response.model.WebStudentDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "student_details")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt"}, allowGetters = true)
public class StudentDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_details_id", length = 20, nullable = false)
	private Long studentDetailsId;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;
	
	@Column(name = "roll_no", length = 50, nullable = false)
	private String rollNo;
	
	@Column(name = "student_name", length = 150, nullable = false)
	private String studentName;

	@OneToOne
	@JoinColumn(name = "course_details_id")
	private CourseDetails courseDetails;
	
	@Column(name = "status", length = 1, nullable = false)
	private String status = "1";

	@Column(name = "organization_details_id", length = 20, nullable = false)
	private Long organizationDetailsId;
	
	public Long getStudentDetailsId() {
		return studentDetailsId;
	}

	public void setStudentDetailsId(Long studentDetailsId) {
		this.studentDetailsId = studentDetailsId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getOrganizationDetailsId() {
		return organizationDetailsId;
	}

	public void setOrganizationDetailsId(Long organizationDetailsId) {
		this.organizationDetailsId = organizationDetailsId;
	}

	public WebStudentDetails getWebStudentDetails() {
		WebStudentDetails webStudentDetails = new WebStudentDetails();
		webStudentDetails.setRollNo(rollNo);
		webStudentDetails.setStudentDetailsId(studentDetailsId);
		webStudentDetails.setStudentName(studentName);
		if(courseDetails != null) {
			webStudentDetails.setCourseDetails(courseDetails.getWebCourseDetails());
		}
		return webStudentDetails;
	}
}