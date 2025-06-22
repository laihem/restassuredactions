package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;

public class RestAssuredExampleTest {

    private static ExtentReports extent;
    private static ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-reports/report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetPostById() {
        test = extent.createTest("GET /posts/1");

        Response response = RestAssured
                .given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .extract().response();

        test.info("Response Body: " + response.getBody().asPrettyString());
        test.pass("Test passed successfully");
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}
