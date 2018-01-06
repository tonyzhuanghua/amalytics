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

    // 这个是order的一个固有变量,记录了订单里每个具体的商品,某种商品可能不止一个
    // 这些商品集合是一个无需顺序的单个item集合,里面包含重复的item, 比如 apple, orange, apple, papaya, garlic, orange....
    private List<Item> items = new ArrayList<>();

    // itemGroup是店家预先设置的,里面包含了两个重要的Map类: 商品列表Map(不会重复),以及对应的折扣信息Map
    //为了将【商品列表】和【折扣列表】对应起来,这两个变量都是map,而且都是以商品名称作为key
    private ItemGroup itemGroup;



    // 这个构造函数是以 itemGroup 为参数构造的,意味着在实例化这个Order类之前,itemGroup必须已经准备好了
    // 也就是说,折扣信息在订单前就存在

    public Order(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public List<Item> getItems() {
        return items;
    }


    //这个add方法是将购物篮里面的物品逐一加到订单了去,那么这里面物品就允许重复
    public void add(List<String> itemNames) {

        itemNames.forEach((itemName) -> {

            Item item = itemGroup.getListingItems().get(itemName);

            items.add(new Item(item.getName(), item.getPrice()));

        });
    }



    //对这个订单实例进行实际价格的运算
    public double calFinalPrice() {
        double finalPrice = 0;

        //这一步是将购物篮里的商品按照商品名做主键,商品集合作为值, 生成一个map, 用来计算最后价格
        // map的键是唯一的商品名, 值是订单里这个商品集合, 该商品集合讲在下面,使用对于的折扣策略来计算总价
        Map<String, List<Item>> groupItems = items.stream()
                .collect(Collectors.groupingBy(Item::getName));

        //遍历上面的map, 对每一类商品算一个总价
        for (Map.Entry<String, List<Item>> listItemEntry : groupItems.entrySet()) {
            finalPrice += itemGroup.getDiscounts() // 返回总的折扣策略集合
                    .get(listItemEntry.getKey()) // 获得该种商品的具体折扣策略,返回的是一个DiscountStrategy实例
                    .calculatePrice(listItemEntry.getValue()); //由折扣策略类计算出N个同类商品最后折扣价格
                                                               // 折扣策略类的主要功能就是算出N个同类商品,打折后的价格
                                                               // 所以订单里某个商品的集合的总价是交给策略类来算的
        }

        return finalPrice;
    }


    public double calOrigianlPrice() {
        double originalPrice = 0;
        Map<String, List<Item>> groupItems = items.stream().collect(Collectors.groupingBy(Item::getName));

        for (Map.Entry<String, List<Item>> listItemEntry : groupItems.entrySet()) {
            originalPrice += itemGroup.defaultDiscountSetting() //返回无折扣策略
                    .calculatePrice(listItemEntry.getValue());
        }

        return originalPrice;
    }


}
