package com.portal.journey.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.journey.config.TwilloConfig;
import com.portal.journey.entity.Subscribers;
import com.portal.journey.util.SNSData;
import com.portal.journey.util.SMSSender;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

/**
 * 
 * @author santhoshkumardurairaj
 *
 */
@Service
public class TwilloSMSService implements SMSSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(TwilloSMSService.class);

	/**
	 * 
	 */
	private final TwilloConfig twilioConfiguration;

	@Autowired
	public TwilloSMSService(TwilloConfig twilioConfiguration) {
		this.twilioConfiguration = twilioConfiguration;
	}

	/**
	 * This method is for testing purpose
	 */
	@Override
	public void sendSMS(SNSData smsRequest) {
		if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
			PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
			PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
			String message = smsRequest.getMessage();
			MessageCreator creator = Message.creator(to, from, message);
			creator.create();
			LOGGER.info("Send sms {}", smsRequest);
		} else {
			throw new IllegalArgumentException(
					"Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number");
		}

	}

	/**
	 * 
	 * @param information
	 */
	public void sendSMSNotification(Subscribers subscriber) {
		if (isPhoneNumberValid(subscriber.getPhoneNo())) {
			PhoneNumber to = new PhoneNumber(subscriber.getPhoneNo());
			PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
			String message = subscriber.getMessage();
			MessageCreator creator = Message.creator(to, from, message);
			creator.create();
			LOGGER.info("Send sms {}", subscriber);
		} else {
			throw new IllegalArgumentException("Phone number [" + subscriber.getPhoneNo() + "] is not a valid number");
		}
	}

	/**
	 * 
	 * @param phoneNumber
	 * @return
	 */
	private boolean isPhoneNumberValid(String phoneNumber) {
		// TODO: Implement phone number validator
		return true; // Validate in future
	}
}
