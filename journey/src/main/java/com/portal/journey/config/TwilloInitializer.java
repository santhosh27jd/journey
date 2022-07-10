package com.portal.journey.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

/**
 * 
 * @author santhoshkumardurairaj
 *
 */
@Configuration
public class TwilloInitializer {
	private final static Logger LOGGER = LoggerFactory.getLogger(TwilloInitializer.class);

	/**
	 * Twillo config injection
	 */
	private final TwilloConfig twilioConfiguration;

	/**
	 * 
	 * @param twilioConfiguration
	 */
	@Autowired
	public TwilloInitializer(TwilloConfig twilioConfiguration) {
		this.twilioConfiguration = twilioConfiguration;
		Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
		LOGGER.info("Twilio initialized ... with account sid {} ", twilioConfiguration.getAccountSid());
	}
}
