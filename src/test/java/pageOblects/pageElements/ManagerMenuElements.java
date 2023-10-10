package pageOblects.pageElements;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManagerMenuElements {
    WebDriver driver;

    public ManagerMenuElements(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//button[@ng-click='addCust()']")
    private WebElement addCustomerBnt;
    @FindBy(xpath = "//button[@ng-click='openAccount()']")
    private WebElement openAccountBnt;
    @FindBy(xpath = "//button[@ng-click='showCust()']")
    private WebElement customersBnt;

    @Step("Открыть страницу добавления пользователя")
    public void openAddCustomerPage() {
        addCustomerBnt.click();
    }

    @Step("Открыть страницу добавления счёта")
    public void openAccountPage() {
        openAccountBnt.click();
    }

    @Step("Открыть просмотра пользователей")
    public void openCustomersPage() {
        customersBnt.click();
    }
}
