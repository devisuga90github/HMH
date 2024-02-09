package hmh_Favqs;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TC05_UnFavQuote extends BaseClass {
    @Test(dependsOnMethods = "hmh_Favqs.TC02_FavQuote.checkfavQuote")
    public void checkUnfavQuote() {
        try {
            // Marking Quote as Favorite=false 
            Response unFavResponse = RestAssured.given().headers(getAddHeaders()).put("/quotes/" + Quote_id + "/unfav");
            unFavResponse.prettyPrint();

            int statusCode = unFavResponse.statusCode();
            System.out.println("The status code of checkUnFavQuote is: " + statusCode);

            // Checking the status code is 200
            unFavResponse.then().assertThat().statusCode(Matchers.equalTo(200));
            
            // Checking there is no error code in responsebody
            unFavResponse.then().assertThat().body(Matchers.not(Matchers.hasKey("error_code")));
            Reporter.log("Validated 200 response code along with the absence of error code in response body");

            // Assert the value of unfavorite from response --If the quote is checked as unFavorite then boolean value will be false in the response
            unFavResponse.then().assertThat().body("user_details.favorite", Matchers.is(false));
            Reporter.log("Validated the response body for user_details.favorite: false");
            
        } 
        
        catch (Exception e) {
            Reporter.log("Exception occurred: " + e.getMessage());
            // Fail the test case explicitly
            Assert.fail("Test case failed due to exception: " + e.getMessage());
        }
    }
}
