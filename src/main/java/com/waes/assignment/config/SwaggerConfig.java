package com.waes.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * This class is responsible for configuring the Swagger API
 * </p>
 * 
 * @author Mehmet Ali Tastan
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig  {                    
    
	/**
	 * to visit all path for given base package
	 * 
	 * @return the docket
	 */
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.waes.assignment.controller"))
          .paths(PathSelectors.any())
          .build();                                           
    }
}
