package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Epic("UI тесты XYZ Bank")
public class CustomersTest extends BaseTest {

    @Test
    @Feature("Сортировка клиентов по имени (First Name)")
    @Description("Сортировка клиентов по имени (First Name)")
    void sortCustomersByFirstName() {
        managerMenuElements.openCustomersPage();

        customersPage.sortByFirstName();
        Assertions.assertEquals("Ron", customersPage.getFirstCustomerName());
        Assertions.assertEquals("Albus", customersPage.getLastCustomerName());

        customersPage.sortByFirstName();
        Assertions.assertEquals("Albus", customersPage.getFirstCustomerName());
        Assertions.assertEquals("Ron", customersPage.getLastCustomerName());
    }

    @Test
    @Feature("Поиск клиента")
    @Description("Поиск клиента")
    void searchCustomer() {
        managerMenuElements.openCustomersPage();
        customersPage.seachByString("Harry");

        Assertions.assertEquals(1, customersPage.getCustomersCount());
        Assertions.assertEquals("Harry", customersPage.getFirstCustomerName());
    }
}
