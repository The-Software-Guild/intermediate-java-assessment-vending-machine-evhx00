
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Salajrawi
 */
public class VendingMachineDaoImpl implements VendingMachineDao{

    Map<String,Item> itemMap;
    final FileDao fio;
    private static final String ITEM_FILE = "items.txt";

    public VendingMachineDaoImpl() throws VendingMachineException{
        fio = new FileDaoImpl();
        itemMap = fio.readFile(ITEM_FILE);
    }


    @Override
    public Item getItem(String name) throws VendingMachineException{
        try{
            itemMap = fio.readFile(ITEM_FILE);
            Item item = itemMap.get(name);
            item.getCost(); //test that it's valid

            return item;
        }
        catch (NullPointerException e) {
            throw new VendingMachineException("Could not find item " + name);
        }
    }

    @Override
    public List<Item> listAllItems() throws VendingMachineException{
        itemMap = fio.readFile(ITEM_FILE);
        return new ArrayList<>(itemMap.values());
    }

    @Override
    public Item addItem(Item item) throws VendingMachineException{
        itemMap = fio.readFile(ITEM_FILE);
        Item res = itemMap.put(item.getName(), item);
        fio.writeFile(new ArrayList<>(itemMap.values()));
        return res;
    }

    @Override
    public Item removeItem(Item item) throws VendingMachineException{
        itemMap = fio.readFile(ITEM_FILE);
        Item res = itemMap.remove(item.getName());
        fio.writeFile(new ArrayList<>(itemMap.values()));
        return res;
    }

    @Override
    public void changeInventoryCount(Item item, int newCount) throws VendingMachineException{
        item.setNumInventoryItems(newCount);
        Item res = itemMap.put(item.getName(),item);
        fio.writeFile(new ArrayList<>(itemMap.values()));
    }

}
