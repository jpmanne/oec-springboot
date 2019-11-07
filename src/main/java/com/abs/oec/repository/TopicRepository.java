/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abs.oec.dao.model.TopicDetails;

@Transactional
@Repository
public interface TopicRepository extends JpaRepository<TopicDetails, Long> {

	//========================================================================
	
	public default List<TopicDetails> save(Iterable<TopicDetails> topics) {

	    List<TopicDetails> result = new ArrayList<TopicDetails>();

	    if (topics == null) {
	        return result;
	    }
	    for (TopicDetails entity : topics) {
	        result.add(save(entity));
	    }
	    return result;
	}
	
	//========================================================================
	
	@Query("select a from TopicDetails a where a.unitId = :unitId")
	List<TopicDetails> getTopicsByUnitId(@Param("unitId") Long unitId);
	
	
	//========================================================================
	
}
