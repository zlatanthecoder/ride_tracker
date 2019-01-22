package com.pluralsight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	
	
}
