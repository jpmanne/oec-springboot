/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abs.oec.dao.model.StudentAttendanceDetails;

@Transactional
@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendanceDetails, Long> {

	//========================================================================
	
	public default List<StudentAttendanceDetails> save(Iterable<StudentAttendanceDetails> studentsAttendanceDetails) {

	    List<StudentAttendanceDetails> result = new ArrayList<StudentAttendanceDetails>();

	    if (studentsAttendanceDetails == null) {
	        return result;
	    }
	    for (StudentAttendanceDetails entity : studentsAttendanceDetails) {
	        result.add(save(entity));
	    }
	    return result;
	}
	
	//========================================================================
	
	/*@Query("select a from StudentAttendanceDetails a where a.rollNo = :rollNo")
	List<StudentAttendanceDetails> getStudentsByRollNo(@Param("rollNo") String rollNo);
	
	//========================================================================
	
	@Query(value = "SELECT * FROM student_details a WHERE course_details_id = :courseDetailsId", nativeQuery = true)
	List<StudentAttendanceDetails> getStudentsByCourseDetailsId(@Param("courseDetailsId") Long courseDetailsId);*/
	
	
	//========================================================================
	
}
