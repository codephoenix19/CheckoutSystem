package Entity;

public class EndUser {
    /*
    * addProduct() --> to add a product to list of cart items
    * amendQuantity() --> to change the quantity of a particular item in the cart
    * removeProduct() --> remove the product from the list
    * and update total() --> is to be called at the end of each above method
    * This should consider the items added and apply existing discount strategies to calculate the final total
    * */
    private Checkout CART = Checkout.getInstance();

    public void addItem(Long productId){
        CART.addProduct(productId, 1, false);
    }

    public void changeQuantity(Long productId, int quantity){
        CART.amendQuantity(productId, quantity);
    }

    public void removeProduct(Long productId){
        CART.removeProduct(productId);
    }

    public void calculate(){
        CART.calculate();
    }

}
