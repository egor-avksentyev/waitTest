package com.qa.waitTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

public class waitTest {
    // Instance of WebDriver
    public static WebDriver driver;
    public static WebDriverWait wait;
    private final String TEXT1 = "Invalid credentials";
    private final String TEXT2 = "Username cannot be empty";
    private final String TEXT3 = "Password cannot be empty";

    /**
     * Set up method
     */
    @Before
    public void setUp() {

        // If you want to disable infobars please use this code

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        // Initialize path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        // Initialize instance of ChromeDriver and add options
        driver = new ChromeDriver(options);
        // Maximize window
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        wait.ignoring(TimeoutException.class)
                .withMessage("OMG Where the element?")   // Print this message
                .withTimeout(Duration.ofSeconds(10))      // timeout
                .pollingEvery(Duration.ofSeconds(2));     // Tries each second

    }

    /**
     * Open Google page, search and quit
     */
    @Test
    public void waitTest() throws InterruptedException {
        /** Open browser*/
        driver.get("https://s1.demo.opensourcecms.com/s/44");

        /** Switch to proper frame*/
        driver.switchTo().frame("preview-frame");

        /**wait until username field loads*/
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[contains(@id,'txtUsername')]"))));

        /**enter username => "test"*/
        driver.findElement(By.xpath("//input[contains(@id,'txtUsername')]")).sendKeys("test");

        /**enter password => "test"*/
        driver.findElement(By.xpath("//input[contains(@id,'txtPassword')]")).sendKeys("test");

        /**Click login button*/
        driver.findElement(By.xpath("//input[contains(@class,'button')]")).click();

        /**Wait until error message appears*/
        wait.until(ExpectedConditions.and(ExpectedConditions.textToBe(By.xpath("//span[contains(@id,'spanMessage')]"), TEXT1)));

        /**Assert error message is correct*/
        Assert.assertEquals("Text is not as expected", TEXT1, driver.findElement(By.xpath("//span[contains(@id,'spanMessage')]")).getText());

        /**Click when username and login fields are empty*/
        driver.findElement(By.xpath("//input[contains(@class,'button')]")).click();

        /**Wait until error message appears*/
        wait.until(ExpectedConditions.and(ExpectedConditions.textToBe(By.xpath("//span[contains(@id,'spanMessage')]"), TEXT2)));

        /**Assert error message is correct*/
        Assert.assertEquals("Text is not as expected", TEXT2, driver.findElement(By.xpath("//span[contains(@id,'spanMessage')]")).getText());

        /** Enter only username*/
        driver.findElement(By.xpath("//input[contains(@id,'txtUsername')]")).sendKeys("test");

        /**Click login button*/
        driver.findElement(By.xpath("//input[contains(@class,'button')]")).click();

        /**Wait until error message appears*/
        wait.until(ExpectedConditions.and(ExpectedConditions.textToBe(By.xpath("//span[contains(@id,'spanMessage')]"), TEXT3)));

        /**Assert error message is correct*/
        Assert.assertEquals("Text is not as expected", TEXT3, driver.findElement(By.xpath("//span[contains(@id,'spanMessage')]")).getText());

        /** Switch to default frame*/
        driver.switchTo().defaultContent();

        /** close black frame*/
        driver.findElement(By.xpath("//i[contains(@class,'icon-cancel')]")).click();

        /**wait until black frame disappears*/
        wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//i[contains(@class,'icon-cancel')]"))));

    }

    /**
     * After method, quit driver
     */

    @After

    public void tearDown() {

        // Quit from Driver. close() just close window,
        // quit() - close all window an driver
        driver.quit();
    }


}
