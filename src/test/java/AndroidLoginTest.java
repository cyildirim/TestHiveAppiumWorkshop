import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by can on 02/04/17.
 */
public class AndroidLoginTest
{
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private AppiumDriver appiumDriver;

    @Before
    public void setUp() throws MalformedURLException
    {

        URL url = new URL("http://0.0.0.0:4724/wd/hub");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        //device configurations
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus One");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");


        //app configurations
        capabilities.setCapability(MobileCapabilityType.APP, "/opt/appium-workshop/app-debug.apk");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);


        appiumDriver = new AndroidDriver(url, capabilities);
        appiumDriver.resetApp();
    }

    @After
    public void tearDown()
    {
        appiumDriver.closeApp();
    }

    @Test
    public void testAndroidLogin()
    {
        login();

        Assert.assertTrue(appiumDriver.findElement(By.id("com.example.can.logintesthive:id/elem1")).isDisplayed());
    }

    @Test
    public void testScrollWelcomePage()
    {
        login();

        TouchAction touchAction = new TouchAction(appiumDriver);
        touchAction.press(200,700).waitAction(400).moveTo(200,500).release();
        touchAction.perform();

    }


    private void login()
    {
        appiumDriver.findElement(By.id("com.example.can.logintesthive:id/editText_username")).sendKeys("admin");
        appiumDriver.findElement(By.id("com.example.can.logintesthive:id/editText_password")).sendKeys("password");

        appiumDriver.findElement(By.id("com.example.can.logintesthive:id/button")).click();

        WebDriverWait webDriverWait = new WebDriverWait(appiumDriver, 30);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.example.can.logintesthive:id/elem1")));
    }


    public void swipeScreen(SwipeDirection swipeDirection, int repeat)
    {
        int screenHeight = appiumDriver.manage().window().getSize().getHeight();
        int screenWidth = appiumDriver.manage().window().getSize().getWidth();
        int startX;
        int endX;

        for (int i = 0; i < repeat; i++)
        {
            int startY;
            TouchAction touchAction = new TouchAction(appiumDriver);
            // appium converts press-wait-moveto-release to a swipe action
            switch (swipeDirection)
            {
                case UP:
                    startX = screenWidth / 2;
                    endX = startX;
                    startY = (int) (0.2 * screenHeight);

                    touchAction.press(startX, startY).waitAction(400).moveTo(endX, screenHeight).release();
                    break;
                case DOWN:
                    startX = screenWidth / 2;
                    endX = startX;
                    startY = (int) (0.65 * screenHeight);
                    touchAction.press(startX, startY).waitAction(400).moveTo(endX, -screenHeight).release();
                    break;
                case LEFT:
                    startX = (int) (0.2 * screenWidth);
                    startY = screenHeight / 2;
                    touchAction.press(startX, startY).waitAction(400).moveTo(screenWidth, startY).release();
                    break;
                case RIGHT:
                    startX = (int) (0.65 * screenWidth);
                    startY = screenHeight / 2;
                    touchAction.press(startX, startY).waitAction(400).moveTo(-screenWidth, startY).release();
                    break;
            }
            touchAction.perform();
        }

    }

    public enum SwipeDirection
    {
        UP, DOWN, LEFT, RIGHT
    }
}
