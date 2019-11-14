package com.abs.oec.response.model;

public class WebStudentDetails {
	private Long studentDetailsId;
	private String rollNo;
	private String studentName;
	private WebCourseDetails courseDetails;
	public Long getStudentDetailsId() {
		return studentDetailsId;
	}
	public void setStudentDetailsId(Long studentDetailsId) {
		this.studentDetailsId = studentDetailsId;
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
	public WebCourseDetails getCourseDetails() {
		return courseDetails;
	}
	public void setCourseDetails(WebCourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}
}
