package test.RestAssured;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class HealthCheckTest extends BaseTest {
	
	@Test
	public void CheckTest()
	{
		//new/simplest way of specification
		//baseURI = "https://restful-booker.herokuapp.com";
		
		// or we can use the old methods
		//RequestSpecification spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com")
			//	.build();
		
	/**	given()
		.when()
		.get("/ping")
		.then()
		.assertThat()
		.statusCode(201);
		**/
		
		//oldway 
		given(spec)
	   .when()
		   .get("/ping")
	   .then()
		   .assertThat()
		   .statusCode(201);
	}

	@Test
	public void headersAndCookiesTest()
	{
		//manually pass cookies and headers
		// other way create cookies and headers before value
		
		Header someHeader = new Header("some_name", "some_value");
		spec.header(someHeader);
		
		Cookie someCookie = new Cookie.Builder("Some cookie","Some cookies value").build();
		spec.cookie(someCookie);
		
		//way 1
		Response response1 = RestAssured.given(spec)
				.cookie("Test cookie name", "Test cookie value")
				.header("TestHeader name", "Test Header Value")
				.log().all()
				.get("/ping");
		//------------way 1 ends 
		
		Response response = RestAssured.given(spec).get("/ping");
		
		//get headers
		Headers headers = response.getHeaders();
		System.out.println("Headers : " + headers);
		
		//single header values 
		Header serverHeader1 = headers.get("Server");
		System.out.println(serverHeader1.getName() + ": " +serverHeader1.getValue());
		
	    // other way
		String ServerHeader2 = response.getHeader("Server");
		System.out.println("Server : " + ServerHeader2);
		
		//get Cookies
		Cookies cookies = response.getDetailedCookies();
		System.out.println("Cookies : " + cookies);
		
	}
}
