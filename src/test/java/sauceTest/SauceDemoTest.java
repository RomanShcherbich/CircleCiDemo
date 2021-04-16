package sauceTest;

import base.BaseTest;
import constants.Environment;
import io.qameta.allure.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ProductsPage;
import steps.MainSteps;
import utils.AllureUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SauceDemoTest extends BaseTest {

    private String username = Environment.USERNAME;
    private String locked_user = Environment.USERNAME_LOCKED;
    private String password = Environment.PASSWORD;

    @Flaky
    @Description("Тест с использование гибкого ожидания")
    @Issue("4")
    @TmsLink("4")
    @Test(description = "Test to demonstrate fluent waiter", groups = {"flacky"})
    @Parameters("keyword")
    public void testJsWaiter(String keyword) {
        MainSteps.step("open page 1");
        loginPage.openPage();
        MainSteps.stepKeyword(keyword);
        ProductsPage productsPage = loginPage.validLogin(username, password);
        FluentWait<ProductsPage> fluent = new FluentWait<>(productsPage)
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        fluent.until(ProductsPage::isProductPageLoaded);

        try {
            productsPage.waitWithJsWaiter();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        MainSteps.step("validate page opened");
        AllureUtils.addText(String.format("{\"%s\":\"%s\"}", "keyword3", keyword));
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
    }

    @Test(groups = {"init-browser"})
    public void validLogin(){
        loginPage.openPage();
        loginPage.waitLoginPageLoaded();
        ProductsPage productsPage = loginPage.validLogin(username, password);
        Map<String, String> map = new HashMap<>();
        map.put("username", "password");
        map.put("key2", "value3");
        map.put("key3", "value4");
        MainSteps.stepWithAttachment(map);
        productsPage.isProductPageLoaded();
    }

    @Test(groups = {"flacky"})
    public void addProductToCart(){
        loginPage.openPage();
        ProductsPage productsPage = loginPage.validLogin(username, password);
        productsPage.isProductPageLoaded();
        Assert.assertEquals(productsPage.addProductToCart("Sauce Labs Backpack").getButtonTitle(),
                "REMOVE");

    }

    @Test(groups = {"flacky"})
    public void addAllProductsToCart(){
        loginPage.openPage();
        ProductsPage productsPage = loginPage.validLogin(username, password);
        productsPage.isProductPageLoaded();
        productsPage.addAllProducts();
        Assert.assertEquals(productsPage.addProductToCart("Sauce Labs Backpack").getButtonTitle(),
                "ADD TO CART");
//                "REMOVE");
    }


    @Test(groups = {"init-browser"})
    public void logout(){
        loginPage.openPage();
        ProductsPage productsPage = loginPage.validLogin(username, password);
        productsPage.isProductPageLoaded();
        productsPage.logout();
    }


    @Test(description = "Environment variable test")
    @Link("https://app.circleci.com/settings/project/github/RomanShcherbich/SeleniumAdvancedDemoLesson/environment-variables?return-to=https%3A%2F%2Fapp.circleci.com%2Fpipelines%2Fgithub%2FRomanShcherbich%2FSeleniumAdvancedDemoLesson")
    @Description("Circleci config test to provide environment variables")
    public void externalEnvironmentsTest(){
        Assert.assertEquals(locked_user , "locked_out_user", "External variable [locked_user] is " + locked_user);
    }


}
