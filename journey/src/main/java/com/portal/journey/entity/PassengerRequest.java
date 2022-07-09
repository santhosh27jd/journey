package com.portal.journey.entity;


import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author santhoshkumardurairaj
 *
 */
@Data
public class PassengerRequest {
	/**
	 * Input Param passenger_id
	 */
	@JsonProperty("passenger_id")
	private String passengerId;

	/**
	 * Input Param origin_location
	 */
	@JsonProperty("origin_location")
	private String originLocation;
	
	/**
	 * Input Param destination_location
	 */
	@JsonProperty("destination_location")
	private String destinationLocation;
	
	/**
	 * Input Param departure_time
	 */
	@JsonProperty("departure_time")
	private String departureTime;

	public Optional<String>  getPassengerId() {
		return Optional.ofNullable(passengerId);
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public Optional<String> getOriginLocation() {
		return Optional.ofNullable(originLocation);
	}

	public void setOriginLocation(String originLocation) {
		this.originLocation = originLocation;
	}

	public Optional<String> getDestinationLocation() {
		return Optional.ofNullable(destinationLocation);
	}

	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public Optional<String> getDepartureTime() {
		return Optional.ofNullable(departureTime);
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

}
