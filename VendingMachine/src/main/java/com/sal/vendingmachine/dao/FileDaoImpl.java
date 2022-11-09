
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class FileDaoImpl implements FileDao{
    // declare variables
    @Autowired
    private Map<String,Item> items = new HashMap<>();
    private static final String ITEM_FILE = "items.txt";
    private static final String DELIMITER = ",";

    // This function is used for read from a file
    // It splits each line according to delimiter.
    // It returns an item as a String
    @Override
    public Item unmarshallItem(String line) {
        String[] itemTokens = line.split(DELIMITER);
        String itemName;
        BigDecimal cost;
        Item itemFromFile = new Item();

        int numInventoryItems;
        if(itemTokens.length > 1) {
            itemName = itemTokens[0];
            cost = BigDecimal.valueOf(Double.valueOf(itemTokens[1]));
            numInventoryItems = Integer.parseInt(itemTokens[2]);
            itemFromFile = new Item(itemName, cost, numInventoryItems);

        }
        return itemFromFile;
    }

    // This function is used for write to a file
    // It returns a String which contains item and its properties
    @Override
    public String marshallItem(Item item) {
        return item.getName() + DELIMITER + item.getCost() + DELIMITER + item.getNumInventoryItems();
    }

    // This function is used for write to a file. It uses marshall method here.
    // It writes line by line, not the all file at once. At every line it calls marshall method.
    public void writeFile(List<Item> list) throws VendingMachineException{
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ITEM_FILE));
        }
        catch(IOException e)
        {
            throw new VendingMachineException("Could not save item data", e);
        }

        String itemAsText;
        for(Item currentItem : list){

            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }

        out.close();
    }

    // This function is used for read from a file. It uses unmarshall method here.
    // It reads line by line, not the all file at once. At every line it calls unmarshall method.
    @Override
    public Map<String,Item> readFile(String file) throws VendingMachineException{
        Scanner scanner;

        try {
            scanner = new Scanner( new BufferedReader( new FileReader(file)));
        }
        catch(FileNotFoundException e)
        {
            throw new VendingMachineException("File not found", e);
        }
        String currentLine;
        Item currentItem;
        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getName(), currentItem);

        }
        scanner.close();
        return items;
    }
    
}
