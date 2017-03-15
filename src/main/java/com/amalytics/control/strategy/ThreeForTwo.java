package com.amalytics.control.strategy;

import com.amalytics.entity.Item;

import java.util.List;

/**
 * Created by zhuanghua on 2017/3/13.
 */
public class ThreeForTwo extends DiscountStrategy {

    @Override
    public double calculatePrice(List<Item> items) {

        if (items.size() == 0) {
            return 0;
        }

        int group = items.size() / 3;
        int remain = items.size() % 3;

        return group * items.get(0).getPrice() * 2 + remain * items.get(0).getPrice();
    }
}
