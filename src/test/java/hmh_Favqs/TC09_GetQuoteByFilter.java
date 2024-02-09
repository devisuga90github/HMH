package hmh_Favqs;

import java.util.HashMap;
import java.util.Map;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class TC09_GetQuoteByFilter extends BaseClass {

	@Test(dataProvider = "queryParamsProvider", priority = 3)
	public void getQuotesWithTags(String filter, String type) {
		try {
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("filter", filter);
			queryParams.put("type", type);

			Response response = inputHeaders.queryParams(queryParams).get("/quotes");
			response.prettyPrint();

			int statusCode = response.statusCode();
			System.out.println("The status code for displaying Quoted with filter and Type: " + statusCode);

			// Checking the status code is 200
			response.then().assertThat().statusCode(Matchers.equalTo(200));
			
			response.then().assertThat().body(Matchers.not(Matchers.hasKey("error_code")));
			Reporter.log("Validated 200 response code along with the absence of error code in response body");

			
		} 
		
		catch (Exception e) {
			Reporter.log("Exception occurred: " + e.getMessage());
			// Fail the test case explicitly
			Assert.fail("Test case failed due to exception: " + e.getMessage());
		}
	}

	@DataProvider(name = "queryParamsProvider")
	public Object[][] queryParamsProvider() {
		return new Object[][] { 
			{ "funny", "tag" }, 
			{ "Nassim Taleb", "author" }, 
			{ "gose", "user" }
			};
	}
}
