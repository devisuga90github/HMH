package hmh_Favqs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {

	public static RequestSpecification inputHeaders;
	public static String Session_ID;
	public static int Quote_id;
	public static Map<String, Object> basicHeaders = new HashMap<String, Object>();

	@BeforeSuite
	public void setUp() {

		// Setting baseURL
		RestAssured.baseURI = "https://favqs.com/api";

		// Initializing Basic Headers for Content-Type and Authorization
		basicHeaders.put("Content-Type", "application/json");
		basicHeaders.put("Authorization", "Token token=24ed07e2ce15c41a031853e7ef3ca472");

		File file = new File("./src/test/resources/Login Credentials.json");
		inputHeaders = RestAssured.given().headers(basicHeaders);
		
		try {
			Response response = inputHeaders.when().body(file).post("/session");
			// Getting Dynamic Session ID and storing in the map as addHeaders
			Session_ID = response.jsonPath().getString("User-Token");
		} 
		
		catch (Exception e) {
			System.out.println("Exception occurred during session creation: " + e.getMessage());
		}
	}

	// adding SessionID to basic headers.
	public static Map<String, Object> getAddHeaders() {
		Map<String, Object> addHeaders = new HashMap<String, Object>(basicHeaders);
		addHeaders.put("User-Token", Session_ID);
		return addHeaders;
	}

	// destroy user session

	@AfterSuite
	public void destroySession() {
	    try {
	        Response deleteSession = RestAssured.given().headers(getAddHeaders()).delete("/session");

	        // Assert
	        try {
	            deleteSession.then().assertThat().statusCode(Matchers.equalTo(200));
	            deleteSession.then().assertThat().body("message", Matchers.equalTo("User logged out."));
	            deleteSession.then().assertThat().body(Matchers.not(Matchers.hasKey("error_code")));
	        } 
	        
	        catch (AssertionError e) {
	            System.out.println("Assertion error occurred for status code: " + e.getMessage());
	        }
	       
	    } 
	    
	    catch (Exception e) {
	        System.out.println("Exception occurred during session destruction: " + e.getMessage());
	    }
	}
}
