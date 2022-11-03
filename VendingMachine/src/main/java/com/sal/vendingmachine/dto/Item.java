
package com.sal.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author salajrawi
 */
public class Item {
    private String name;
    private BigDecimal cost;
    private int numInventoryItems;

    //Creating and updating Item objects
    public Item(String name, BigDecimal cost, int numInventoryItems) {
        this.name = name;
        this.cost = cost;
        this.numInventoryItems = numInventoryItems;
    }
    
    public Item(String name, String costString, String inventoryString) {
        this.name = name;
        this.cost = new BigDecimal(costString);
        this.numInventoryItems = Integer.parseInt(inventoryString);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getNumInventoryItems() {
        return numInventoryItems;
    }

    public void setNumInventoryItems(int numInventoryItems) {
        this.numInventoryItems = numInventoryItems;
    }

    @Override
    public String toString() {
        return name + ": " + "$" + cost + ", Count: " + numInventoryItems;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, numInventoryItems);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return numInventoryItems == item.numInventoryItems && Objects.equals(name, item.name) && Objects.equals(cost, item.cost);
    }
}
