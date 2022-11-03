
package com.mattu.vendingmachine.service;

import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.AuditDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author salajrawi
 */
public class VendingMachineServiceImplTest {

    public static VendingMachineService service;
    private Item item;

    public VendingMachineServiceImplTest() {
        try{
            VendingMachineDao dao = new VendingMachineDaoImpl();
            AuditDao auditDao = new AuditDaoImpl();
            service = new VendingMachineServiceImpl(dao, auditDao);
        }
        catch (VendingMachineException e) {
            System.out.println("Error.");
        }
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws VendingMachineException {
        item = new Item("ItemTest", new BigDecimal(3), 1);
        service.addItem(item);
    }

    @AfterEach
    public void tearDown() throws VendingMachineException{
        service.removeItem(item);
    }

    /**
     * Test of getItem method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testGetItem() throws Exception {
        Item ItemTest = new Item("Potato Chip", new BigDecimal("3.49"), 97);
        assertNotNull(ItemTest, "Item should not be null");
        assertEquals(service.getItem("Potato Chip"), ItemTest, "Test item should equal getItem");
    }

    /**
     * Test of listAllItems method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testListAllItems() throws Exception {
        assertEquals(9, service.listAllItems().size(), "9 items");
    }

    /**
     * Test of changeInventoryCount method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testChangeInventoryCount(){
        Item ItemTest = new Item("Cookies",  new BigDecimal("5.40").setScale(2,RoundingMode.FLOOR), 13);
        try{
            service.changeInventoryCount(ItemTest, 100);
            assertNotNull(ItemTest, "Item should not be null");
            assertEquals(100, ItemTest.getNumInventoryItems(), "Inventory item should be 100");
        }catch(VendingMachineException e){
            System.out.println("Error.");
        }
        try{
            service.changeInventoryCount(ItemTest, -100);
        }catch(VendingMachineException e){
            System.out.println("Error.");
        }
    }

    /**
     * Test of sellItem method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testSellItem() throws Exception {
        Item ItemTest = new Item("Pepsi",  new BigDecimal("1.99"), 6);
        BigDecimal change = service.sellItem(new BigDecimal("3.0"), ItemTest);
        assertNotNull(ItemTest, "Item should not be null");
        assertEquals(1.01, change.doubleValue(), "Change should be 1.01");

    }
}
