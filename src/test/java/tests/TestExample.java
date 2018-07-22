package tests;

import api.HttpClient;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.*;

public class TestExample extends BaseTest {
    MainPage mainPage;
    LoginPage loginPage;
    HomePage homePage;
    ProjectPage projectPage;
    TaskPage taskPage;

    @BeforeMethod
    public void setUp(){
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        projectPage = new ProjectPage(driver);
        taskPage = new TaskPage(driver);
    }

    @Test
    public void test1(){
        String projectName = "TestProject";
        String projectID;

        try {
           projectID = HttpClient.createProjct(projectName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPage.clickWithEmailButton();
        loginPage.setEmail(BaseTest.EMAIL);
        loginPage.clickContinueWithEmailButton();
        loginPage.setPassword(BaseTest.PASSWORD);
        loginPage.clickLoginButton();
//        try {
//            if(homePage.checkTimeZonePopup()){
//                homePage.clickTimeZoneYesButton();
//            }
//        }catch (Exception e){
//        }
        homePage.clickBurgerMenu();
        homePage.clickProjectsDownButton();
        Assert.assertTrue(homePage.checkProject(projectName),"Project isn't present");

    }

    @Test
    public void test2(){
        String projectName = "TestProject";
        String projectID = "";
        String taskName = "TestTask";
        try {
           projectID = HttpClient.createProjct(projectName);
        }catch (Exception e){
            e.printStackTrace();
        }
        mainPage.clickWithEmailButton();

        loginPage.setEmail(BaseTest.EMAIL);
        loginPage.clickContinueWithEmailButton();
        loginPage.setPassword(BaseTest.PASSWORD);
        loginPage.clickLoginButton();

//        if(homePage.checkTimeZonePopup()){
//            homePage.clickTimeZoneYesButton();
//        }

        homePage.clickBurgerMenu();
        homePage.clickProjectsDownButton();
        homePage.clickProject(projectName);

        projectPage.clickFabButton();
        projectPage.setTaskName(taskName);
        projectPage.clickSendButton();
        projectPage.clickBackButton();

        try {
            Assert.assertTrue(HttpClient.checkTask(taskName),String.format("No %s found in %s",taskName,projectName));
            HttpClient.deleteProject(projectID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        String projectName = "TestProject";
        String projectID = "";
        String taskName = "TestTask";
        String taskID = "";

        try{
           projectID =  HttpClient.createProjct(projectName);
           taskID = HttpClient.addTask(taskName,projectID);
        }catch (Exception e){
            e.printStackTrace();
        }

        mainPage.clickWithEmailButton();
        loginPage.setEmail(BaseTest.EMAIL);
        loginPage.clickContinueWithEmailButton();
        loginPage.setPassword(BaseTest.PASSWORD);
        loginPage.clickLoginButton();
//        try {
//            if(homePage.checkTimeZonePopup()){
//                homePage.clickTimeZoneYesButton();
//            }
//        }catch (Exception e){
//        }
        homePage.clickBurgerMenu();
        homePage.clickProjectsDownButton();
        homePage.clickProject(projectName);
        projectPage.clickTask(taskName);
        taskPage.clickCompleteButton();

        try {
            Thread.sleep(10000);
            HttpClient.uncompleteTask(taskID);
            projectPage.refresh();
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(projectPage.checkTask(taskName),"Task isn't present");

        try{
            HttpClient.deleteProject(projectID);
        }catch (Exception e){

        }
    }

}
