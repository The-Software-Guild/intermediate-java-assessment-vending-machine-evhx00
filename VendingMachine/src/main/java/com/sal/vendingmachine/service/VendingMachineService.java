
package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author salajrawi
 */
public interface VendingMachineService {
    Item getItem(String name) throws VendingMachineException;
    List<Item> listAllItems() throws VendingMachineException;
    void addItem(Item item) throws VendingMachineException;
    void removeItem(Item item) throws VendingMachineException;
    void changeInventoryCount(Item item, int newCount) throws VendingMachineException;
    BigDecimal sellItem(BigDecimal totalFunds,Item item) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException;
}
