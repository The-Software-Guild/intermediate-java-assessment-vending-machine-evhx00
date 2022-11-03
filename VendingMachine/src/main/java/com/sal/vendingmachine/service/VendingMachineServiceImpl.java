
package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.AuditDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author salajrawi
 */
public class VendingMachineServiceImpl implements VendingMachineService{

    private final VendingMachineDao dao;
    private final AuditDao adao;

    public VendingMachineServiceImpl() throws VendingMachineException {
        this.dao = new VendingMachineDaoImpl();
        this.adao = new AuditDaoImpl();
    }

    public VendingMachineServiceImpl(VendingMachineDao dao, AuditDao adao) {
        this.dao = dao;
        this.adao = adao;
    }

    //Get item, and checks if there are any in the inventory
    @Override
    public Item getItem(String name) throws VendingMachineException {
        Item item = dao.getItem(name);
        if(item.getNumInventoryItems() == 0) {
            throw new VendingMachineException("No item found with " + name);
        }
        return item;
    }

    //Lists all items
    @Override
    public List<Item> listAllItems() throws VendingMachineException{
        return dao.listAllItems()
                .stream()
                .filter(item->item.getNumInventoryItems()>0)
                .collect(Collectors.toList());
    }

    @Override
    public void addItem(Item item) throws VendingMachineException{
        dao.addItem(item);
    }

    @Override
    public void removeItem(Item item) throws VendingMachineException {
        dao.removeItem(item);
    }

    @Override
    public void changeInventoryCount(Item item, int newCount) throws VendingMachineException{
        item.setNumInventoryItems(newCount);
        dao.changeInventoryCount(item, newCount);
    }

    //To sell items, the Vending machine checks if items are in the inventory, and has enough funds
    @Override
    public BigDecimal sellItem(BigDecimal totalFunds, Item item) throws VendingMachineException, VendingMachineItemInventoryException, VendingMachineInsufficientFundsException {
        BigDecimal itemCost = item.getCost();

        if (item.getNumInventoryItems() <= 0) {
            throw new VendingMachineItemInventoryException("Item inventory is empty.");
        }
        else if (itemCost.compareTo(totalFunds) > 0) {
            throw new VendingMachineInsufficientFundsException("Not enough funds.");

        } else {
            changeInventoryCount(item, item.getNumInventoryItems() - 1);
            adao.writeAuditEntry("Item " + item.getName()+" is sold. Number in inventory: "+ item.getNumInventoryItems());
            return totalFunds.subtract(itemCost);
        }
    }
}
