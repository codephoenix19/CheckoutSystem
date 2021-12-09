package DiscountingStrategies;

import Custom.Tuple3;
import Entity.Checkout;

public class BundleDeal implements Deal {
    /*
    Buy 2 macbook airs and get 1 keyboard free
     */
    Long productIdA;
    int quantityA;
    Long productIdB;
    int quantityB;

    private Checkout CART = Checkout.getInstance();

    public BundleDeal(Long productIdA, int quantityA, Long productIdB, int quantityB){
        // do something here
        this.productIdA = productIdA;
        this.productIdB = productIdB;
        this.quantityA = quantityA;
        this.quantityB = quantityB;
    }

    @Override
    public void apply() {
        // logic to check if productIdA with quantity >= qtyA exists
        if(!CART.getProductPriceMapper().containsKey(productIdA))
            return;
        Tuple3 tuple = CART.getProductPriceMapper().get(productIdA);
        int quantity = (Integer)tuple.getSecond();
        if(quantity < quantityA) return;
        CART.addProduct(productIdB, (quantity/quantityA)*quantityB, true);
    }

    @Override
    public String toString() {
        return "BundleDeal{" +
                "productIdA=" + productIdA +
                ", quantityA=" + quantityA +
                ", productIdB=" + productIdB +
                ", quantityB=" + quantityB +
                '}';
    }
}
