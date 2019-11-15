/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.dao.model;

import com.abs.oec.common.Constants;
import com.abs.oec.response.model.WebStudentAttendanceDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "student_attendance_details")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt"}, allowGetters = true)
public class StudentAttendanceDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_attendance_details_id", length = 20, nullable = false)
	private Long studentAttendanceDetailsId;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;
	
	@OneToOne
	@JoinColumn(name = "student_details_id")
	private StudentDetails studentDetails;
	
	@Column(name = "attendance", length = 1, nullable = false)
	private String attendance;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.DATE)
	private Date attendanceDate;

	public Long getStudentAttendanceDetailsId() {
		return studentAttendanceDetailsId;
	}

	public void setStudentAttendanceDetailsId(Long studentAttendanceDetailsId) {
		this.studentAttendanceDetailsId = studentAttendanceDetailsId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public StudentDetails getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
	public WebStudentAttendanceDetails getWebStudentAttendanceDetails() {
		WebStudentAttendanceDetails webStudentAttendanceDetails = new WebStudentAttendanceDetails();
		webStudentAttendanceDetails.setAttendance(attendance);
		webStudentAttendanceDetails.setStudentAttendanceDetailsId(studentAttendanceDetailsId);
		if(studentDetails != null) {
			webStudentAttendanceDetails.setStudentDetails(studentDetails.getWebStudentDetails());
		}
		if(attendanceDate != null) {
			webStudentAttendanceDetails.setAttendanceDate(new SimpleDateFormat(Constants.DATE_FORMAT).format(attendanceDate));
		}
		return webStudentAttendanceDetails;
	}
}