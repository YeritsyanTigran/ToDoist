package page;

import com.gargoylesoftware.htmlunit.javascript.host.Touch;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.touch.TouchActions;

import java.util.List;

/**
 * Created by user on 7/21/18.
 */
public class ProjectPage {
    private AndroidDriver driver;
    private WebDriverWait wait;
    private TouchAction touchAction;

    @FindBy(id = "fab")
    private WebElement fabButton;

    @FindBy(id = "message")
    private WebElement taskField;

    @FindBy(id = "button1")
    private WebElement sendButton;

    @FindBy(id="empty_content")
    private WebElement emptyContent;

    @FindBy(id = "action_mode_close_button")
    private WebElement backButton;

    @FindBy(id = "text")
    private List<WebElement> tasksTitles;

    @FindBy(id = "snackbar_action")
    private WebElement undoButton;

    @FindBy(id = "content_toolbar_container")
    private WebElement mainContainer;


    public ProjectPage(AndroidDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver,10);
        touchAction = new TouchAction(driver);
    }

    public void clickFabButton(){
        fabButton.click();
    }

    public void setTaskName(String taskName){
        taskField.sendKeys(taskName);
    }

    public void clickSendButton(){
        sendButton.click();
    }

    public void clickBackButton(){
        backButton.click();
    }


    public void refresh(){
        touchAction.press(driver.findElement(By.id("empty_content"))).waitAction(1000).moveTo(0,1000).release().perform();
    }

    public void clickTask(String taskName){
        for(WebElement i : tasksTitles){
            if(i.getText().equals(taskName)){
                i.click();
                break;
            }
        }
    }
    public boolean checkTask(String taskName){
        for(WebElement i:tasksTitles){
            if(i.getText().equals(taskName)){
                return true;
            }
        }
        return false;
    }
    public void clickUndoButton(){
        undoButton.click();
    }

}
