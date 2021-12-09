package Entity;

import DiscountingStrategies.BundleDeal;
import DiscountingStrategies.Deal;
import DiscountingStrategies.DiscountDeal;

public class StoreOwner {
   /*
    - Create a new product
    - Amend the price/description of a product
    - Remove a product
    - Apply discount deals for products (eg buy 1 get 50% off the second)
    - Apply bundle deals (eg buy 1 laptop, get a mouse for free)
    */
    private Checkout cart = Checkout.getInstance();
    private Inventory inventory = Inventory.getInstance();

    public int createProduct(Long productId, String name, String description, Double price) {
        // add new products list into inventory list for now - later into products database
        try {
            Product product = new Product(productId, name, description, price);
            inventory.addProduct(product);
        } catch(Exception ex){
            System.out.println("Exception is " + ex.getMessage());
            return -1;
        }
        return 1;
    }

    public void amendPrice(Long productId, Double price){
        // update product price in inventory list
        cart.amendPrice(productId, price);
    }

    public void removeProduct(Long productId){
        // remove product from enum list
        cart.removeProduct(productId);
    }

    public void createDiscountDeal(Long productId, int quantity, int percentage){
        Deal deal = new DiscountDeal(productId, quantity, percentage);
        inventory.addDiscountDealToDeals(deal);
    }

    public void createBundleDeal(Long productIdA, int quantityA, Long productIdB, int quantityB){
        Deal deal = new BundleDeal(productIdA, quantityA, productIdB, quantityB);
        inventory.addBundleDealToDeals(deal);
    }

}
