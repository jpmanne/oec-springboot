package com.abs.oec.response.model;

public class WebStudentAttendanceDetails {
	private Long studentAttendanceDetailsId;
	private String attendance;
	private String attendanceDate;
	private WebStudentDetails studentDetails;
	public Long getStudentAttendanceDetailsId() {
		return studentAttendanceDetailsId;
	}
	public void setStudentAttendanceDetailsId(Long studentAttendanceDetailsId) {
		this.studentAttendanceDetailsId = studentAttendanceDetailsId;
	}
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public WebStudentDetails getStudentDetails() {
		return studentDetails;
	}
	public void setStudentDetails(WebStudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}
}
