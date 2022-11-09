
package com.sal.vendingmachine.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class Item {
    // declare properties
    private String name;
    private BigDecimal cost;
    private int numInventoryItems;

    @Autowired
    public Item() {
    }

    public Item(String name, BigDecimal cost, int numInventoryItems) {
       this.name = name;
       this.cost = cost;
       this.numInventoryItems = numInventoryItems;
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
        return "Item{" + "name=" + name + ", cost=" + cost + ", numInventoryItems=" + numInventoryItems + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return numInventoryItems == item.numInventoryItems && Objects.equals(name, item.name) && Objects.equals(cost, item.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, numInventoryItems);
    }
}
