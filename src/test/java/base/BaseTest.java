package base;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pages.LoginPage;
import steps.MainSteps;
import utils.PropertyUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;

    @BeforeTest
    public void setUp(ITestContext context) {
        String driverPath = PropertyUtils.getEnv("driver_path");
        System.setProperty("webdriver.chrome.driver", driverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); //Bypass OS security model
        options.addArguments("--start-maximized");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1280, 720));
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        loginPage = new LoginPage(driver);
        setDriverAttribute(context);
        MainSteps.step("Open driver instance");
    }


    private void setDriverAttribute(ITestContext context) {
        String variable = "driver";
        System.out.println("Setting driver into context with variable name " + variable);
        context.setAttribute(variable, driver);
    }

    @BeforeMethod
    public void openWindow() {

    }

    private void openNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
    }

    private void switchToNewTab() {
        String subWindowHandler = null;
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            subWindowHandler = handle;
        }
        driver.switchTo().window(subWindowHandler);
    }

    @AfterClass
    public void closeWindow(ITestContext context) {
        MainSteps.step("Close previous tab");
        openNewTab();
        driver.manage().deleteAllCookies();
        driver.close();
        MainSteps.step("Open new tab");
        switchToNewTab();
    }

    @AfterTest
    public void closeBrowser(ITestContext context) {
        driver.quit();
    }

}
