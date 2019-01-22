package com.pluralsight.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {

	@Test(timeout=10000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8080/ride_tracker/rides", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
		}
	}
	
	
	@Test(timeout=10000)
	public void testCreateRide() {
		RestTemplate restTemplate = new RestTemplate();
		
		Ride ride = new Ride();
		ride.setName("Round Valley Ride");
		ride.setDuration(38);
		
		/*put method used to create & update the records
		restTemplate.put("http://localhost:8080/ride_tracker/ride", ride); ride_tracker is the project context name here */
		
		//restTemplate.postForObject(url, request, responseType)
		ride = restTemplate.postForObject("http://localhost:8080/ride_tracker/ride", ride, Ride.class); //getting the ride object back as a response rather than getting null as a response
		
	}
	
}
