package page;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by user on 7/21/18.
 */
public class TaskPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "menu_item_complete")
    private WebElement completeButton;

    public TaskPage(AndroidDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void clickCompleteButton(){
        completeButton.click();
    }
}
