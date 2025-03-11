package test.RestAssured;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBookingTests extends BaseTest{
	
	@Test
	public void createBookingTest()
	{
		
		
		// Ref - create booking method has been created in the java main class and calling it here by using inheritance.
		Response response = createBooking();
		
		//Verification & Validation
		//{"bookingid":1816,"booking":{"firstname":"Suresh","lastname":"Kumar","totalprice":150,"depositpaid":true,"bookingdates":{"checkin":"2025-02-28","checkout":"2025-03-03"},"additionalneeds":"Breakfast"}}

		SoftAssert softAssert = new SoftAssert();
		String firstname = response.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(firstname, "Suresh", "Firstname is not matching");
		
		String lastname = response.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(lastname, "Kumar", "Last name is not mathcing");
		
		int actualPrice = response.jsonPath().getInt("booking.totalprice");
		softAssert.assertEquals(actualPrice, 150, "Price doesn't match");
		
		boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
		softAssert.assertTrue(depositpaid, "deposit paid should be true but it not");
		
		String checkin = response.jsonPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(checkin, "2025-02-28", "Checkin date is not mathced");
		
		String checkout = response.jsonPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(checkout, "2025-03-03", "Checkout date is not mathced");
		
		
		String additionalneeds = response.jsonPath().getString("booking.additionalneeds");
		softAssert.assertEquals(additionalneeds, "Breakfast", "Additional needs is not matched");
		
		softAssert.assertAll();
		
		
	}

	@Test
	public void createBookingWithPOJOTest()
	{
        // create body with POJO's 
		BookingDates bookingdates = new BookingDates("2025-02-28", "2025-03-03");
		Booking booking = new Booking("Crazy", "Cruze", 200, true , bookingdates, "Hot water");
		
		// After updating the above context need to change the body as booking object 
		
		//Get Response 
		// body one need to select object type and it need to convert as a string 
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking).post("/booking");
		response.print();
		
		// other way starts here 
		// other way of getting the response and verify 
		BookingId bookingid = response.as(BookingId.class);
		
		System.out.println("Request: " + booking.toString() );
		System.out.println("Response: " + bookingid.getBooking().toString());
		//assert - verify all the fields 
		Assert.assertEquals(bookingid.getBooking().toString(), booking.toString());
		
		
		
	}

}
