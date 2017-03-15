package com.amalytics.control;


import com.amalytics.entity.Item;
import com.amalytics.entity.ItemGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhuanghua on 2017/3/13.
 */
public class Order {

    private List<Item> items = new ArrayList<>();
    private ItemGroup itemGroup;

    public Order(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public List<Item> getItems() {
        return items;
    }


    public void add(List<String> itemNames) {

        itemNames.forEach((itemName) -> {

            Item inventoryItem = itemGroup.getListingItems().get(itemName);

            items.add(new Item(inventoryItem.getName(), inventoryItem.getPrice()));

        });
    }

    public double calFinalPrice() {
        double finalPrice = 0;
        Map<String, List<Item>> groupItems = items.stream().collect(Collectors.groupingByConcurrent(Item::getName));

        for (Map.Entry<String, List<Item>> listItemEntry : groupItems.entrySet()) {
            finalPrice += itemGroup.getDiscounts().get(listItemEntry.getKey()).calculatePrice(listItemEntry.getValue());
        }

        return finalPrice;
    }


    public double calOrigianlPrice() {
        double originalPrice = 0;
        Map<String, List<Item>> groupItems = items.stream().collect(Collectors.groupingByConcurrent(Item::getName));

        for (Map.Entry<String, List<Item>> listItemEntry : groupItems.entrySet()) {
            originalPrice += itemGroup.defaultDiscountSetting().calculatePrice(listItemEntry.getValue());
        }

        return originalPrice;
    }


}
