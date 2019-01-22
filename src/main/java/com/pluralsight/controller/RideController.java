package com.pluralsight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pluralsight.error.ServiceError;
import com.pluralsight.model.Ride;
import com.pluralsight.service.RideService;

@Controller
public class RideController {

	@Autowired
	private RideService rideService;
	
	@RequestMapping(value = "/rides", method = RequestMethod.GET)
	public @ResponseBody List<Ride> getRides() {
		return rideService.getRides();
	}
	
	
	//@PutMapping("ride")
	@PostMapping("ride")
	public @ResponseBody Ride createRide(@RequestBody Ride ride) { //@RequestBody here will bind the Ride object which we are sending in Put Request to this object
		return rideService.createRide(ride); 	//@ResponseBody will convert whatever the method is returning into JSON format & return it		
	}
	
	
	@GetMapping("ride/{id}")
	public @ResponseBody Ride getRide(@PathVariable("id") Integer id) {
		return rideService.getRide(id);
	}
	
	
	@PutMapping("ride")
	public @ResponseBody Ride updateRide(@RequestBody Ride ride) {
		return rideService.updateRide(ride);
	}
	
	
	@GetMapping("batch")
	public @ResponseBody Object batchUpdate() { //creating Response as Object just for the sake as RestTemplate donot have any method without responseType
		 rideService.batchUpdate();
		 return null;
	}
	
	@DeleteMapping("delete/{id}")
	public @ResponseBody Object deleteRide(@PathVariable("id") Integer id) {
		rideService.deleteRide(id);
		return null;
	}
	
	@GetMapping("test")
	public @ResponseBody Object test() {
		throw new DataAccessException("Testing Exception thrown") {};
	}
	
	
	
	//here we are grabbing the exception from the database tier & passing back in a more user friendly error message to the client
	@ExceptionHandler(RuntimeException.class) //adding Exception Handler for handling any RuntimeException that is thrown
	public ResponseEntity<ServiceError> handle(RuntimeException ex) {
		ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.OK);
	}
	
	
}
