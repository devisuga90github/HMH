package hmh_Favqs;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC07_ListAllQuotes extends BaseClass {

    @Test(priority = 1)
    public void listQuotes() {
        try {
            Response allQuoteResponse = inputHeaders.get("/quotes");
            allQuoteResponse.prettyPrint();
            int statusCode = allQuoteResponse.statusCode();
            System.out.println("The status code for Listing all Quotes: " + statusCode);

            // Checking the status code is 200
            allQuoteResponse.then().assertThat().statusCode(Matchers.equalTo(200));
            
            // Checking there is no error code in response body
            allQuoteResponse.then().assertThat().body(Matchers.not(Matchers.hasKey("error_code")));
            Reporter.log("Validated 200 response code along with the absence of error code in response body");

            // Asserting the number of quotes returned
            allQuoteResponse.then().assertThat().body("quotes.size()", Matchers.equalTo(25));
            Reporter.log("Validated that there are 25 quotes returned");
        } 
        
        catch (Exception e) {
            Reporter.log("Exception occurred: " + e.getMessage());
            // Fail the test case explicitly
            Assert.fail("Test case failed due to exception: " + e.getMessage());
        }
    }
}
