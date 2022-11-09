
package com.sal.vendingmachine.service;

// This exception class is for item's inventory count. If the user wants to buy
// something that out of stock, it will give an error.
public class VendingMachineItemInventoryException extends Exception{
    public VendingMachineItemInventoryException(String msg){
        super(msg);
    }
    
    public VendingMachineItemInventoryException(String msg, Throwable cause){
        super(msg,cause);
    }
}
