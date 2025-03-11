package test.RestAssured;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartialUpdateBookingTests extends BaseTest{

	@Test
	private void partialUpdateBooking() {
		Response response = createBooking();
		response.print();
		
		int bookingID = response.jsonPath().getInt("bookingid");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put( "firstname", "Senthil");
		
		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2025-02-28");
		bookingdates.put("checkout", "2025-03-05");
		jsonObject.put("bookingdates", bookingdates);
		
		Response patchResponse = RestAssured.given(spec).auth().preemptive().basic("admin","password123")
				.contentType(ContentType.JSON).body(jsonObject.toString())
				.patch("/booking/" + bookingID);
		patchResponse.print();
		
		Assert.assertEquals(patchResponse.getStatusCode(), 200, "status code is not matching");
		
		
		//verify the value 
		SoftAssert softAssert = new SoftAssert();
		String updateFirstName = patchResponse.jsonPath().getString("firstname");
		softAssert.assertEquals(updateFirstName, "Senthil", "Firstname is mismatched");
		
		String lastname = patchResponse.jsonPath().getString("lastname");
		softAssert.assertEquals(lastname, "Kumar", "Last name is not mathcing");
		
		int actualPrice = patchResponse.jsonPath().getInt("totalprice");
		softAssert.assertEquals(actualPrice, 150, "Price doesn't match");
		
		boolean depositpaid = patchResponse.jsonPath().getBoolean("depositpaid");
		softAssert.assertTrue(depositpaid, "deposit paid should be true but it not");
		
		String checkin = patchResponse.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(checkin, "2025-02-28", "Checkin date is not mathced");
		
		String checkout = patchResponse.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(checkout, "2025-03-05", "Checkout date is not mathced");
		
		
		String additionalneeds = patchResponse.jsonPath().getString("additionalneeds");
		softAssert.assertEquals(additionalneeds, "Breakfast", "Additional needs is not matched");
		
		
		
		softAssert.assertAll();
		
	}

}
