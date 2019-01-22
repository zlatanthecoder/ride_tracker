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
		ride.setName("SobTrail Valley Ride");
		ride.setDuration(38);
		
		/*put method used to create & update the records
		restTemplate.put("http://localhost:8080/ride_tracker/ride", ride); ride_tracker is the project context name here */
		
		//restTemplate.postForObject(url, request, responseType)
		ride = restTemplate.postForObject("http://localhost:8080/ride_tracker/ride", ride, Ride.class); //getting the ride object back as a response rather than getting null as a response
		
	}
	
	
	@Test
	public void testGetRide() {
		RestTemplate restTemplate = new RestTemplate();
		
		//restTemplate.getForObject(url, responseType)
		Ride ride = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/2", Ride.class);
		
		System.out.println(ride);
	}
	
	
	@Test
	public void testUpdateRide() {
		RestTemplate restTemplate = new RestTemplate();

		//restTemplate.getForObject(url, responseType)
		Ride ride = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/2", Ride.class); //retrieving object based on id
		
		ride.setDuration(ride.getDuration()+1); //setting the new value
		
		//restTemplate.put(url, request);
		restTemplate.put("http://localhost:8080/ride_tracker/ride", ride); //updating the new value through put method. sending updated ride object in request body
		
		System.out.println(ride);
	}
	
	
	@Test
	public void testBatchUpdate() { //Batching is used to update multiple rows at one go
		/* below query has been run in mysql for Batch update
		 * alter table ride add ride_date datetime after duration
		 */
		
		RestTemplate restTemplate = new RestTemplate();
		
		//restTemplate.getForObject(url, responseType)
		restTemplate.getForObject("http://localhost:8080/ride_tracker/batch", Object.class); //adding response just for the sake as we don't have any method without the responseType
		
		
	}
	
	
	@Test
	public void testDeleteRide() {
		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.delete("http://localhost:8080/ride_tracker/delete/2");
	}
	

	@Test
	public void testException() {
		RestTemplate restTemplate = new RestTemplate();
		
		//restTemplate.getForObject(url, responseType)		
		restTemplate.getForObject("http://localhost:8080/ride_tracker/test", Ride.class);
	}
}
