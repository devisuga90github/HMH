package hmh_Favqs;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TC03_FavQuote_InvalidData extends BaseClass {
    @Test(dependsOnMethods = "hmh_Favqs.TC01_QuoteOfTheDay.quoteOfDay")
    public void checkFavWithInvalidQuoteID() {
        try {
            // Marking Quote as Favorite with an invalid Quote ID
            int invalidQuoteId = 9999999;
            Response favResponseInvalid = RestAssured.given().headers(getAddHeaders())
                    .put("/quotes/" + (Quote_id + invalidQuoteId) + "/fav");
            favResponseInvalid.prettyPrint();

            int statusCode = favResponseInvalid.statusCode();
            String statusMessage = "The status code of checkFavWithInvalidQuoteID is: " + statusCode;
            System.out.println(statusMessage); 

            // Verify response for invalid Quote ID as per API documentation
            favResponseInvalid.then().assertThat().statusCode(Matchers.equalTo(200));
            favResponseInvalid.then().assertThat().body("error_code", Matchers.equalTo(40));
            favResponseInvalid.then().assertThat().body("message", Matchers.equalTo("Quote not found."));
        } 
        
        catch (AssertionError e) {
            // Handle assertion error
            String errorMessage = "Assertion failed: " + e.getMessage();
            Reporter.log(errorMessage); // Log error message to TestNG report
            Assert.fail(errorMessage); // Mark the test case as failed
        } 
        
        catch (Exception e) {
            // Handle other exceptions
            String errorMessage = "Exception occurred: " + e.getMessage();
            Reporter.log(errorMessage); // Log error message to TestNG report
            Assert.fail(errorMessage); // Mark the test case as failed
        }
    }
}
