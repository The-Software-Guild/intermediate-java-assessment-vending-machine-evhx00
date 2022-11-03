
package com.sal.vendingmachine.controller;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;

/**
 *
 * @author Salajrawi
 */
public class VendingMachineController {

    private final VendingMachineView view;
    private final VendingMachineService service;
    BigDecimal balance = new BigDecimal("0.0");

    // Initialize View and Service
    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
    }

    //runs application
    public void run(){
        boolean start = true;
        try{
            while (start) {
                view.printMenu();
                view.displayBalance(balance);
                String operation = view.getMenuSelection();
                switch(operation) {
                    case "1":  // add funds
                        balance = addFunds(balance);
                        break;
                    case "2":  // buy items
                        try{
                            balance = buyItems(balance);
                        }catch(VendingMachineItemInventoryException | VendingMachineInsufficientFundsException e){
                            view.displayBalance(balance);
                            view.displayErrorMessage(e.getMessage());
                        }
                        break;
                    case "3": // quit
                        quit(balance);
                        start = false;
                        break;
                    default:
                        view.displayUnknownCommand();
                }
            }
        }catch(VendingMachineException e){
            view.displayErrorMessage(e.getMessage());
        } catch (VendingMachineInsufficientFundsException e) {
            throw new RuntimeException(e);
        }
    }

    //Method for user to add funds to VendingMachine
    public BigDecimal addFunds(BigDecimal balance){
        return balance.add(view.displayAndGetFunds());
    }

    //Method for user to add Items to VendingMachine
    public BigDecimal buyItems(BigDecimal balance) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException {
        view.printAllItems(service.listAllItems());

        try {
            balance = service.sellItem(balance, service.getItem(view.getItemSelection()));
            view.purchaseSucceeded();
        } catch (VendingMachineException e) {
            view.displayErrorMessage(e.getMessage());
        }
        return balance;
    }

    //Method when user wants to quit && provides change if balance is > 0
    public void quit(BigDecimal balance) throws VendingMachineInsufficientFundsException{
        if (balance.compareTo(BigDecimal.valueOf(0)) > 0){
            Change change = new Change();
            view.printChanges(change.getChange(balance));
        }
        view.displayQuitMessage();
    }
}
