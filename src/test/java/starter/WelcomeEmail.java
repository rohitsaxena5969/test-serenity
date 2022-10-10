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
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.awaitility.Awaitility;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;

public class WelcomeEmail {

    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeEmail.class);

    public WelcomeEmail() {}

    private static final Map<String, String> customer_url = new HashMap<String, String>() {{
        put("staging", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/TestingAPI/V1/Request?CustomerID=");
        put("boe", "https://cnollc-cnocdh-simulation.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
        put("dev", "https://cnollc-cnocdh-dt1.pegacloud.net/prweb/api/TestingAPI/V1/Request?CustomerID=");
    }};

    private static final Map<String, String> ih_url = new HashMap<String, String>() {{
        put("staging", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
        put("boe", "https://cnollc-cnocdh-simulation.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
        put("dev", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
    }};

    private String customerId;
    private String policyId;
    private String interactionId;
    private String env;
    private boolean append;

    @Given("Environment is {string}")
    public void environment_is_staging(String env) {
        this.env = env;
    }

    @Given("Policy is generated for customer within last {string} days")
    @Step
    public void policy_is_generated_for_customer_within_last_days(String days) throws JSchException,
            SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-"+days, "GBL", "Y", "N", "Rohit", "Delivered",
                "ACTIVE", "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append)});
        populateVars(params);
    }

    @Given("Policy is generated for customer with status {string}")
    public void policy_is_generated_for_customer_with_status(String status) throws JSchException,
            SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", "GBL", "Y", "N", "Rohit", "Delivered",
                status, "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append)});
        populateVars(params);
    }

    @Given("Policy is generated for customer after next {string} days")
    public void policy_is_generated_for_customer_after_next_days(String days) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {days, "GBL", "Y", "N", "Rohit", "Delivered",
                "ACTIVE", "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append)});
        populateVars(params);
    }

    @Given("Policy is generated for customer for {string} policy after next {string} days")
    public void policy_is_generated_for_customer_for_policy_after_next_days(String policy, String days) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {days, policy, "Y", "N", "Rohit", "Delivered",
                "ACTIVE", "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append)});
        populateVars(params);
    }

    @Given("Policy is generated for customer for {string} policy with status {string}")
    public void policy_is_generated_for_customer_for_policy_with_status(String policy, String status) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", policy, "Y", "N", "Rohit", "Delivered",
                status, "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append)});
        populateVars(params);
    }

    @Given("Policy is generated for customer with status {string} within last {string} days")
    public void policy_is_generated_for_customer_with_status_days(String status, String days) throws JSchException,
            SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-" + days, "GBL", "Y", "N", "Rohit", "Delivered",
                status, "raghugoud@futureproofai.com", "1", env, Boolean.toString(append)});
        populateVars(params);
    }

    @Given("Policy is generated for customer for policy {string} with status {string} within last {string} days")
    public void policy_is_generated_for_customer_for_policy_with_status_days(String policy, String status, String days) throws JSchException,
            SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-" + days, policy, "Y", "N", "Rohit", "Delivered",
                status, "raghugoud@futureproofai.com", "1", env, Boolean.toString(append)});
        populateVars(params);
    }

    @Given("Policy is generated for customer for {string} policy")
    public void policy_is_generated_for_customer_for_policy(String policy) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", policy, "Y", "N", "Rohit", "Delivered", "ACTIVE",
                "raghugoud@futureproofai.com", "1" ,env, Boolean.toString(append)});
        populateVars(params);
    }

    @Given("Policy is generated for customer with email {string}")
    public void policy_is_generated_for_customer_with_email(String email) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", "GBL", "Y", "N", "Rohit",
                "Delivered", "ACTIVE", email, "1" ,env, Boolean.toString(append)});
        populateVars(params);
    }

    @Given("Bulk Policy is generated for {int} customers with email {string}")
    public void bulk_policy_is_generated_for_customer_with_email(int num, String email) throws JSchException, SftpException, IOException, CsvException {
        String[] params = BulkFileGenerator.generate(new String[] {"-2", "GBL", "Y", "N", "Rohit",
                "Delivered", "ACTIVE", email, Integer.toString(num), env, Boolean.toString(append)});
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


    @Then("Email status is delivered in CDH for {string}")
    public void email_status_should_be_delivered_from_cdh(String label) throws MalformedURLException {
        Awaitility.await().atMost(600, TimeUnit.SECONDS).pollInterval(60, TimeUnit.SECONDS).until(() ->
        {
            List list = when()
                    .get(new URL(ih_url.get(env) + customerId))
                    .then()
                    .extract().response().body().path("OfferHistory.findAll {" +
                            "it -> (it.Outcome == 'Delivered' && it.Label == '" + label +"')" +
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

        List list = when()
                .get(new URL(ih_url.get(env) + customerId))
                .then()
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

    @Then("Email status is {string} in CDH")
    public void email_status_is_in_cdh(String status) throws MalformedURLException, InterruptedException, JSchException {

        LOGGER.info("Customer id: " + customerId);
        Awaitility.await().atMost(600, TimeUnit.SECONDS).pollInterval(10, TimeUnit.SECONDS).until(() ->
        {
            List list = when()
                    .get(new URL(ih_url.get(env) + customerId))
                    .then()
                    .extract().response().body().path("OfferHistory.findAll {" +
                            "it -> (it.Outcome == '" + status  +"')" +
                            "}.InteractionID");

            System.out.println("list " + list);
            return list.size() > 0;
        });
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
}
