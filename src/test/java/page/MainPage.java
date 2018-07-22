package page;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by user on 7/20/18.
 */
public class MainPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "btn_welcome_continue_with_email")
    private WebElement withEmailButton;

    public MainPage(AndroidDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void clickWithEmailButton(){
        int i=0;
        try {
            while (withEmailButton.isDisplayed()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                withEmailButton.click();
                if (i == 5) {
                    break;
                }
            }
        }catch (Exception e){

        }
    }
}
