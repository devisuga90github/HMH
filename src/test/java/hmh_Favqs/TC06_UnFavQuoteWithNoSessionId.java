package hmh_Favqs;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TC06_UnFavQuoteWithNoSessionId extends BaseClass {
	@Test(dependsOnMethods = "hmh_Favqs.TC02_FavQuote.checkfavQuote")
	public void checkUnfavQuote() {
		try {
			// Marking Quote as Favorite=false with no Session ID. Passing just basicHeaders
			Response unFavResponse = RestAssured.given().headers(basicHeaders).put("/quotes/" + Quote_id + "/unfav");
			unFavResponse.prettyPrint();

			int statusCode = unFavResponse.statusCode();
			System.out.println("The status code of checkUnFavQuoteWithNoSessionID is: " + statusCode);

			// Checking the status code is 200
			unFavResponse.then().assertThat().statusCode(Matchers.equalTo(200));

			// Checking there is error code 20 in responsebody
			unFavResponse.then().assertThat().body("error_code", Matchers.equalTo(20)).body("message",
					Matchers.equalTo("No user session found."));
			Reporter.log("Validated 200 response code along with the error code in response body"
					+ "error_code: 20, message: No user session found.");
		} 
		
		catch (Exception e) {
			Reporter.log("Exception occurred: " + e.getMessage());
			// Fail the test case explicitly
			Assert.fail("Test case failed due to exception: " + e.getMessage());
		}
	}
}
