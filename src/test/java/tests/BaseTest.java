package tests;

import helpers.PropertyProvider;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import pageOblects.AddCustomerPage;
import pageOblects.CustomersPage;
import pageOblects.pageElements.ManagerMenuElements;

import java.time.Duration;

public class BaseTest {
    static ChromeDriver driver;
    static AddCustomerPage addCustomerPage;
    static ManagerMenuElements managerMenuElements;
    static CustomersPage customersPage;

    @BeforeAll
    @Step("Открыть браузер")
    static void setUp() {
        driver = new ChromeDriver();
        Dimension dimension = new Dimension(1920 *2, 1080*2);
        driver.manage().window().setSize(dimension);

        int timeout = Integer.parseInt(PropertyProvider.getInstance().getProperties("timeout"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));

        addCustomerPage = new AddCustomerPage(driver);
        managerMenuElements = new ManagerMenuElements(driver);
        customersPage = new CustomersPage(driver);
    }

    @AfterAll
    @Step("Закрыть браузер")
    static void tearDown() {
        driver.quit();
    }

    @BeforeEach
    @Step("Перейти на сайт")
    void openSite() {
        String url = PropertyProvider.getInstance().getProperties("url");
        driver.get(url);
    }

    @AfterEach
    @Step("Очистить кукис и хранилище")
    void clearCache() {
        driver.manage().deleteAllCookies();
        driver.getSessionStorage().clear();
        driver.getLocalStorage().clear();
    }

}
