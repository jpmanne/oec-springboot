/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abs.oec.dao.model.ExceptionDetails;

@Repository
public interface ExceptionRepository extends JpaRepository<ExceptionDetails, Long> {

}
