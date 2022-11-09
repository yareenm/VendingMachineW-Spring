
package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.AuditDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendingMachineServiceImpl implements VendingMachineService{

    private VendingMachineDao dao;
    private AuditDao adao;

    public VendingMachineServiceImpl() throws VendingMachineException {

    }
    @Autowired
    public VendingMachineServiceImpl(VendingMachineDao dao, AuditDao adao) {
        this.dao = dao;
        this.adao = adao;
    }

    // Get the item by its name (key)
    @Override
    public Item getItem(String name) throws VendingMachineException, VendingMachineItemInventoryException{
        return dao.getItem(name);
    }

    // Displays all items count more than 0 and uses lambda expression for it.
    // First it converts the list to the stream.
    // Then with the filter function it filters the related stream under a specific condition.
    // Lastly, it converts stream to a Collection (List)
    @Override
    public List<Item> listAllItems() throws VendingMachineException{
        adao.writeAuditEntry("ALL ITEMS LISTED.");
        return dao.listAllItems()
                .stream()
                .filter(item->item.getNumInventoryItems()>0)
                .collect(Collectors.toList());
    }

    // Adds item to the file
    // Checks if the item was already in the vending machine
    @Override
    public Item addItem(Item item) throws VendingMachineException{

        if(dao.getItem(item.getName()) != null){
            throw new VendingMachineException("This item already exists in Vending Machine");
        }
        dao.addItem(item);
        adao.writeAuditEntry("ITEM " + item.getName() + " ADDED.");
        return item;
    }

    // Removes item from a file
    @Override
    public Item removeItem(Item item) throws VendingMachineException{
        adao.writeAuditEntry("ITEM " + item.getName() + " REMOVED." );
        return dao.removeItem(item);
    }

    // Changes inventory count of an item
    @Override
    public Item changeInventoryCount(Item item, int newCount) throws VendingMachineException{
        adao.writeAuditEntry("ITEM " + item.getName() + " INVENTORY COUNT CHANGED." );
        return dao.changeInventoryCount(item,newCount);
    }

    // Sells an item
    // Checks for insufficient fund of the user, and remaining item number on the inventory.
    @Override
    public BigDecimal sellItem(BigDecimal totalFunds, Item item) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException{

        if(item.getNumInventoryItems() <= 0) {
            throw new VendingMachineItemInventoryException("ERROR : Item out of stock.");
        }

        if((totalFunds.subtract(item.getCost())).compareTo(BigDecimal.ZERO) < 0 ){
            throw new VendingMachineInsufficientFundsException("ERROR: Insufficent fund.");
        }

        // The user can buy item one at a time
        // So, the inventory count is gonna decreased by 1
        dao.changeInventoryCount(item,item.getNumInventoryItems()-1);
        adao.writeAuditEntry("ITEM "+ item.getName()+ " SOLD.");

        return totalFunds.subtract(item.getCost());
    }
    
}
