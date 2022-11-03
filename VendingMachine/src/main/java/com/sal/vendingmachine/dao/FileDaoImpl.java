
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.FileDao;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author salajrawi
 */
public class FileDaoImpl implements FileDao {

    private static final String ITEM_FILE = "items.txt";
    private static final String DELIMITER = ",";

    //Converts an Item object into a String
    @Override
    public String marshallItem(Item item) {
        String text = item.getName() + DELIMITER;
               text += item.getCost() + DELIMITER;
               text += item.getNumInventoryItems();
               return text;
    }

    //Splits String into data at the delimiter
    @Override
    public Item unmarshallItem(String line) {
        String[] data = line.split(DELIMITER);

        return new Item(data[0], data[1], data[2]);
    }

    //Reads text file and unmarshalls
    @Override
    public Map<String, Item> readFile(String file) throws VendingMachineException {
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(ITEM_FILE)));
            Map<String, Item> itemMap = new HashMap<>();

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                Item currentItem = unmarshallItem(currentLine);
                itemMap.put(currentItem.getName(), currentItem);
            }
            return itemMap;
        } catch (FileNotFoundException e) {
            throw new VendingMachineException("File not found", e);
        }
    }

    //Marshall's items and writes to text file
    @Override
    public void writeFile(List<Item> list) throws VendingMachineException {
        try {
            PrintWriter output = new PrintWriter(new FileWriter(ITEM_FILE));

            String text;

            for (Item item : list) {
                text = marshallItem(item);
                output.println(text);
            }

            output.flush();

            output.close();
        } catch (IOException e) {
            throw new VendingMachineException("Could not save item data", e);
        }
    }
}
