package Model;

import Custom.Tuple3;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Checkout {
    /*
    Implement singleton instance of checkout for reference across all classes
     */
    private static volatile Checkout INSTANCE = null;

    private double totalPrice;

    // To maintain Map(product id --> name, quantity, price)
    private Map<Long, Tuple3<String, Integer, Double>> productPriceMapper;

    private Inventory inventory = Inventory.getInstance();

    public Map<Long, Tuple3<String, Integer, Double>> getProductPriceMapper() {
        return productPriceMapper;
    }

    public void setProductPriceMapper(Map<Long, Tuple3<String, Integer, Double>> productPriceMapper) {
        this.productPriceMapper = productPriceMapper;
    }

    private Checkout(){
        if(INSTANCE != null)
            throw new RuntimeException("Class instance is to be obtained through getInstance method only");
        this.productPriceMapper = new HashMap<Long, Tuple3<String, Integer, Double>>();
    }

    public static Checkout getInstance(){
        if(INSTANCE == null) {
            synchronized(Checkout.class) {
                if(INSTANCE == null) INSTANCE = new Checkout();
            }
        }
        return INSTANCE;
    }

    public void calculate(){
        // first apply all deals on the cart items then calculate total price
        inventory.getDeals().forEach(deal -> deal.apply(this.productPriceMapper));
        // logic to iterate over products, get quantities and multiply with price
        Iterator mapIterator = this.productPriceMapper.entrySet().iterator();
        while(mapIterator.hasNext()){
            Map.Entry element = (Map.Entry) mapIterator.next();
            if(element != null) {
                Tuple3<String, Integer, Double> tuple3 = (Tuple3<String, Integer, Double>) element.getValue();
                this.totalPrice += (tuple3.getSecond() * tuple3.getThird());
            }
        }
    }

    public void addProductIntoCart(long productId, int quantity, boolean isDeal){
        Tuple3 tuple = checkIfProductExists(productId);
        if(tuple!=null){
            int qty = (int)tuple.getSecond();
            tuple.setSecond(qty + 1);
        } else {
            Product foundItem = inventory.getProduct(productId);
            if(foundItem != null) {
                String productName = foundItem.getName();
                Double price = isDeal ? 0 : foundItem.getPrice();
                tuple = new Tuple3<String, Integer, Double>(productName, quantity, price);
            } else
                return;
        }
        this.setProductValuesInMapper(productId, tuple);
    }

    public void amendQuantityOfCartItem(long productId, int quantity){
        Tuple3 tuple = checkIfProductExists(productId);
        if(tuple == null) {
            this.addProductIntoCart(productId, quantity, false);
//            throw new RuntimeException("No such product exists");
        }
        if(quantity == 0) {
            this.removeProductFromCart(productId);
            return;
        }
        tuple.setSecond(quantity);
        this.setProductValuesInMapper(productId, tuple);
    }

    public void amendPriceOfCartItem(long productId, Double price){
        if(this.productPriceMapper.containsKey(productId)){
            Tuple3 tuple = this.productPriceMapper.get(productId);
            tuple.setThird(price);
            setProductValuesInMapper(productId, tuple);
        }
    }

    public void removeProductFromCart(long productId){
        Tuple3 tuple = checkIfProductExists(productId);
        if(tuple == null)
            throw new RuntimeException("You cannot remove a product which is not present in the cart");
        this.productPriceMapper.remove(productId);
    }

    public Tuple3 checkIfProductExists(long productId){
        if(this.productPriceMapper.containsKey(productId)){
            return this.productPriceMapper.get(productId);
        }
        return null;
    }

    private void setProductValuesInMapper(long productId, Tuple3 tuple){
            productPriceMapper.put(productId, tuple);
    }

    public void printCartItems(){
        Iterator mapIterator = this.productPriceMapper.entrySet().iterator();
        while(mapIterator.hasNext()){
            Map.Entry<Long, Tuple3> entry = (Map.Entry<Long, Tuple3>) mapIterator.next();
            System.out.println("Product Id : " + entry.getKey() + " With values as : " + entry.getValue());
        }
    }

    public void emptyCart(){
        this.productPriceMapper = new HashMap<Long, Tuple3<String, Integer, Double>>();
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
