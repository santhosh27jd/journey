package com.portal.journey.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.portal.journey.entity.Journey;
import com.portal.journey.entity.Passenger;

/**
 * Repository
 * 
 * @author santhoshkumardurairaj
 *
 */
@Repository
public class JourneyRepository {

	/**
	 * Logger instance
	 */
	private final Logger log = LoggerFactory.getLogger(JourneyRepository.class);

	/**
	 * DynamoDBMapper injection
	 */
	@Autowired
	private DynamoDBMapper dynammoDBMapper;

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Passenger loadPassengerById(String id) {
		log.info("Load Passenger By ID");
		return dynammoDBMapper.load(Passenger.class, id);
	}

	/**
	 * 
	 * @param person
	 * @return
	 */
	public Passenger save(Passenger person) {
		log.info("Create Passenger");
		dynammoDBMapper.save(person);
		return person;
	}

	/**
	 * 
	 * @param journey
	 * @return
	 */
	public Journey saveJourney(Journey journey) {
		log.info("Create Journey");
		dynammoDBMapper.save(journey);
		return journey;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Journey loadJourneyById(String id) {
		log.info("Load Journey By ID");
		return dynammoDBMapper.load(Journey.class, id);
	}

	/**
	 * 
	 * @param passengerId
	 * @return
	 */
	public Journey loadJourneyByPassengerId(String passengerId) {
		log.info("Load Journey By Passenger ID");
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("passengerId", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(passengerId)));
		List<Journey> list = dynammoDBMapper.scan(Journey.class, scanExpression);
		return list.stream().findAny().orElse(null);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public List<Passenger> findPassengerByJourneyDate(String date) {
		log.info("Load Journey By Date");
		// Getting details using journey date
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("journeyDate", new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue().withS(date)));
		List<Journey> list = dynammoDBMapper.scan(Journey.class, scanExpression);

		// getting passenger id list from journey table
		List<String> idList = list.stream().map(jour -> jour.getPassengerId()).collect(Collectors.toList());
		List<AttributeValue> attList = idList.stream().map(el -> new AttributeValue().withS(el))
				.collect(Collectors.toList());

		// getting passenger list
		DynamoDBScanExpression scanExpressionId = new DynamoDBScanExpression();
		scanExpressionId.addFilterCondition("id",
				new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(attList));
		List<Passenger> passengerList = dynammoDBMapper.scan(Passenger.class, scanExpressionId);
		return passengerList;
	}

	/**
	 * 
	 * @param id
	 * @param passenger
	 * @return
	 */
	public String updatePassenger(String id, Passenger passenger) {
		log.info("Update Passenger By ID");
		dynammoDBMapper.save(passenger, new DynamoDBSaveExpression().withExpectedEntry("id",
				new ExpectedAttributeValue(new AttributeValue().withS(id))));
		return id;
	}

	/**
	 * 
	 * @param id
	 * @param journey
	 * @return
	 */
	public String updateJourney(String id, Journey journey) {
		log.info("Load Journey By ID");
		dynammoDBMapper.save(journey, new DynamoDBSaveExpression().withExpectedEntry("id",
				new ExpectedAttributeValue(new AttributeValue().withS(id))));
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public List<Passenger> findAllPassenger() {
		log.info("Load All Passenger ");
		return dynammoDBMapper.scan(Passenger.class, new DynamoDBScanExpression());
	}

	/**
	 * 
	 * @return
	 */
	public List<Journey> findAllJourney() {
		log.info("Load All Journey ");
		return dynammoDBMapper.scan(Journey.class, new DynamoDBScanExpression());
	}
}
