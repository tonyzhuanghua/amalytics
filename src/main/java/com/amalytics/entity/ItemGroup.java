package com.amalytics.entity;

import com.amalytics.control.strategy.DiscountStrategy;
import com.amalytics.control.strategy.DiscountType;
import com.amalytics.control.strategy.NoDiscount;
import com.amalytics.control.strategy.ThreeForTwo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/*
* 这个类是商家设置好的,商品打折集合对照表。买家下订单后,商家要对照这个类来计算折扣
* 这个类其实是由商品, 打折名称两个Map, 生成一个 <商品名, 打折策略> 的map
* 生成这个打折策略map过程在构造函数中,使得这个类实例化后,打折策略map已经生成
* 打折策略抽象类DiscountStrategy只有一个计算价格的方法
* 所以逻辑关系可以以下表述
* 商品Item类 + 商品打折信息 -> 商品打折策略 + 订单 -> 计算出价格
* */
public class ItemGroup {

    // 商家准备好的商品列表, key是商品名称不可重复, item是该商品名称对应的商品实例
    private Map<String, Item> listingItems = new HashMap<String, Item>();
    // 这里的 DiscountType 是enum类里定义的折扣名称
    private Map<String, DiscountType> discountTypes = new HashMap<>();
    // 下面这个变量是由构造函数生成的,
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

    // 该构造函数生成打折策略map <商品名, 打折策略>
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