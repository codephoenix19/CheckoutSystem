package DiscountingStrategies;

import Custom.Tuple3;
import Model.Inventory;
import Model.Product;

import java.util.Map;

public class DiscountDeal implements Deal {
    /*
    Buy 2 airpods and get 25% off
     */
    private final Long productId;
    private final Integer quantity;
    private final Integer discountPercentage;
    private Inventory inventory = Inventory.getInstance();

    public DiscountDeal(Long productId, Integer quantity, Integer discountPercentage) {
        this.productId = productId;
        this.quantity = quantity;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public void apply(Map<Long, Tuple3<String, Integer, Double>> cart) {
        // logic is to get product details from inventory
        // Then check if product exists in cart and sufficient quantity is present
        // If so, apply discount percentage on the total price
        if(cart.containsKey(productId)){
            Tuple3 tuple = cart.get(productId);
            int qty = (Integer) tuple.getSecond();
            if(qty >= quantity){
                Product productInfo = inventory.getProduct(productId);
                Double price = productInfo.getPrice();
                Double updatedPrice = (price - price*discountPercentage/100);
                tuple.setThird(updatedPrice);
                cart.put(productId, tuple);
            }
        }
    }

    @Override
    public String toString() {
        return "DiscountDeal{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}
