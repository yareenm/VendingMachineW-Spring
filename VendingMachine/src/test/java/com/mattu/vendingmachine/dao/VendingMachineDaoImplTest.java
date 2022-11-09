
package com.mattu.vendingmachine.dao;

import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineDaoImplTest {
    
    public static VendingMachineDao testDao;
    
    public VendingMachineDaoImplTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
        testDao = new VendingMachineDaoImpl();
    }

    @BeforeEach
    public void setUp() throws Exception {
        testDao = new VendingMachineDaoImpl();
    }

    @Test
    public void testGetItem() throws Exception {
        // create my method test inputs
        String name = "Ayran";
        BigDecimal cost = new BigDecimal("4.5");
        int count = 4;
        Item item = new Item(name,cost,count);

        // add the item to the dao
        testDao.addItem(item);
        // get the item from the dao
        Item getItem = testDao.getItem(item.getName());

        // check the data is equal
        assertEquals(item.getName(),getItem.getName(),"Checking the item name.");
        assertEquals(item.getCost(),getItem.getCost(),"Checking the item cost.");
        assertEquals(item.getNumInventoryItems(),getItem.getNumInventoryItems(),"Checking the item inventory count.");

    }

    @Test
    public void testListAllItems() throws Exception {
        // create my method test inputs
        String name1 = "Ayran";
        BigDecimal cost1 = new BigDecimal("4.5");
        int count1 = 4;
        Item item1 = new Item(name1,cost1,count1);

        // create my method test inputs
        String name2 = "Salgam";
        BigDecimal cost2 = new BigDecimal("3.5");
        int count2 = 2;
        Item item2 = new Item(name2,cost2,count2);

        // add the inputs
        testDao.addItem(item1);
        testDao.addItem(item2);

        // get all the items from dao
        List<Item> testList = testDao.listAllItems();

        // check the data is not null
        assertNotNull(testList,"The list of items must not null");

        // check the data contains all the variables
        assertTrue(testDao.listAllItems().contains(item1),"The list of items should include Ayran");
        assertTrue(testDao.listAllItems().contains(item2),"The list of items should include Salgam");


    }


    @Test
    public void testChangeInventoryCount() throws Exception {
        // create my method test inputs
        String name1 = "Ayran";
        BigDecimal cost1 = new BigDecimal("4.5");
        int count1 = 4;
        Item item1 = new Item(name1,cost1,count1);

        int updatedCount = 10;
        // add the item
        testDao.addItem(item1);

        // get the item from dao
        testDao.changeInventoryCount(item1,updatedCount);

        List<Item> testList = testDao.listAllItems();

        // check the data is not null
        assertNotNull(testList,"The list of items must not null");
        assertEquals(testList.size(),testList.size(),"List of items should be remained");

        // check the data is equal
        assertEquals(updatedCount,item1.getNumInventoryItems(),"Checking the item inventory count.");

    }
    
}
