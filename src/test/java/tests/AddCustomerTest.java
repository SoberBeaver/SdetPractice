package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Epic("UI тесты XYZ Bank")
public class AddCustomerTest extends BaseTest {

    @Test
    @Feature("Создание клиента (Customer)")
    @Description("Создание клиента (Customer)")
    void createCustomer() {
        managerMenuElements.openAddCustomerPage();

        addCustomerPage.setFirstName("Tom").setLastName("Riddle").setPostCode("E66666").clickAddCustomer();
        String alertText = addCustomerPage.getAlertText();
        addCustomerPage.closeAlert();
        Assertions.assertEquals("Customer added successfully with customer id :6", alertText);
    }
}
