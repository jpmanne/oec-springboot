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

import com.abs.oec.dao.model.CourseDetails;

@Transactional
@Repository
public interface CourseRepository extends JpaRepository<CourseDetails, Long> {

	//========================================================================
	
	public default List<CourseDetails> save(Iterable<CourseDetails> courses) {

	    List<CourseDetails> result = new ArrayList<CourseDetails>();

	    if (courses == null) {
	        return result;
	    }
	    for (CourseDetails entity : courses) {
	        result.add(save(entity));
	    }
	    return result;
	}
	
	//========================================================================
	
	@Query("select a from CourseDetails a where a.courseCode = :courseCode")
	List<CourseDetails> getCoursesByCourseCode(@Param("courseCode") String courseCode);
	
	
	//========================================================================
	
}
