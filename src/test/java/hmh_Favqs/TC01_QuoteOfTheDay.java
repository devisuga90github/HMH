package hmh_Favqs;

import org.hamcrest.Matchers;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TC01_QuoteOfTheDay extends BaseClass {

    @Test(priority = 0)
    public void quoteOfDay() {
        try {
            Response response = RestAssured.given().get("/qotd");
            Quote_id = response.jsonPath().getInt("quote.id");
            // Printing the response body
            response.prettyPrint();

            int statusCode = response.statusCode();
            System.out.println("The status code of QuoteOfTheDay is: " + statusCode);
            System.out.println("The QuoteID is: " + Quote_id);
            
            // Checking the status code is 200
            response.then().assertThat().statusCode(Matchers.equalTo(200));

            // Checking there is no error code in responsebody
            response.then().assertThat().body(Matchers.not(Matchers.hasKey("error_code")));
            
            // Log the Quote ID to TestNG report
            Reporter.log("The QuoteID for the Quote of the day : " + Quote_id);
        } 
        
        catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            // Fail the test case explicitly
            assert false : "Test case failed due to exception: " + e.getMessage();
            
        }
    }
}
