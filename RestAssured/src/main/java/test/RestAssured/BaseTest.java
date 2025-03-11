package test.RestAssured;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
	
	public RequestSpecification spec;
	
	@BeforeMethod
	public void setUp()
	{
		 spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com")
				.build();
	}
	
	protected Response createBooking() {
		//create Json body 
		/***
		 *{
    "bookingid": 1,
    "booking": {
        "firstname": "Jim",
        "lastname": "Brown",
        "totalprice": 111,
        "depositpaid": true,
        "bookingdates": {
            "checkin": "2018-01-01",
            "checkout": "2019-01-01"
        },
        "additionalneeds": "Breakfast"
    }
}
		 * 
		 */
		
		JSONObject body = new JSONObject();
		body.put( "firstname", "Suresh");
		body.put( "lastname", "Kumar");
		body.put( "totalprice", 150);
		body.put( "depositpaid", true);
		
		JSONObject bookingdates = new JSONObject();
		bookingdates.put( "checkin", "2025-02-28");
		bookingdates.put( "checkout", "2025-03-03");
		body.put(  "bookingdates", bookingdates);
		body.put("additionalneeds", "Breakfast");
		
		//Get Response 
		// body one need to select object type and it need to convert as a string 
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString()).post("/booking");
		response.print();
		return response;
	}

}
