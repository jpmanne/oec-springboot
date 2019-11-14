/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableJpaAuditing
public class OECApplication /*extends SpringBootServletInitializer*/ {
	private static final Logger LOGGER = LoggerFactory.getLogger(OECApplication.class);
	
	//=========================================================================
	
	public static void main(String[] args) {
		SpringApplication.run(OECApplication.class, args);
		//LOGGER.info("Simple log statement with inputs {}, {} and {}", 1,2,3);
		LOGGER.info("Start of the OECApplication............... ");
	}
	
	//=========================================================================
	
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OECApplication.class);
	}*/
	
	//=========================================================================
}
