package com.portal.journey.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.portal.journey.entity.Journey;
import com.portal.journey.entity.Passenger;
import com.portal.journey.entity.PassengerRequest;
import com.portal.journey.exception.JourneyRunTimeException;
import com.portal.journey.service.JourneyService;
import com.portal.journey.util.ConstantUtil;
import com.portal.journey.util.JSONReader;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 * 
 * @author santhoshkumardurairaj
 *
 */
@RestController
@Slf4j
@RequestMapping("/plan/journey")
public class JourneyController {

	/**
	 * Logger instance
	 */
	private final Logger log = LoggerFactory.getLogger(JourneyController.class);

	/**
	 * JSONReader
	 */
	private final JSONReader jsonReader = JSONReader.getInstance();

	/**
	 * RestTemplate Injection
	 */
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * JourneyService Injection
	 */
	@Autowired
	private JourneyService journeyService;

	/**
	 * 
	 * @param passengerId
	 * @param originLocation
	 * @param destinationLocation
	 * @param departureTime
	 * @throws IOException
	 */
	@PostMapping
	public ResponseEntity<Object> plan(@RequestBody PassengerRequest passengerReq) throws IOException {
		long minDuration = 0;
		log.info("Passenger requesting for journey ", passengerReq.getPassengerId());
		// validating passenger-id
		passengerReq.getPassengerId().orElseThrow(JourneyRunTimeException::new);

		// validating origin
		passengerReq.getOriginLocation().orElseThrow(JourneyRunTimeException::new);

		// validating destination
		passengerReq.getDestinationLocation().orElseThrow(JourneyRunTimeException::new);

		// Request to External API
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			httpHeaders.add("x-api-key", ""); // Need to get from Authorized person
			HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(httpHeaders);
			ResponseEntity<JSONObject> response = restTemplate.exchange(ConstantUtil.EXTERNAL_API + "?origin="
					+ passengerReq.getOriginLocation() + "&destination=" + passengerReq.getDestinationLocation(),
					HttpMethod.GET, entity, JSONObject.class);
			minDuration = jsonReader.parseJsonAndFindMinDuraiton(response);
			log.info("Minimum duration in minutes is calculated", minDuration);
		} catch (Exception ex) {
			// API not connected, using Moc DATA
			log.info("API is not connected");
			if (ex instanceof ResourceAccessException) {
				Resource resource = new ClassPathResource(ConstantUtil.FILENAME);
				minDuration = jsonReader.findMinDuration(resource.getFile().getPath());
				log.info("Minimum duration in minutes", minDuration);
			}

		}

		// GET Journey Details from the passenger id
		Journey journeyDetail = journeyService.getJourneyByPassenger(passengerReq.getPassengerId().get());
		if (journeyDetail != null) {
			journeyDetail.setDurationTime(minDuration); // Minimum duration time in minutes
			journeyDetail.setBestOptionRoute(jsonReader.getBestJourneyDetails()); // Leg details of the best route
			// Save BEST Route data and time.
			String journeyId = journeyService.updateJourney(journeyDetail.getId(), journeyDetail);
			if (journeyId != null) {
				log.info("Best option journey details is updated", journeyId);
			}

		}
		log.info("Journey request is planned successfully");
		return new ResponseEntity<>("Journey request is planned successfully", HttpStatus.OK);

	}

	/**
	 * 
	 * @param passenger
	 * @return
	 */
	@PostMapping("/add")
	public ResponseEntity<Object> create(@RequestBody Passenger passenger) {
		log.info("Adding Passenger");
		passenger.setCreatedBy(ConstantUtil.USER);
		Date createTime = Date.from(Instant.now());
		passenger.setCreatedDateTime(createTime);
		journeyService.createPassenger(passenger);
		log.info("Passenger is created successfully");
		return new ResponseEntity<>("Passenger is created successfully", HttpStatus.OK);
	}

	/**
	 * 
	 * @param journey
	 * @return
	 */
	@PostMapping("/addJourney")
	public ResponseEntity<Object> createJourney(@RequestBody Journey journey) {
		log.info("Creating Journey");
		journey.setCreatedBy(ConstantUtil.USER);
		Date createTime = Date.from(Instant.now());
		journey.setCreatedDateTime(createTime);
		journeyService.createJourney(journey);
		log.info("Journey is created successfully");
		return new ResponseEntity<>("Journey is created successfully", HttpStatus.OK);
	}

	/**
	 * 
	 * @param journey
	 * @return
	 */
	@PutMapping("/updateJourney")
	public ResponseEntity<Object> updateJourney(@RequestBody Journey journey) {
		log.info("updating Journey");
		journey.setUpdatedBy(ConstantUtil.USER);
		Date createTime = Date.from(Instant.now());
		journey.setUpdatedDateTime(createTime);
		String updateId = journeyService.updateJourney(journey.getId(), journey);
		log.info("Journey is updated successfully ");
		return new ResponseEntity<>("Journey is updated successfully " + updateId, HttpStatus.OK);

	}
}
