import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by can on 01/04/17.
 * <p>
 * {
 * "automationName": "XCUITest",
 * "newCommandTimeout": "600",
 * "app": "/opt/appium-workshop/LoginTest.app",
 * "nativeInstrumentsLib": true,
 * "platformVersion": "10.2",
 * "platformName": "iOS",
 * "deviceName": "iPhone 6"
 * }
 */

public class IOSAppiumTest
{
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123";
    public AppiumDriver appiumDriver;

    @Before
    public void setUp() throws MalformedURLException
    {
        URL url = new URL("http://0.0.0.0:4723/wd/hub");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        //device configurations
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");


        //app configurations
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability(MobileCapabilityType.APP, "/opt/appium-workshop/LoginTest.app");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);
        capabilities.setCapability("nativeInstrumentsLib", true);

        appiumDriver = new IOSDriver(url, capabilities);

        appiumDriver.resetApp();
    }

    @After
    public void tearDown()
    {
        appiumDriver.closeApp();
    }

    @Test
    public void testLogin() throws InterruptedException, IOException
    {
        appiumDriver.findElement(By.xpath("//XCUIElementTypeTextField[@value='Username']")).sendKeys(USERNAME);
        appiumDriver.findElement(By.xpath("//XCUIElementTypeSecureTextField")).sendKeys(PASSWORD);
        appiumDriver.findElement(By.xpath("//XCUIElementTypeButton")).click();


        MobileElement welcome = (MobileElement) appiumDriver.findElement(By.xpath("//XCUIElementTypeButton[@name='Back']"));

        WebDriverWait webDriverWait = new WebDriverWait(appiumDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(welcome));


        File screenshotFile = appiumDriver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(System.getProperty("user.home") + "/Screenshots/" + RandomStringUtils.randomAlphanumeric(10) + ".png"));

        Assert.assertTrue(appiumDriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='Welcome']")).isDisplayed());
    }
}
