package page;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by user on 7/20/18.
 */
public class LoginPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "email_exists_input")
    private WebElement emailField;

    @FindBy(id = "btn_continue_with_email")
    private WebElement continueWithEmailButton;

    @FindBy(id = "log_in_password")
    private WebElement passwordField;

    @FindBy(id = "btn_log_in")
    private WebElement loginButton;

    public LoginPage(AndroidDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
        PageFactory.initElements(driver,this);
    }

    public void setEmail(String email){
        wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailField.sendKeys(email);
    }

    public void clickContinueWithEmailButton(){
//        wait.until(ExpectedConditions.elementToBeClickable(continueWithEmailButton)).click();\
        continueWithEmailButton.click();
    }

    public void setPassword(String password){
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.sendKeys(password);
    }

    public void clickLoginButton(){
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

    }

}
