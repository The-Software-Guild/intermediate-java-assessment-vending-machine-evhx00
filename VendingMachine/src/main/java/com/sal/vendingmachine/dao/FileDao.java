
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import java.util.List;
import java.util.Map;

/**
 *
 * @author salajrawi
 */
public interface FileDao {
    Item unmarshallItem(String line);
    String marshallItem(Item item);
    void writeFile(List<Item> list) throws VendingMachineException;
    Map<String,Item> readFile(String file) throws VendingMachineException;
}
