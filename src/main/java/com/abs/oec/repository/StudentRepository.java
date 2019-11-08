/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abs.oec.dao.model.StudentDetails;

@Transactional
@Repository
public interface StudentRepository extends JpaRepository<StudentDetails, Long> {

	//========================================================================
	
	public default List<StudentDetails> save(Iterable<StudentDetails> students) {

	    List<StudentDetails> result = new ArrayList<StudentDetails>();

	    if (students == null) {
	        return result;
	    }
	    for (StudentDetails entity : students) {
	        result.add(save(entity));
	    }
	    return result;
	}
	
	//========================================================================
	
	@Query("SELECT sd FROM StudentDetails sd WHERE sd.rollNo = :rollNo")
	Collection<StudentDetails> getStudentsByRollNo(@Param("rollNo") String rollNo);
	
	//========================================================================
	
	@Query(value = "SELECT * FROM student_details a WHERE course_details_id = :courseDetailsId", nativeQuery = true)
	List<StudentDetails> getStudentsByCourseDetailsId(@Param("courseDetailsId") Long courseDetailsId);
	
	
	//========================================================================
	
}
