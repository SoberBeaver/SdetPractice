package pageOblects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class BasePage {

    WebDriver driver;
    @Step("Закрыть алерт")
    public void closeAlert() {
        driver.switchTo().alert().accept();
    }

    @Step("Получить текст алерта")
    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }
}
