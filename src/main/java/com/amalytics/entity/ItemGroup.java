package com.amalytics.entity;

import com.amalytics.control.strategy.DiscountStrategy;
import com.amalytics.control.strategy.DiscountType;
import com.amalytics.control.strategy.NoDiscount;
import com.amalytics.control.strategy.ThreeForTwo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ItemGroup {

    private Map<String, Item> listingItems = new HashMap<String, Item>();
    private Map<String, DiscountType> discountTypes = new HashMap<>();
    private Map<String, DiscountStrategy> discounts = new HashMap<>();

    private NoDiscount noDiscount;
    private ThreeForTwo threeForTwo;

    public Map<String, Item> getListingItems() {
        return listingItems;
    }

    public Map<String, DiscountStrategy> getDiscounts() {
        return discounts;
    }

    public Map<String, DiscountType> getDiscountTypes() {
        return discountTypes;
    }

    public DiscountStrategy defaultDiscountSetting() {
        return new NoDiscount();
    }

    public ItemGroup(List<Item> items, List<DiscountType> discountTypeList) {

        noDiscount = new NoDiscount();
        threeForTwo = new ThreeForTwo();

        IntStream.range(0, items.size()).forEach(i -> {
            Item item = items.get(i);

            listingItems.put(item.getName(), item);
            discountTypes.put(item.getName(), discountTypeList.get(i));

            if (discountTypeList.get(i).equals(DiscountType.THREE_FOR_TWO)) {
                discounts.put(item.getName(), threeForTwo);
            } else {
                discounts.put(item.getName(), noDiscount);
            }
        });
    }
}