package pageOblects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CustomersPage extends BasePage {

    public CustomersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//a[contains(text(), 'First Name')]")
    private WebElement sortByFirstNameBtn;
    @FindBy(xpath = "//input[@ng-model='searchCustomer']")
    private WebElement searchCustomerBtn;
    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> tableRows;
    @FindBy(xpath = "//tbody/tr/td[1]")
    private List<WebElement> tableFirstNames;
    @FindBy(xpath = "//div[contains(@class, 'ng-scope')]//table")
    private WebElement table;

    @Step("Отсортировать таблицу по имени")
    public CustomersPage sortByFirstName() {
        sortByFirstNameBtn.click();
        return this;
    }

    @Step("Получить имена пользователей")
    public List<String> getCustomerNames() {

        List<String> names = tableFirstNames.stream()
                .map(WebElement::getText).toList();
        return names;
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

    @Step("Проверить, что пользователь есть в таблице")
    public WebElement checkCustomerListedInTable(String firstName, String lastName, String code) {
        String s = String.format(
                "//tr[contains(td[1], '%s') and contains(td[2], '%s') and contains(td[3], '%s')]",
                firstName, lastName, code);
        return table.findElement(By.xpath(s));
    }
}
