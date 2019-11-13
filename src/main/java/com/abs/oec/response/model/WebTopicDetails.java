package com.abs.oec.response.model;

public class WebTopicDetails {
	private Long topicDetailsId;
	private String topic;
	private Long unitId;
	public Long getTopicDetailsId() {
		return topicDetailsId;
	}
	public void setTopicDetailsId(Long topicDetailsId) {
		this.topicDetailsId = topicDetailsId;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
}
