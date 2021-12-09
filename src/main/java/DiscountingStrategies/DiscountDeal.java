package DiscountingStrategies;

import Custom.Tuple3;
import Entity.Checkout;

public class DiscountDeal implements Deal {
    /*
    Buy 2 airpods and get 25% off
     */
    private final Long productId;
    private final Integer quantity;
    private final Integer discountPercentage;
    private Checkout CART = Checkout.getInstance();

    public DiscountDeal(Long productId, Integer quantity, Integer discountPercentage) {
        this.productId = productId;
        this.quantity = quantity;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public void apply() {
        // modify logic to amend price of product Id
        if(CART.getProductPriceMapper().containsKey(productId)){
            Tuple3 tuple = CART.getProductPriceMapper().get(productId);
            int qty = (Integer) tuple.getSecond();
            if(qty >= quantity && qty%quantity == 0){
                Double price = (Double) tuple.getThird();
                Double updatedPrice = (price - price*discountPercentage/100);
                CART.amendPrice(productId, updatedPrice);
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
