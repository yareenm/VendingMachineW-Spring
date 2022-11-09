
package com.sal.vendingmachine.dao;

public class VendingMachineException extends Exception{
    public VendingMachineException(String msg){
        super(msg);
    }
    
    public VendingMachineException(String msg, Throwable cause){
        super(msg,cause);
    }
}
