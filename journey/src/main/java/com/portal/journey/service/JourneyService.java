package com.portal.journey.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.journey.entity.Journey;
import com.portal.journey.entity.Passenger;
import com.portal.journey.repository.JourneyRepository;

/**
 * 
 * @author santhoshkumardurairaj
 *
 */
@Service
public class JourneyService {
	

	/**
	 * Logger instance
	 */
	private final Logger log = LoggerFactory.getLogger(JourneyService.class);

	/**
	 * Repository Injected
	 */
	@Autowired
	private JourneyRepository journeyRepository;

	/**
	 * 
	 * @param passengerId
	 * @return
	 */
	public Journey getJourneyByPassenger(String passengerId) {
		log.info("getJourneyByPassenger");
		return journeyRepository.loadJourneyByPassengerId(passengerId);
	}

	/**
	 * 
	 * @param passenger
	 * @return
	 */
	public Passenger createPassenger(Passenger passenger) {
		log.info("createPassenger");
		return journeyRepository.save(passenger);
	}

	/**
	 * 
	 * @param id
	 * @param passenger
	 * @return
	 */
	public String updatePassenger(String id, Passenger passenger) {
		log.info("updatePassenger");
		return journeyRepository.updatePassenger(id, passenger);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Passenger findPassenger(String id) {
		log.info("findPassenger");
		return journeyRepository.loadPassengerById(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Journey findJourney(String id) {
		log.info("findJourney");
		return journeyRepository.loadJourneyById(id);
	}

	/**
	 * 
	 * @param journey
	 * @return
	 */
	public Journey createJourney(Journey journey) {
		log.info("createJourney");
		return journeyRepository.saveJourney(journey);
	}

	/**
	 * 
	 * @param id
	 * @param journey
	 * @return
	 */
	public String updateJourney(String id, Journey journey) {
		log.info("updateJourney");
		return journeyRepository.updateJourney(id, journey);
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public List<Passenger> loadPassengerByJourneyDate(String date){
		return journeyRepository.findPassengerByJourneyDate(date);
	}
}
