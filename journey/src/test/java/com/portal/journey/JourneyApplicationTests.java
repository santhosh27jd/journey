package com.portal.journey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.portal.journey.controller.JourneyController;
import com.portal.journey.entity.Journey;
import com.portal.journey.entity.Passenger;
import com.portal.journey.repository.JourneyRepository;

@SpringBootTest
class JourneyApplicationTests {

	@Autowired
	JourneyController journeyController;

	private JourneyRepository journeyRepository = Mockito.mock(JourneyRepository.class);

	@Test
	void contextLoads() {
		assertThat(journeyController).isNotNull();
	}

	@Test
	void createPassengerCheck() {
		Passenger input = new Passenger();
		input.setFirstName("Test");
		input.setLastName("TestName");
		input.setCreatedBy("Testuser");
		input.setCreatedDateTime(Date.from(Instant.now()));
		input.setAddress("Address");
		when(journeyRepository.save(any(Passenger.class))).thenReturn(input);
		Passenger passenger = journeyRepository.save(input);
		assertThat(passenger).isNotNull();
	}

	@Test
	void createJourneyCheck() {
		Journey input = new Journey();
		input.setPassengerId("529c4da5-8e57-421d-a1e1-3f2a1ac2ed03");
		input.setOriginLocation("TestName");
		input.setDestinationLocation("Testuser");
		input.setCreatedDateTime(Date.from(Instant.now()));
		input.setCreatedBy("Address");
		input.setDepartureTime(Timestamp.from(Instant.now()));
		Journey journey = journeyRepository.saveJourney(input);
		assertThat(journey).isNotNull();
	}

	@Test
	void updateJourney() {
		Journey input = new Journey();
		input.setId("9a7bdc8e-e018-4452-82a8-e3aff88755d2");
		input.setPassengerId("529c4da5-8e57-421d-a1e1-3f2a1ac2ed03");
		input.setOriginLocation("TestName");
		input.setDestinationLocation("Testuser");
		input.setCreatedDateTime(Date.from(Instant.now()));
		input.setCreatedBy("Address");
		input.setDepartureTime(Timestamp.from(Instant.now()));
		String id = journeyRepository.updateJourney(input.getId(),input);
		assertThat(id).isNotNull();
	}

}
