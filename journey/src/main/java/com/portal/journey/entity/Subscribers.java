package com.portal.journey.entity;

/**
 * 
 * @author santhoshkumardurairaj
 *
 */
public class Subscribers {

	/**
	 * message
	 */
	private final String message;
	
	/**
	 * phoneNo
	 */
	private final String phoneNo;
	
	public Subscribers(String message, String phoneNo) {
		// TODO Auto-generated constructor stub
		this.message = message;
		this.phoneNo = phoneNo;
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
	public String getPhoneNo() {
		return phoneNo;
	}
}
