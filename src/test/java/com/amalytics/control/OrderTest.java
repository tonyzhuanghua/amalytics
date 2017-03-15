package com.amalytics.control;

import com.amalytics.control.strategy.DiscountType;
import com.amalytics.entity.Item;
import com.amalytics.entity.ItemGroup;
import org.junit.Before;
import org.junit.Test;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Created by zhuanghua on 2017/3/15.
 */
public class OrderTest {

    private ItemGroup itemGroup;
    private List<Item> items = new CopyOnWriteArrayList<>();
    private List<DiscountType> itemDiscounts = new CopyOnWriteArrayList<>();

    private String[] itemsArray, itemsArray1;

    @Before
    public void setUp() {
        items.add(new Item("Apple", 0.25));
        items.add(new Item("Orange", 0.3));
        items.add(new Item("Garlic", 0.15));
        items.add(new Item("Papaya", 0.5));

        itemDiscounts.add(DiscountType.NO_DISCOUNT);
        itemDiscounts.add(DiscountType.NO_DISCOUNT);
        itemDiscounts.add(DiscountType.NO_DISCOUNT);
        itemDiscounts.add(DiscountType.THREE_FOR_TWO);

        itemGroup = new ItemGroup(items, itemDiscounts);

        itemsArray = new String[]{
                "Apple", "Orange", "Garlic", "Papaya", "Papaya", "Papaya" , "Papaya"
        };

        itemsArray1 = new String[]{
                "Papaya", "Apple", "Orange", "Garlic", "Papaya"
        };

    }

    private Order cartItemsToOrder(String[] itemsArray) {


        List<String> cartItems = Arrays.asList(itemsArray);

        Order order = new Order(itemGroup);
        order.add(cartItems);
        return order;
    }

    @Test
    public void test_calculate_original_price() {

        assertEquals(cartItemsToOrder(itemsArray).calOrigianlPrice(), 2.7d, 0.001);
    }

    @Test
    public void test_calculate_final_price() {

        assertEquals(cartItemsToOrder(itemsArray).calFinalPrice(), 2.2d, 0.001);
    }


    @Test
    public void test_calculate_original_price_1() {

        assertEquals(cartItemsToOrder(itemsArray1).calOrigianlPrice(), 1.7d, 0.001);
    }


    @Test
    public void test_calculate_final_price_1() {

        assertEquals(cartItemsToOrder(itemsArray1).calFinalPrice(), 1.7d, 0.001);
    }

    @Test
    public void print_receipt(){

        System.out.println("-------------------------------------" );
        System.out.println("               Receipt               " );
        System.out.println("-------------------------------------" );

        Order order = cartItemsToOrder(itemsArray);
        List<Item> listItems = order.getItems();

        Map<String, DiscountType>  discountTypeMap = itemGroup.getDiscountTypes();
        listItems.forEach((item) -> {
            System.out.println(item.getName() + "     " + item.getPrice());

            if(discountTypeMap.get(item.getName())!=null && discountTypeMap.get(item.getName()) != DiscountType.NO_DISCOUNT)
                System.out.println("      discount applied: " + discountTypeMap.get(item.getName()));

        });

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.UP);



        System.out.println("-------------------------------------" );

        System.out.println("original:           "+ nf.format(order.calOrigianlPrice()));

        System.out.println("discount:           "+ nf.format((order.calOrigianlPrice()-order.calFinalPrice())));

        System.out.println("-------------------------------------" );

        System.out.println("you pay:            "+ nf.format(order.calFinalPrice())+"\n\n");
    }

}
