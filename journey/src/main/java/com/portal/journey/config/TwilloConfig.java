package com.portal.journey.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author santhoshkumardurairaj
 *
 */
@Configuration
@ConfigurationProperties("twilio")
public class TwilloConfig {

	/**
	 * accountSid
	 */
	private String accountSid;
	/**
	 * authToken
	 */
	private String authToken;
	/**
	 * trialNumber
	 */
	private String trialNumber;

	public TwilloConfig() {

	    }

	/**
	 * 
	 * @return
	 */
	public String getAccountSid() {
		return accountSid;
	}

	/**
	 * 
	 * @param accountSid
	 */
	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}

	/**
	 * 
	 * @return
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * 
	 * @param authToken
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * 
	 * @return
	 */
	public String getTrialNumber() {
		return trialNumber;
	}

	/**
	 * 
	 * @param trialNumber
	 */
	public void setTrialNumber(String trialNumber) {
		this.trialNumber = trialNumber;
	}
}
