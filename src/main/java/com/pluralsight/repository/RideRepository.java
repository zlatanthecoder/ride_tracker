package com.pluralsight.repository;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideRepository {

	List<Ride> getRides();

	Ride createRide(Ride ride);
	
	Ride getRide(Integer id); //select One Record

	Ride updateRide(Ride ride); //update 1 record
	
	void updateRides(List<Object[]> pairs); //for Batch Update
	
	void deleteRide(Integer id);
}