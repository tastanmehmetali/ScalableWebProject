package com.waes.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * To initialize the services for the class of the Spring Boot Application
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
@SpringBootApplication
public class DiffApplication {

	/**
	 * 
	 * @param args that required arguments to initialize, start and configure the web applicaiton 
	 */
	public static void main(final String[] args) {
		SpringApplication.run(DiffApplication.class, args);
	}
	
}
