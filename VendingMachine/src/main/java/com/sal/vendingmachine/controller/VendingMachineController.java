
package com.sal.vendingmachine.controller;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Coins;
import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.VendingMachineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineService service;

    public VendingMachineController() throws VendingMachineException {
    }
    @Autowired
    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
    }
    
    
    
    public void run(){
        BigDecimal balance = new BigDecimal(0.0);
        boolean start = true;
        try{
            while (start) {
            view.printMenu();
            view.displayBalance(balance);
            String operation = view.getMenuSelection();
            switch(operation) {
                case "1":  // add funds
                    balance=addFunds(balance);
                    break;
                case "2":  // buy items
                    try{
                        balance=buyItems(balance);
                    }catch(VendingMachineItemInventoryException | VendingMachineInsufficientFundsException e){
                        view.displayBalance(balance);
                        view.displayErrorMessage(e.getMessage());
                    }
                    break;
                case "3": // quit
                    try{
                        quit(balance);
                    }catch(VendingMachineInsufficientFundsException e){
                        view.displayBalance(balance);
                        view.displayErrorMessage(e.getMessage());
                    }
                    start = false;
                    break;
                default:
                    view.displayUnknownCommand();
            }
        }
        }catch(VendingMachineException e){
            view.displayErrorMessage(e.getMessage());
        }

    }
    
    public BigDecimal addFunds(BigDecimal balance){
      BigDecimal currentFund= view.displayAndGetFunds();

      balance = balance.add(currentFund);
      view.displayBalance(balance);

      return balance;
    }
    
    public BigDecimal buyItems(BigDecimal balance) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException{
        ArrayList<String> items = view.printAllItems(service.listAllItems());
        int selection = view.getItemSelection();
        Change changeDue = new Change();
        BigDecimal  updatedBalance = balance;
        String[] itemAsString = items.get(selection).split(",");
        Item currentItem = service.getItem(itemAsString[0]);

        try{
            updatedBalance=service.sellItem(balance, currentItem);
        }catch(VendingMachineException e){
            view.displayErrorMessage(e.getMessage());
        }

        view.purchaseSucceeded();

        HashMap<Coins,Integer> changes = changeDue.getChange(updatedBalance);
        view.printChanges(changes);

        return balance.subtract(currentItem.getCost());
    }
    
    public void quit(BigDecimal balance) throws VendingMachineInsufficientFundsException{
        view.displayBalance(balance);
        view.displayQuitMessage();
    }
}
