package pageOblects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCustomerPage extends BasePage {
    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//input[@ng-model='fName']")
    private WebElement firstName;
    @FindBy(xpath = "//input[@ng-model='lName']")
    private WebElement lastName;
    @FindBy(xpath = "//input[@ng-model='postCd']")
    private WebElement postCode;
    @FindBy(xpath = "//button[text()='Add Customer']")
    private WebElement addCustomerBtn;

    @Step("Ввести имя пользователя: {name}")
    public AddCustomerPage setFirstName(String name) {
        firstName.sendKeys(name);
        return this;
    }

    @Step("Ввести фамилию пользователя: {lastName}")
    public AddCustomerPage setLastName(String lastName) {
        this.lastName.sendKeys(lastName);
        return this;
    }

    @Step("Ввести код: {code}")
    public AddCustomerPage setPostCode(String code) {
        postCode.sendKeys(code);
        return this;
    }

    @Step("Нажать Add Customer")
    public AddCustomerPage clickAddCustomer() {
        addCustomerBtn.click();
        return this;
    }
}
