package com.amalytics.control.strategy;

import com.amalytics.entity.Item;

import java.util.List;

/**
 * Created by zhuanghua on 2017/3/13.
 */
public class NoDiscount extends DiscountStrategy {
    @Override
    public double calculatePrice(List<Item> items){
        if (items.size() == 0) return 0;
        return items.size() * items.get(0).getPrice();
    }
}
