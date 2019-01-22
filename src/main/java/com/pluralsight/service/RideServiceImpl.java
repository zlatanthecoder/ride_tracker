package com.pluralsight.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;

@Service("rideService")
public class RideServiceImpl implements RideService {

	@Autowired
	private RideRepository rideRepository;
	
	@Override
	public List<Ride> getRides() {
		return rideRepository.getRides();
	}
	
	@Override
	public Ride createRide(Ride ride) {
		return rideRepository.createRide(ride);		
	}

	@Override
	public Ride getRide(Integer id) {		
		return rideRepository.getRide(id);
	}

	@Override
	public Ride updateRide(Ride ride) {		
		return rideRepository.updateRide(ride);
	}

	@Override
	public void batchUpdate() {
		List<Ride> rides = rideRepository.getRides(); //getting all the rides
		
		List<Object[]> pairs = new ArrayList<>(); //creating a name-value pairs
		
		rides.forEach(ride-> {
			Object[] temp = {new Date(), ride.getId()}; //creating the name value pairs & order is mattered because in update query 1st ? is for ride_date value & 2nd ? is for id
			pairs.add(temp);
		});
		
		rideRepository.updateRides(pairs);
		
		
	}
	
	
	@Override
	public void deleteRide(Integer id) {
		 rideRepository.deleteRide(id);
	}
	
}
