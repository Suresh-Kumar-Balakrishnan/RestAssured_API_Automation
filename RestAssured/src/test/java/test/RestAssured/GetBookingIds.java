package test.RestAssured;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBookingIds extends BaseTest {
	
	@Test
	public void BookingId() {
		
		// Note - here i'm gonna try old way of API testing 
		
		//get the response and store
		Response response = RestAssured.given(spec).get("/booking");
		response.print();
		
		//verify the response 
		Assert.assertEquals(response.getStatusCode(), 200 , "Status code should be 200");
		
		//Verify atleast 1 Booking exists 
		List<Integer> BookingIds = response.jsonPath().getList("bookingid");
		Assert.assertFalse(BookingIds.isEmpty(), "Booking Id should not be Empty");
		
		
	}
	
	@Test
	public void getBookingIdWithFilter() {

		//Add Query parameter
		spec.queryParam("firstname","Mary");
		spec.queryParam("lastname","Jones");
		
		//get the response and store
		Response response = RestAssured.given(spec).get("/booking");
		response.print();
		
		//verify the response 
		Assert.assertEquals(response.getStatusCode(), 200 , "Status code should be 200");
		
		//Verify atleast 1 Booking exists 
		List<Integer> BookingIds = response.jsonPath().getList("bookingid");
		Assert.assertFalse(BookingIds.isEmpty(), "Booking Id should not be Empty");
		
	}
}
