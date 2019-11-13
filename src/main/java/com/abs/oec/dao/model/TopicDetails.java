/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.dao.model;

import com.abs.oec.response.model.WebTopicDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "topic_details")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt"}, allowGetters = true)
public class TopicDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topic_details_id", length = 20, nullable = false)
	private Long topicDetailsId;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;
	
	@Column(name = "topic", length = 150, nullable = false)
	private String topic;

	@Column(name = "unit_id", length = 20, nullable = true)
	private Long unitId;

	public Long getTopicDetailsId() {
		return topicDetailsId;
	}

	public void setTopicDetailsId(Long topicDetailsId) {
		this.topicDetailsId = topicDetailsId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
	
	public WebTopicDetails getWebTopicDetails() {
		WebTopicDetails webTopicDetails = new WebTopicDetails();
		webTopicDetails.setTopicDetailsId(topicDetailsId);
		webTopicDetails.setTopic(topic);
		webTopicDetails.setUnitId(unitId);
		return webTopicDetails;
	}
}