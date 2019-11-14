package com.abs.oec.response.model;

public class WebCourseDetails {
	private Long courseDetailsId;
	private String courseCode;
	private String courseName;
	private String semester;
	public Long getCourseDetailsId() {
		return courseDetailsId;
	}
	public void setCourseDetailsId(Long courseDetailsId) {
		this.courseDetailsId = courseDetailsId;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
}
