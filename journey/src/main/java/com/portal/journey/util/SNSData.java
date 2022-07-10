package com.portal.journey.util;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author santhoshkumardurairaj
 *
 */
public class SNSData {

	/**
	 * message
	 */
	@NotBlank
	private final String message;
	@NotBlank
	private final String phoneNumber; // For Testing purpose only, will get phone number from passenger information in DB
	@NotBlank
	private final String journeyDate;

	public SNSData(@JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("message") String message,
			@JsonProperty("journeyDate") String journeyDate) {
		this.message = message;
		this.phoneNumber = phoneNumber;
		this.journeyDate = journeyDate;
	}

	/**
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @return
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "SNSData{" + "phoneNumber=" + phoneNumber +'\'' + ", message='" + message + '\'' + ", journeyDate='" + journeyDate + '\'' + '}';
	}

	/**
	 * 
	 * @return
	 */
	public String getJourneyDate() {
		return journeyDate;
	}
}
