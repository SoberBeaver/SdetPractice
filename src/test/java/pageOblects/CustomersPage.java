package pageOblects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CustomersPage {
    WebDriver driver;

    public CustomersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//a[contains(text(), 'First Name')]")
    private WebElement sortByFirstNameBtn;
    @FindBy(xpath = "//tbody/tr[1]")
    private WebElement firstCustomerTr;
    @FindBy(xpath = "//tbody/tr[last()]")
    private WebElement lastCustomerTr;
    @FindBy(xpath = "//input[@ng-model='searchCustomer']")
    private WebElement searchCustomerBtn;
    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> tableRows;

    @Step("Отсортировать таблицу по имени")
    public CustomersPage sortByFirstName() {
        sortByFirstNameBtn.click();
        return this;
    }

    @Step("Получить имя первого пользователя")
    public String getFirstCustomerName() {
        return firstCustomerTr.findElement(By.xpath("td[1]")).getText();
    }

    @Step("Получить имя последнего пользователя")
    public String getLastCustomerName() {
        return lastCustomerTr.findElement(By.xpath("td[1]")).getText();
    }

    @Step("Ввести в поиск строку: {searchString}")
    public CustomersPage seachByString(String searchString) {
        searchCustomerBtn.sendKeys(searchString);
        return this;
    }

    @Step("Получить количество пользователей в таблице")
    public int getCustomersCount() {
        return tableRows.size();
    }
}
