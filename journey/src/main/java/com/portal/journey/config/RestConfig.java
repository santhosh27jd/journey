package com.portal.journey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author santhoshkumardurairaj
 *
 */
@Configuration
public class RestConfig {

	/**
	 * RestTemplate Bean configuration
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
