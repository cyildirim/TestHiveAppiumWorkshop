import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by can on 02/04/17.
 */
public class AndroidLoginTest
{
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


        appiumDriver = new AndroidDriver(url,capabilities);
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

    }
}
