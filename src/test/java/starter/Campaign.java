package starter;

import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Campaign {

    @When("Campaign is run")
    public void campaign_is_run() {
        WebDriver driver = WebDriverManager.chromedriver().create();
        driver.get("https://cnollc-cnocdh-stg1.pegacloud.net/prweb?pyActivity=PegaMKT-Work-Program.StartOutboundScheduleNow");
        WebElement userIDBox = driver.findElement(By.id("txtUserID"));
        WebElement txtPassword = driver.findElement(By.id("txtPassword"));
        WebElement submitBtn = driver.findElement(By.id("sub"));

        userIDBox.sendKeys("rohitsaxena@futureproofai.com");
        txtPassword.sendKeys("London@1234");
        submitBtn.click();
    }
}
