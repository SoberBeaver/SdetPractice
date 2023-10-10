package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pageOblects.AddCustomerPage;
import pageOblects.CustomersPage;
import pageOblects.pageElements.ManagerMenuElements;

@Epic("Управление клинтами XYZ Bank")
public class AddCustomerTest extends BaseTest {
    ManagerMenuElements managerMenuElements = new ManagerMenuElements(driver);
    AddCustomerPage addCustomerPage = new AddCustomerPage(driver);
    CustomersPage customersPage = new CustomersPage(driver);

    @Test
    @Feature("Создание клиента (Customer)")
    @Description("Создание клиента (Customer)")
    void createCustomer() {
        managerMenuElements.openAddCustomerPage();

        String firstName = "Tom";
        String lastName = "Riddle";
        String code = "E66666";
        addCustomerPage.setFirstName(firstName).setLastName(lastName).setPostCode(code).clickAddCustomer();
        String alertText = addCustomerPage.getAlertText();
        addCustomerPage.closeAlert();
        Assertions.assertEquals("Customer added successfully with customer id :6", alertText);

        managerMenuElements.openCustomersPage();
        customersPage.checkCustomerListedInTable(firstName, lastName, code);
    }
}
