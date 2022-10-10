package starter;

import com.example.constants.SFTPConnectConstants;
import com.example.helloworld.BulkFileGenerator;
import com.example.helloworld.EmailFileGenerator;
import com.example.sftp.SFTPConnect;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.opencsv.exceptions.CsvException;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.awaitility.Awaitility;
import org.junit.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import starter.constants.URLConstants.*;

import static io.restassured.RestAssured.*;

public class WelcomeEmail {

    public WelcomeEmail() {}

    private String customerId;
    private String policyId;
    private String interactionId;
    private String env;
    private boolean append;
    private String accessToken;
    private String sessionId;

    public static final Map<String, String> customer_url = new HashMap<String, String>() {{
        put("staging", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/TestingAPI/V1/Request?CustomerID=");
        put("boe", "https://cnollc-cnocdh-simulation.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
        put("dev", "https://cnollc-cnocdh-dt1.pegacloud.net/prweb/api/TestingAPI/V1/Request?CustomerID=");
    }};

    public static final Map<String, String> ih_url = new HashMap<String, String>() {{
        put("staging", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
        put("boe", "https://cnollc-cnocdh-simulation.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
        put("dev", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
    }};

    public static final Map<String, String> clickstream_url = new HashMap<String, String>() {{
        put("staging", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/Clickstream/1.1/Insert");
        put("boe", "https://cnollc-cnocdh-dt1.pegacloud.net/prweb/api/Clickstream/1.1/Insert");
        put("dev", "https://cnollc-cnocdh-simulation.pegacloud.net/prweb/api/Clickstream/1.1/Insert");
    }};

    public static final Map<String, String> oauth_url = new HashMap<String, String>() {{
        put("staging", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/PRRestService/oauth2/v1/token");
        put("boe", "https://cnollc-cnocdh-dt1.pegacloud.net/prweb/PRRestService/oauth2/v1/token");
        put("dev", "https://cnollc-cnocdh-simulation.pegacloud.net/prweb/PRRestService/oauth2/v1/token");
    }};

    public static final String jsonBody = "{\n" +
            "\"EventType\": \"EVENT_TYPE\",\n" +
            "\"EventSequenceInSession\": \"516\",\n" +
            "\"VisitorCookieID\": \"9fc57c6e459b48f2abee13eaed7bf010\",\n" +
            "\"VisitorIdentification\": \"CUSTOMER_ID\",\n" +
            "\"VisitorIdentificationStrength\": \"100\",\n" +
            "\"EventTimestamp\": \"DATE.820+00:00\",\n" +
            "\"TransactionName\": \"CPApplication Life Insurance\",\n" +
            "\"SessionKey\": \"SESSION_KEY\",\n" +
            "\"TransactionInstanceID\": \"c855162966154d5b93c42cf47bb8ca10\",\n" +
            "\"ProductName\": \"Permanent Whole Life Insurance\",\n" +
            "\"FirstName\": \"Silviu\",\n" +
            "\"LastName\": \"Stefan\",\n" +
            "\"ProductCode\": \"PRODUCT_ID\",\n" +
            "\"ApplicationC3ID\": \"8a8b40b582ab0df10182ac8bef460114\"\n" +
            "}";

    @Given("Environment is {string}")
    public void environment_is_staging(String env) {
        this.env = env;
    }

    @Given("Policy is generated for {string} customer within last {string} days")
    public void policy_is_generated_for_customer_within_last_days(String isNew, String days) throws JSchException,
            SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-"+days, "GBL", "Y", "N", "Rohit", "Delivered",
                "ACTIVE", "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append),
                Boolean.toString(isNew.equalsIgnoreCase("new"))});
        populateVars(params);
    }

    @Given("Policy is generated for {string} customer with donot email as {string}")
    public void policyIsGeneratedForCustomerWithDonotEmailAs(String isNew, String doNotEmail) throws JSchException,
            SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", "GBL", "Y", doNotEmail, "Rohit", "Delivered",
                "ACTIVE", "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append),Boolean.toString(isNew.equalsIgnoreCase("new")),Boolean.toString(isNew.equalsIgnoreCase("new"))});
        populateVars(params);
    }

    @Given("Policy is generated for {string} customer with status {string}")
    public void policy_is_generated_for_customer_with_status(String isNew, String status) throws JSchException,
            SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", "GBL", "Y", "N", "Rohit", "Delivered",
                status, "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append),Boolean.toString(isNew.equalsIgnoreCase("new"))});
        populateVars(params);
    }

    @Given("Policy is generated for {string} customer after next {string} days")
    public void policy_is_generated_for_customer_after_next_days(String isNew,  String days) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {days, "GBL", "Y", "N", "Rohit", "Delivered",
                "ACTIVE", "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append),Boolean.toString(isNew.equalsIgnoreCase("new"))});
        populateVars(params);
    }

    @Given("Policy is generated for {string} customer for {string} policy after next {string} days")
    public void policy_is_generated_for_customer_for_policy_after_next_days(String isNew, String policy, String days) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {days, policy, "Y", "N", "Rohit", "Delivered",
                "ACTIVE", "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append),Boolean.toString(isNew.equalsIgnoreCase("new"))});
        populateVars(params);
    }

    @Given("Policy is generated for {string} customer for {string} policy with status {string}")
    public void policy_is_generated_for_customer_for_policy_with_status(String isNew, String policy, String status) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", policy, "Y", "N", "Rohit", "Delivered",
                status, "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append),Boolean.toString(isNew.equalsIgnoreCase("new"))});
        populateVars(params);
    }

    @Given("Policy is generated for {string} customer with status {string} within last {string} days")
    public void policy_is_generated_for_customer_with_status_days(String isNew, String status, String days) throws JSchException,
            SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-" + days, "GBL", "Y", "N", "Rohit", "Delivered",
                status, "raghugoud@futureproofai.com", "1", env, Boolean.toString(append),Boolean.toString(isNew.equalsIgnoreCase("new"))});
        populateVars(params);
    }

    @Given("Policy is generated for {string} customer for policy {string} with status {string} within last {string} days")
    public void policy_is_generated_for_customer_for_policy_with_status_days(String isNew, String policy, String status, String days) throws JSchException,
            SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-" + days, policy, "Y", "N", "Rohit", "Delivered",
                status, "raghugoud@futureproofai.com", "1", env, Boolean.toString(append),Boolean.toString(isNew.equalsIgnoreCase("new"))});
        populateVars(params);
    }

    @Given("Policy is generated for {string} customer for {string} policy")
    public void policy_is_generated_for_customer_for_policy(String isNew, String policy) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", policy, "Y", "N", "Rohit", "Delivered", "ACTIVE",
                "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append),Boolean.toString(isNew.equalsIgnoreCase("new"))});
        populateVars(params);
    }

    @Given("Policy is generated for {string} customer with email {string}")
    public void policy_is_generated_for_customer_with_email(String isNew, String email) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", "GBL", "Y", "N", "Rohit",
                "Delivered", "ACTIVE", email, "1" ,env, Boolean.toString(append),Boolean.toString(isNew.equalsIgnoreCase("new"))});
        populateVars(params);
    }

    @Given("Bulk Policy is generated for {int} customers with email {string}")
    public void bulk_policy_is_generated_for_customer_with_email(int num, String email) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", "GBL", "Y", "N", "Rohit",
                "Delivered", "ACTIVE", email, Integer.toString(num), env, Boolean.toString(append),"true"});
        populateVars(params);
    }

    @When("Files are uploaded to SFTP")
    public void files_are_uploaded() throws IOException, JSchException, SftpException {
        SFTPConnect sftpConnect = new SFTPConnect(env);
        try {
            sftpConnect.openSession();
            sftpConnect.uploadFiles();
        }
        finally {
            sftpConnect.disconnectSession();
        }
    }

    @When("Customers and policies are found in CDH")
    public void customers_and_policies_are_found_in_cdh() throws MalformedURLException {
        Awaitility.await().atMost(180, TimeUnit.SECONDS).pollInterval(10, TimeUnit.SECONDS).until(() ->
        {
            final Response body= get(new URL(customer_url.get(env) + customerId));
            System.out.println("id: "+ body.getBody().jsonPath().get("CustomerID") +
                    ", products: " + body.getBody().jsonPath().get("Products"));
            return body.getBody().jsonPath().get("CustomerID") != null && body.getBody().jsonPath().get("Products") != null;
        });
    }

    @When("Customer and policy files are removed from SFTP")
    public void customer_and_policy_files_are_removed_from_sftp() throws JSchException, IOException {
        List<String> files = new ArrayList<>();
        files.add(SFTPConnectConstants.SFTP_CONNECT_WELLSPRING_DIR + SFTPConnectConstants.CUST_FILE_NAME);
        files.add(SFTPConnectConstants.SFTP_CONNECT_WELLSPRING_DIR + SFTPConnectConstants.POLICY_FILE_NAME);
        files.add(SFTPConnectConstants.SFTP_CONNECT_WELLSPRING_DIR + SFTPConnectConstants.CUST_CTL_FILE_NAME);
        files.add(SFTPConnectConstants.SFTP_CONNECT_WELLSPRING_DIR + SFTPConnectConstants.POLICY_CTL_FILE_NAME);

        SFTPConnect sftpConnect = new SFTPConnect(env);
        try {
            sftpConnect.openSession();
            Awaitility.await().atMost(300, TimeUnit.SECONDS).pollInterval(10, TimeUnit.SECONDS).until(() ->
            {
                System.out.println("Files exist");
                return !sftpConnect.ifFilesExist(files);
            });
            System.out.println("Files not exist");
        }
        finally {
            sftpConnect.disconnectSession();
        }
    }


    @Then("Email status is {string} in CDH for {string}")
    public void email_status_is_in_cdh(String status, String label) throws MalformedURLException {
        Awaitility.await().atMost(600, TimeUnit.SECONDS).pollInterval(60, TimeUnit.SECONDS).until(() ->
        {
            List list = when()
                    .get(new URL(ih_url.get(env) + customerId))
                    .then()
                    .extract().response().body().path("OfferHistory.findAll {" +
                            "it -> (it.Outcome == '" + status +"' && it.Label == '" + label +"')" +
                            "}.InteractionID");

            if(list.size() > 0) {
                System.out.println("Interaction id for " + label +": " + interactionId);
                interactionId = (String) list.get(0);
                return true;
            }
            return false;
        });
    }

    @Given("Email status is delivered in CDH for {string} for customer {string}")
    public void email_status_is_delivered_in_cdh_for_for_customer_customer(String label, String customerId) throws MalformedURLException {

        List list = given().log().all()
                .get(new URL(ih_url.get(env) + customerId))
                .then()
                .log().all()
                .extract().response().body().path("OfferHistory.findAll {" +
                        "it -> (it.Outcome == 'DeliverHold' && it.Label == '" + label +"')" +
                        "}.InteractionID");

        if(list.size() > 0) {
            System.out.println("Interaction id for " + label +": " + interactionId);
            this.customerId = customerId;
            this.interactionId = (String) list.get(0);
        } else {
            Assert.fail("No delivered status found for customer " + customerId + " in IH");
        }
    }

    @When("Email {string} file is generated from PCM before {int} days")
    public void email_file_is_generated_from_pcm_before_days(String status, int days) throws JSchException, SftpException, IOException, CsvException {
            EmailFileGenerator.generate(new String[]{customerId, status, interactionId, Integer.toString(days), env});
    }

    @Then("Email status is <cdh_status> in CDH")
    public void email_status_is_cdh_status_in_cdh() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("Email status is {string} in CDH")
    public void email_status_is_in_cdh(String status) throws MalformedURLException, InterruptedException, JSchException {
        Awaitility.await().atMost(600, TimeUnit.SECONDS).pollInterval(10, TimeUnit.SECONDS).until(() ->
        {
            List list = when()
                    .get(new URL(ih_url.get(env) + customerId))
                    .then()
                    .extract().response().body().path("OfferHistory.findAll {" +
                            "it -> (it.Outcome == '" + status  +"' && it.InteractionID == '" + interactionId +"')" +
                            "}.InteractionID");

            System.out.println("list " + list);
            return list.size() > 0;
        });
    }

    @Then("Email status is not delivered in CDH for {string}")
    public void emailStatusIsNotDeliveredInCDHFor(String label) throws MalformedURLException {
        List list = given().log().all()
                .get(new URL(ih_url.get(env) + customerId))
                .then()
                .extract().response().body().path("OfferHistory.findAll {" +
                        "it -> (it.Outcome == 'Pending' && it.Label == '" + label +"')" +
                        "}.InteractionID");

        if(list.size() > 0) {
            Assert.fail("Email sent for " + label);
        }
    }

    @Given("{string} event is triggered within {int} hours")
    public void event_is_triggered_from_celebrus(String eventType, int hours) {
        String body = jsonBody.replace("EVENT_TYPE", eventType)
                        .replace("DATE", getDate(hours))
                        .replace("SESSION_KEY", this.sessionId)
                        .replace("PRODUCT_ID", "SIWL")
                        .replace("CUSTOMER_ID", this.customerId);

        given().log().all().header("authorization", "Bearer " + this.accessToken)
                .body(body)
                .when().post(clickstream_url.get(env))
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Given("{string} event is triggered within {int} hours for product {string}")
    public void event_is_triggered_from_celebrus(String eventType, int hours, String product) {
        String body = jsonBody.replace("EVENT_TYPE", eventType)
                .replace("DATE", getDate(hours))
                .replace("SESSION_KEY", this.sessionId)
                .replace("PRODUCT_ID", product)
                .replace("CUSTOMER_ID", this.customerId);

        given().log().all().header("authorization", "Bearer " + this.accessToken)
                .body(body)
                .when().post(clickstream_url.get(env))
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Given("{string} event is triggered within {int} hours for customer {string}")
    public void event_is_triggered_from_celebrus_customer(String eventType, int hours, String customerId) {
        String body = jsonBody.replace("EVENT_TYPE", eventType)
                .replace("DATE", getDate(hours))
                .replace("SESSION_KEY", this.sessionId)
                .replace("PRODUCT_ID", "SIWL")
                .replace("CUSTOMER_ID", customerId);

        given().log().all().header("authorization", "Bearer " + this.accessToken)
                .body(body)
                .when().post(clickstream_url.get(env))
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    private String getDate(int hours) {
        return LocalDateTime.now().minusHours(hours).format(DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss"));
    }

    @After
    public void log(Scenario scenario){
        System.out.println(scenario.getName() +", " + "customerId: " + customerId +", policyId: " + policyId);
    }

    private void populateVars(String[] params) {
        this.customerId = params[0];
        this.policyId = params[1];
        this.append = true;
    }

    @And("Generate oauth token")
    public void generateOauthToken() {
        this.accessToken = given().log().all().formParam("client_id","35993973342139243088")
                        .formParam("client_secret", "0ACD2A8B0115D2208FFCEDE204383CCC")
                        .formParam("grant_type", "client_credentials")
                        .when()
                        .post(oauth_url.get(env))
                        .then().log().all()
                        .extract().response().body().path("access_token");
    }

    @And("Wait for {string} seconds")
    public void waitFor(String seconds) throws InterruptedException {
        Thread.sleep(Integer.parseInt(seconds)* 1000L);
    }

    @And("Generate session key")
    public void generateSessionKey() {
        Random random = new Random();
        this.sessionId = "";
        random.ints(4,0,999).forEach(num -> {
            this.sessionId = this.sessionId.concat(Integer.toString(num));
        });
    }
}
