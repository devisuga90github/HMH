package hmh_Favqs;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC08_GetQuoteContainingWords extends BaseClass {

    @Test(dataProvider = "queryParamProvider", priority = 2)
    public void getQuotesContainingWords(String filter) {
        try {
            Map<String, String> queryParam = new HashMap<String, String>();
            queryParam.put("filter", filter);

            Response response = inputHeaders.queryParams(queryParam).get("/quotes");
            response.prettyPrint();

            int statusCode = response.statusCode();
            System.out.println("The status code for filtering with given word: " + statusCode);

            // Checking the status code is 200
            response.then().assertThat().statusCode(Matchers.equalTo(200));
            
            // Checking there is no error code in response body
            response.then().assertThat().body(Matchers.not(Matchers.hasKey("error_code")));
            Reporter.log("Validated 200 response code along with the absence of error code in response body");
        } 
        
        catch (Exception e) {
            Reporter.log("Exception occurred: " + e.getMessage());
            // Fail the test case explicitly
            Assert.fail("Test case failed due to exception: " + e.getMessage());
        }
    }

    @DataProvider(name = "queryParamProvider")
    public Object[][] queryParamProvider() {
        return new Object[][] {
            {"funny"}, 
            {"humor"},
        };
    }
}
