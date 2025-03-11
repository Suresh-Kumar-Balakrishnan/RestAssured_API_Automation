package test.RestAssured;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateBookingTest extends BaseTest{
	
	@Test
	public void updateBookingTest()
	{
		// Ref - create booking method has been created in the java main class and calling it here by using inheritance.
		Response responseCreate = createBooking();
		responseCreate.print();
		
		//get Booking ID of the new booking
		int bookingId = responseCreate.jsonPath().getInt("bookingid");
		
		//Update Booking 
		
		JSONObject body = new JSONObject();
		body.put( "firstname", "Senthil");
		body.put( "lastname", "Kumar");
		body.put( "totalprice", 120);
		body.put( "depositpaid", true);
		
		JSONObject bookingdates = new JSONObject();
		bookingdates.put( "checkin", "2025-02-28");
		bookingdates.put( "checkout", "2025-03-03");
		body.put(  "bookingdates", bookingdates);
		body.put("additionalneeds", "Breakfast");
		
		//Get Response 
		// body one need to select object type and it need to convert as a string 
		Response responseUpdate = RestAssured.given(spec).auth().preemptive()
				.basic("admin", "password123").contentType(ContentType.JSON)
				.body(body.toString()).put("/booking/" + bookingId);
		
		responseUpdate.print();
		
		// reference username and password 
		//    "username" : "admin",
	   // "password" : "password123"
		// for this need to modify the above statement -> given().auth().preemptive().basic("username","password")
		
		//Verification & Validation
		//{"bookingid":1816,"booking":{"firstname":"Suresh","lastname":"Kumar","totalprice":150,"depositpaid":true,"bookingdates":{"checkin":"2025-02-28","checkout":"2025-03-03"},"additionalneeds":"Breakfast"}}

		SoftAssert softAssert = new SoftAssert();
		String firstname = responseUpdate.jsonPath().getString("firstname");
		softAssert.assertEquals(firstname, "Senthil", "Firstname is not matching");
		
		String lastname = responseUpdate.jsonPath().getString("lastname");
		softAssert.assertEquals(lastname, "Kumar", "Last name is not mathcing");
		
		int actualPrice = responseUpdate.jsonPath().getInt("totalprice");
		softAssert.assertEquals(actualPrice, 120, "Price doesn't match");
		
		boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
		softAssert.assertTrue(depositpaid, "deposit paid should be true but it not");
		
		String checkin = responseUpdate.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(checkin, "2025-02-28", "Checkin date is not mathced");
		
		String checkout = responseUpdate.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(checkout, "2025-03-03", "Checkout date is not mathced");
		
		
		String additionalneeds = responseUpdate.jsonPath().getString("additionalneeds");
		softAssert.assertEquals(additionalneeds, "Breakfast", "Additional needs is not matched");
		
		softAssert.assertAll();
		
		
	}

}
