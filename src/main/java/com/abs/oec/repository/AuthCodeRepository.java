/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abs.oec.dao.model.AuthCodeDetails;

@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCodeDetails, Long> {
	
	@Query("select a from AuthCodeDetails a where a.authCode = :authCode")
	List<AuthCodeDetails> getAuthCodeDetailsByAuthCode(@Param("authCode") String authCode);
	
	@Query(value = "SELECT * FROM auth_code_details a WHERE auth_code = :authCode and user_details_id = :userDetailsId", nativeQuery = true)
	List<AuthCodeDetails> getAuthCodeDetailsByAuthCode(@Param("authCode") String authCode, @Param("userDetailsId") Long userDetailsId);

}
