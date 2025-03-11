package test.RestAssured;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class DeleteBookingTest extends BaseTest{
	
	@Test
	public void deleteBooking()
	{
		Response response = createBooking();
		response.print();
		
		int BookingID = response.jsonPath().getInt("bookingid");
		
		//delete booking 
		
		Response deletebooking = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
				.delete("/booking/" + BookingID);
	
		deletebooking.print();
		
	//verify response status 
		Assert.assertEquals(deletebooking.getStatusCode(), 201, "status code is not matching");
		
		
		//Get Booking 
		Response getResponse = RestAssured.given(spec).get("/booking/" + BookingID);
	    getResponse.print();
	    
	    Assert.assertEquals(getResponse.getBody().asString(), "Not Found", "Should be not Found but it has a value ");
	
	}

}
