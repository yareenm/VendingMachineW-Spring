
package com.mattu.vendingmachine.service;

import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.AuditDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineServiceImplTest {
    
    public static VendingMachineService service;
    
    public VendingMachineServiceImplTest() throws VendingMachineException {
     //VendingMachineDao dao = new VendingMachineDaoImpl();
     //AuditDao adao = new AuditDaoImpl();
     //service = new VendingMachineServiceImpl(dao,adao);

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer",VendingMachineService.class);

    }
    
    @BeforeAll
    public static void setUpClass() throws VendingMachineException{
        service = new VendingMachineServiceImpl();
    }

    @BeforeEach
    public void setUp() throws VendingMachineException {
        }

    /**
     * Test of getItem method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testGetItem() throws Exception {
        // declare test variables
        Item testItem = new Item("Cheetos",  new BigDecimal(2.99).setScale(2,RoundingMode.FLOOR), 18);

        try{
            service.addItem(testItem);
            assertNotNull(testItem,"Item should not be null");
            Item getItem = service.getItem(testItem.getName());
            assertEquals(testItem.getName(),getItem.getName(),"Checking the item name.");
            assertEquals(testItem.getCost(),getItem.getCost(),"Checking the item cost.");
            assertEquals(testItem.getNumInventoryItems(),getItem.getNumInventoryItems(),"Checking the item inventory count.");

        }catch(VendingMachineException e){
            fail("Get an item failed.");
        }
    }

    /**
     * Test of listAllItems method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testListAllItems() throws Exception {
        assertEquals(11, service.listAllItems().size(), "11 items");
    }

    /**
     * Test of changeInventoryCount method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testChangeInventoryCount(){
        // declare test variables
        Item testItem = new Item("Cheetos",  new BigDecimal(2.99).setScale(2,RoundingMode.FLOOR), 18);
        try{
            service.changeInventoryCount(testItem, 100);
            assertNotNull(testItem, "Item should not be null");
            assertEquals(100, testItem.getNumInventoryItems(), "Inventory item should be 100");
        }catch(VendingMachineException e){
            fail("No way it will go wrong");
        }

        try{
            service.changeInventoryCount(testItem, -100);
        }catch(VendingMachineException e){
            System.out.println("the value should not be negative");
        }
    }

    /**
     * Test of sellItem method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testSellItem(){
        // declare test variables
        Item testItem = new Item("Pringles",  new BigDecimal(3.99).setScale(2,RoundingMode.FLOOR), 5);
        BigDecimal funds = new BigDecimal("10").setScale(2,RoundingMode.FLOOR);
        try{
            //check selling operation for testItem
            service.sellItem(funds,testItem);
            assertNotNull(testItem, "Item should not be null");
            assertEquals(4, testItem.getNumInventoryItems(), "Inventory item should be 4");

        } catch (VendingMachineInsufficientFundsException e) {
            fail("Insufficent funds.", e);
        } catch (VendingMachineItemInventoryException e) {
            fail("Item out of stock.",e);
        } catch (VendingMachineException e) {
            fail("Something is wrong in the selling part",e);
        }
    }

}
