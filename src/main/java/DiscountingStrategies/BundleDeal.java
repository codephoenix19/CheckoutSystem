package DiscountingStrategies;

import Custom.Tuple3;
import Model.Checkout;
import Model.Inventory;
import Model.Product;

import java.util.Map;

public class BundleDeal implements Deal {
    /*
    Buy 2 macbook airs and get 1 keyboard free
     */
    Long productIdA;
    int quantityA;
    Long productIdB;
    int quantityB;

    private Checkout CART = Checkout.getInstance();
    private Inventory inventory = Inventory.getInstance();

    public BundleDeal(Long productIdA, int quantityA, Long productIdB, int quantityB){
        // do something here
        this.productIdA = productIdA;
        this.productIdB = productIdB;
        this.quantityA = quantityA;
        this.quantityB = quantityB;
    }

    @Override
    public void apply(Map<Long, Tuple3<String, Integer, Double>> cart) {
        // logic to check if productIdA with quantity >= qtyA exists
        if(!cart.containsKey(productIdA))
            return;
        Tuple3 tuple = cart.get(productIdA);
        int quantity = (Integer)tuple.getSecond();
        if(quantity < quantityA) return;
        // Write logic to check if productIdB is already present in cart
        // Get product details from inventory
        // If present, then add updatedQty = quantity/quantityA*quantityB, get current price * quantity of productIdB and
        // Divide it by updatedQty and set the new price to this value
        // If not, then add to cart with updatedQty and original price
        Product productInfo = inventory.getProduct(productIdB);
        int updatedQuantity = 0;
        Tuple3 tuple2;
        if(cart.containsKey(productIdB)){
            tuple2 = cart.get(productIdB);
            int originalQty = (int)tuple2.getSecond();
            updatedQuantity = (quantityB/quantityA)*originalQty;
            Double updatedPrice = productInfo.getPrice()*originalQty/updatedQuantity;
            tuple2.setThird(updatedPrice);
            tuple2.setSecond(originalQty + updatedQuantity);
        } else {
            tuple2 = new Tuple3(productInfo.getName(), 1, productInfo.getPrice());
        }
        cart.put(productIdB,tuple2);
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
