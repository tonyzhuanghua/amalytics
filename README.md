# amalytics

这是一个典型的策略模型，注释写在 Order.java和ItemGroup.java这两个关键类里。

Order的价格计算思路是将无序，可能有重复的商品购物篮，根据商品名称进行分组，然后根据ItemGroup的打折策略（是一个map <商品名，打折策略类>），对分组后的Order进行价格计算。每组商品价格计算的最终结果，来自于不同的策略类实现。


Write a simple program that calculates the price of a shopping basket. 
Items are presented one at a time, in a list, identiﬁed by name 
- for example "Apple" or "Garlic". 
 
The basket can contain any item multiple times.
 
Items are priced as follows:
- Apples are 25p each
- Oranges are 30p each
- Garlic are 15p each
- Papayas are 50p each, but are available as ‘three for the price of two'
Given a list of shopping, calculate the total cost of those items. 
The output should be displayed similar to what you would expect to see on a receipt.


please run
"mvn clean test"
