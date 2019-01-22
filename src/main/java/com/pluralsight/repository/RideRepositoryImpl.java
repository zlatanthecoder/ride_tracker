package com.pluralsight.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;

@Repository("rideRepository") //repository layer communicate with the database
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate; //injecting bean from xml
	
	
	@Override
	public List<Ride> getRides() { //Select All the Records
		
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
		jdbcTemplate.update("insert into ride (name, duration) values(?,?)", ride.getName(), ride.getDuration()); //use update() method for insert, update & delete  
		
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
		System.out.println(key.intValue());*/
		
		
		
		// KeyHolder keyHolder = new GeneratedKeyHolder(); this KeyHolder is used to get the id of the newly inserted record
		//use of SimpleJdbcInsert to get the id of the newly inserted record by using key.intValue() in the above code
		
		
		return ride;
	}



	@Override
	public Ride getRide(Integer id) { //Select One Record
		
		return jdbcTemplate.queryForObject("select * from ride where id = ?", new RideRowMapper(), id);
	}



	@Override
	public Ride updateRide(Ride ride) {
		
		 jdbcTemplate.update("update ride set name = ?, duration = ? where id = ?", ride.getName(), ride.getDuration(), ride.getId());
		 
		 return ride;
	}



	@Override //for Batch Update
	public void updateRides(List<Object[]> pairs) {
		
		//jdbcTemplate.batchUpdate(String sql, List<Object[]> batchArgs) 
		jdbcTemplate.batchUpdate("update ride set ride_date = ? where id = ?", pairs); //batchUpdate can be used for updation & insertion both
		
		/*
		 * Batch Updation for insertion
		 * https://www.logicbig.com/tutorials/spring-framework/spring-data-access-with-jdbc/spring-jdbc-batch-update.html
		  jdbcTemplate.batchUpdate("insert into PERSON (FIRST_NAME, LAST_NAME, ADDRESS) values (?, ?, ?)",
          Arrays.asList(new Object[]{"Dana", "Whitley", "464 Gorsuch Drive"},
                      new Object[]{"Robin", "Cash", "64 Zella Park"})
                       );
		 */
		
	}
	
	
	@Override
	public void deleteRide(Integer id) {				
		//jdbcTemplate.update("delete from ride where id = ?", id); //update() method is used for deletion
		
		//using NamedParameterJdbcTemplate
		NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id); //creating & Initializing named parameter value map
		
		namedTemplate.update("delete from ride where id = :id", paramMap);
	}
	
}
