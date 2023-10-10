package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pageOblects.AddCustomerPage;
import pageOblects.CustomersPage;
import pageOblects.pageElements.ManagerMenuElements;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Управление клинтами XYZ Bank")
public class CustomersTest extends BaseTest {
    ManagerMenuElements managerMenuElements = new ManagerMenuElements(driver);
    AddCustomerPage addCustomerPage = new AddCustomerPage(driver);
    CustomersPage customersPage = new CustomersPage(driver);
    @Test
    @Feature("Сортировка клиентов по имени (First Name)")
    @Description("Сортировка клиентов по имени (First Name)")
    void sortCustomersByFirstName() {
        managerMenuElements.openCustomersPage();

        customersPage.sortByFirstName();
        assertThat(customersPage.getCustomerNames()).isSortedAccordingTo(Comparator.reverseOrder());

        customersPage.sortByFirstName();
        assertThat(customersPage.getCustomerNames()).isSorted();
    }

    @Test
    @Feature("Поиск клиента")
    @Description("Поиск клиента")
    void searchCustomer() {
        managerMenuElements.openAddCustomerPage();

        String firstName = "Severus";
        String lastName = "Snape";
        String code = "E111111";
        addCustomerPage.setFirstName(firstName).setLastName(lastName).setPostCode(code).clickAddCustomer();
        addCustomerPage.closeAlert();
        managerMenuElements.openCustomersPage();
        customersPage.seachByString(firstName);

        Assertions.assertEquals(1, customersPage.getCustomersCount());
        customersPage.checkCustomerListedInTable(firstName, lastName, code);
    }
}
