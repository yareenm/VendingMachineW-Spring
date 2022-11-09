
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class VendingMachineDaoImpl implements VendingMachineDao{

    // declare variables
    Map<String,Item> itemMap;
    FileDao fio;
    private static final String ITEM_FILE = "items.txt";
    @Autowired
    public VendingMachineDaoImpl() throws VendingMachineException {
        fio=new FileDaoImpl();
        itemMap=fio.readFile(ITEM_FILE);
    }
    
    // get an item from the file
    @Override
    public Item getItem(String name) throws VendingMachineException{
        itemMap=fio.readFile(ITEM_FILE);
        return itemMap.get(name);
    }

    // get all items from the file
    @Override
    public List<Item> listAllItems() throws VendingMachineException{
        itemMap=fio.readFile(ITEM_FILE);
        return new ArrayList<>(itemMap.values());
    }

    // add an item to the file
    @Override
    public Item addItem(Item item) throws VendingMachineException{
        itemMap=fio.readFile(ITEM_FILE);
        Item res = itemMap.put(item.getName(), item);
        fio.writeFile(new ArrayList<Item>(itemMap.values()));
        return res;
    }

    // remove an item from the file
    @Override
    public Item removeItem(Item item) throws VendingMachineException{
       itemMap=fio.readFile(ITEM_FILE);
       Item res=itemMap.remove(item.getName());
       fio.writeFile(new ArrayList<Item>(itemMap.values()));
       return res;
    }

    // change inventory count of an item from the file
    @Override
    public Item changeInventoryCount(Item item, int newCount) throws VendingMachineException{
        item.setNumInventoryItems(newCount);
        Item res=itemMap.put(item.getName(),item);
        fio.writeFile(new ArrayList<Item>(itemMap.values()));
        return res;
    }
    
}
