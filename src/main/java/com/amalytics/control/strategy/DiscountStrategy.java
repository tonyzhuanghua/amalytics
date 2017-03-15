package com.amalytics.control.strategy;

import com.amalytics.entity.Item;

import java.util.List;

/**
 * Created by zhuanghua on 2017/3/13.
 */
public abstract class DiscountStrategy {

    public abstract double calculatePrice(List<Item> items);

}
