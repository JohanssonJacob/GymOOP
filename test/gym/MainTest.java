package gym;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    Customer testCustomer = new Customer("5711121234", "Hilmer Heur", LocalDate.parse("2019-08-18"));

    @Test
    void getListWithCustomersTest() {
        List<Customer> customerListTest = Main.getListWithCustomers(Main.customerFile);
        assertEquals(14, customerListTest.size());
        assertEquals("Hilmer Heur" ,customerListTest.get(7).getName());
    }

    @Test
    void hasMembershipTest() {
        assertEquals("This customers membership has ran out.", Main.hasMembership("5711121234", testCustomer));
        assertNotEquals("This customer has an active membership.", Main.hasMembership("5711121234", testCustomer));
    }

    @Test
    void isPreviousCustomerTest() {
        assertFalse(Main.isPreviousCustomer("123456", testCustomer));
        assertTrue(Main.isPreviousCustomer("5711121234", testCustomer));
    }
}