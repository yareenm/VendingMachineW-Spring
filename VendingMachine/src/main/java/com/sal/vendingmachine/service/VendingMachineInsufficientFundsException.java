
package com.sal.vendingmachine.service;

// This exception class is for user's insufficient fund. If the user wants to buy
// something that costs more than funds, it will give an error.
public class VendingMachineInsufficientFundsException extends Exception{
    public VendingMachineInsufficientFundsException(String message) {
        super(message);
    }

    public VendingMachineInsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
