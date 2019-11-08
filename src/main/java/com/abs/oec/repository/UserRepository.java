/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abs.oec.dao.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {
	
	@Query("select a from UserDetails a where a.userName = :userName and a.password = :password")
	List<UserDetails> getUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);
	
	List<UserDetails> getUsersByDateOfBirth(Date dateOfBirth);

}
