package test.RestAssured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class GetBookingTest extends BaseTest {
	
	@Test
	public void BookingId() {
		
		//currently we are using the public api - it could be updated and assertion will be failed later
		
		//using new way
		baseURI = "https://restful-booker.herokuapp.com";
		
		given()
		.when()
		.get("/booking/8")
		.then()
		.assertThat()
		.statusCode(200);
		
		
		
		// Note - here i'm gonna try old way of API testing 
		//create booking
		Response responseCreate = createBooking();
		responseCreate.print();
		
		//get Booking ID of the new booking
		int bookingId = responseCreate.jsonPath().getInt("bookingid");
		
		//set path parameter
		spec.pathParam("bookingId", bookingId);
		
		//get the response and store
		Response response = RestAssured.given(spec).get("/booking/{bookingId}");
		response.print();
		
		//verify the response 
		Assert.assertEquals(response.getStatusCode(), 200 , "Status code should be 200");
		
		//start asserting 
		// Assert --> If any fails it will stop 
		// SoftAssert --> If any fails it still continues but need to add the statement at end 
		
		// sample - {"firstname":"Susan","lastname":"Jackson","totalprice":658,"depositpaid":true,"bookingdates":{"checkin":"2022-09-01","checkout":"2023-05-05"}}
		
		//verify all fields 
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = response.jsonPath().getString("firstname");
		softAssert.assertEquals(actualFirstName, "Suresh", "Firstname in response not matched");
		
		String actualLastName = response.jsonPath().getString("lastname");
		softAssert.assertEquals(actualLastName, "Kumar", "Lastname in response not mathced");
		
		int actualPrice = response.jsonPath().getInt("totalprice");
		softAssert.assertEquals(actualPrice, 150, "Price doesn't match");
		
		Boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
		softAssert.assertTrue(depositpaid, "depositpaid is not matching");
		
		String bookingdates = response.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(bookingdates, "2025-02-28", "Booking dates are not amtching");
		
		String bookingdatescheckout = response.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(bookingdatescheckout, "2025-03-03", "Booking dates are not amtching");
		
		softAssert.assertAll();
			
		
	}
	
	@Test
	public void BookingIdXMLTest() {
		
		System.out.println("********Inside XML method*******");

					
		// Note - here i'm gonna try old way of API testing 
		//create booking
		Response responseCreate = createBooking();
		responseCreate.print();
		
		//get Booking ID of the new booking
		int bookingId = responseCreate.jsonPath().getInt("bookingid");
		
		//set path parameter
		spec.pathParam("bookingId", bookingId);
		
		//get the response and store
		Header xml = new Header("Accept","application/xml");
		spec.header(xml);
		Response response = RestAssured.given(spec).get("/booking/{bookingId}");
		response.print();
		
		//verify the response 
		Assert.assertEquals(response.getStatusCode(), 200 , "Status code should be 200");
		
		//start asserting 
		// Assert --> If any fails it will stop 
		// SoftAssert --> If any fails it still continues but need to add the statement at end 
		
		// sample - {"firstname":"Susan","lastname":"Jackson","totalprice":658,"depositpaid":true,"bookingdates":{"checkin":"2022-09-01","checkout":"2023-05-05"}}
		
		//verify all fields 
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = response.xmlPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstName, "Suresh", "Firstname in response not matched");
		
		String actualLastName = response.xmlPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastName, "Kumar", "Lastname in response not mathced");
		
		int actualPrice = response.xmlPath().getInt("booking.totalprice");
		softAssert.assertEquals(actualPrice, 150, "Price doesn't match");
		
		Boolean depositpaid = response.xmlPath().getBoolean("booking.depositpaid");
		softAssert.assertTrue(depositpaid, "depositpaid is not matching");
		
		String bookingdates = response.xmlPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(bookingdates, "2025-02-28", "Booking dates are not amtching");
		
		String bookingdatescheckout = response.xmlPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(bookingdatescheckout, "2025-03-03", "Booking dates are not amtching");
		
		softAssert.assertAll();
			
		
	}


}
