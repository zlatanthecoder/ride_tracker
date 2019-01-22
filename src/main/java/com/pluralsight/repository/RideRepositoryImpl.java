package com.pluralsight.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;

@Repository("rideRepository") //repository layer communicate with the database
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate; //injecting bean from xml
	
	
	@Override
	public List<Ride> getRides() {
		
/*		return jdbcTemplate.query("select * from ride", new RowMapper<Ride>() { //using anonymous inner class for implementing RowMapper

			@Override //this is just the template method design pattern
			public Ride mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Ride ride = new Ride();
				ride.setId(rs.getInt("id")); //getting the value of the column name"id" form the resultSet & setting the value in ride object
				ride.setName(rs.getString("name"));
				ride.setDuration(rs.getInt("duration"));
				
				return ride; //it add the ride object iteratively into an ArrayList & return List<Ride>
			}
			
		}); */
		
		//Adding Externalize RowMapper for Ride object
		return jdbcTemplate.query("select * from ride", new RideRowMapper());
		
						
	}

	
	
	@Override
	public Ride createRide(Ride ride) {
		//jdbcTemplate.update("insert into ride (name, duration) values(?,?)", ride.getName(), ride.getDuration()); //use update() method for insert, update & delete  
		
/*      SimpleJdbcInsert Approach
  		-------------------------
  		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
		List<String> columns = new ArrayList();
		columns.add("name"); //"name" & "duration" is the column name in the table ride
		columns.add("duration");
		
		insert.setTableName("ride");
		insert.setColumnNames(columns);
		insert.setGeneratedKeyName("id"); //setting the primary key column name
		
		//setting the data in the hashmap
		HashMap<String, Object> dataMap = new HashMap();
		dataMap.put("name", ride.getName());
		dataMap.put("duration", ride.getDuration());
		
		Number key = insert.executeAndReturnKey(dataMap);
		System.out.println(key);*/
		
		
		
		// KeyHolder keyHolder = new GeneratedKeyHolder(); this KeyHolder is used to get the id of the newly inserted record
		
		
		return ride;
	}
	
}
