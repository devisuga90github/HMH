package hmh_Favqs;

import org.hamcrest.Matchers;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TC02_FavQuote extends BaseClass {

	@Test(dependsOnMethods = "hmh_Favqs.TC01_QuoteOfTheDay.quoteOfDay")
	public void checkfavQuote() {
		try {
			// Marking Quote as Favorite=true
			Response favResponse = RestAssured.given().headers(getAddHeaders()).put("/quotes/" + Quote_id + "/fav");
			favResponse.prettyPrint();

			int statusCode = favResponse.statusCode();
			System.out.println("The status code of checkfavQuote is: " + statusCode);

			// Checking the status code is 200
			favResponse.then().assertThat().statusCode(Matchers.equalTo(200));

			// Checking there is no error code in response body
			favResponse.then().assertThat().body(Matchers.not(Matchers.hasKey("error_code")));
			Reporter.log("Validated 200 response code along with the absence of error code in response body");

			// Assert the value of favorite from response --If the quote is checked as favorite is true
			favResponse.then().assertThat().body("user_details.favorite", Matchers.is(true));
			Reporter.log("Validated the response body for user_details.favorite: true");

		} 
		
		catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
			// Fail the test case explicitly
			assert false : "Test case failed due to exception: " + e.getMessage();
		}
	}
}
