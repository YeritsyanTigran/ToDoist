import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BeforeTest {
    private String apkDir = System.getProperty("apkDir");
    private String deviceName = System.getProperty("deviceName");
    private String version = System.getProperty("version");
    private String automatioName = System.getProperty("automationName");
    private boolean fullResrt = Boolean.getBoolean(System.getProperty("fullReset"));


    private static AndroidDriver driver;

    @BeforeClass
    public void initiateDriver(){

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),setupDesiredCapabilities());
        }catch (MalformedURLException e){
            e.fillInStackTrace();
        }
    }

    @Step
    protected static AndroidDriver getDriver() {
        return driver;
    }

    @Step
    private DesiredCapabilities setupDesiredCapabilities(){

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME,"");
        desiredCapabilities.setCapability("platformName","Android");
        desiredCapabilities.setCapability("deviceName",deviceName);
        desiredCapabilities.setCapability(CapabilityType.VERSION,version);
        desiredCapabilities.setCapability("app",new File(apkDir).getAbsolutePath());
        desiredCapabilities.setCapability("automationName",automatioName);
        desiredCapabilities.setCapability("fullReset",fullResrt);

        return desiredCapabilities;
    }

    @AfterClass
    public void closeAppium(){
        driver.closeApp();
        driver.close();
    }
}
