package com.portal.journey.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.portal.journey.exception.JourneyCustomException;

/**
 * 
 * @author santhoshkumardurairaj
 *
 */
public class JSONReader {

	/**
	 * JSONReader static instance
	 */
	private static JSONReader JSONREADER = null;

	/**
	 * Logger instance
	 */
	private final Logger log = LoggerFactory.getLogger(JSONReader.class);

	/**
	 * Best journey JSON string
	 */
	private String bestJourneyDetails;

	/**
	 * Constructor implementation
	 */
	private JSONReader() {

	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public long parseJsonAndFindMinDuraiton(Object obj) {
		log.info("Reading JSON ");

		// Parse JSON Object from API Response
		setBestJourneyDetails(null);
		long minDur = Integer.MAX_VALUE;
		Map<Long, String> legDetails = new HashMap<>();
		// Read JSON file
		JSONArray trippifyResponseArray = (JSONArray) obj;
		for (Object object : trippifyResponseArray) {
			JSONObject json = (JSONObject) object;
			JSONArray legJson = (JSONArray) json.get("legs");
			boolean carTravel = true;
			for (Object legObj : legJson) {
				JSONObject legStat = (JSONObject) legObj;
				JSONObject legStatObj = (JSONObject) legStat.get("legStats");
				String travelMode = (String) legStatObj.get("travelMode");
				log.info("TRAVEL MODE IS WALK OR RAIL ");
				if (travelMode.equals(ConstantUtil.RAIL) || travelMode.equals(ConstantUtil.WALK)) {
					carTravel = false;
				} else {
					break;
				}
			}
			if (!carTravel) {
				long duration = 0;
				Long startTime = (long) json.get("startTime");
				Long endTime = (long) json.get("endTime");
				Timestamp startTimeStamp = new Timestamp(startTime);
				Timestamp endTimeStamp = new Timestamp(endTime);
				Instant startIns = startTimeStamp.toInstant();
				Instant endIns = endTimeStamp.toInstant();
				Duration dur = Duration.between(startIns, endIns);
				duration = dur.toMinutes();
				legDetails.put(duration, json.toJSONString());
				minDur = Math.min(minDur, duration);
			}

		}
		// Best journey details
		setBestJourneyDetails(legDetails.get(minDur));
		return minDur;
	}

	/**
	 * 
	 * @param data
	 */
	public long findMinDuration(String data) {
		log.info("Finding Minimum Duration ");
		long minDur = Integer.MAX_VALUE;
		JSONParser jsonParser = new JSONParser();
		File file = new File(data);
		try (FileReader reader = new FileReader(file)) {
			// Read JSON file
			log.info("Reading JSON ");
			Object obj = jsonParser.parse(reader);
			minDur = parseJsonAndFindMinDuraiton(obj);
		} catch (FileNotFoundException e) {
			log.info("File not Found ");
			throw new JourneyCustomException("FileNotFoundException");
		} catch (IOException e) {
			log.info("No Valid Parameters ");
			throw new JourneyCustomException("IOException");
		} catch (ParseException e) {
			log.info("Unable to Parse ");
			throw new JourneyCustomException("ParseException");
		}
		// Returning minimum duration in minutes
		return minDur;
	}

	/**
	 * JSONReader instance
	 * 
	 * @return
	 */
	public static JSONReader getInstance() {
		if (JSONREADER == null) {
			return new JSONReader();
		}
		return JSONREADER;
	}

	/**
	 * 
	 * @return
	 */
	public String getBestJourneyDetails() {
		return bestJourneyDetails;
	}

	/**
	 * 
	 * @param bestJourneyDetails
	 */
	public void setBestJourneyDetails(String bestJourneyDetails) {
		this.bestJourneyDetails = bestJourneyDetails;
	}
}
