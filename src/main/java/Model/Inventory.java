package Model;

import DiscountingStrategies.Deal;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
//    static Logger LOGGER = Logger.getLogger(Inventory.class);
    private List<Product> products;
    private static volatile Inventory INV_INSTANCE = null;
    private List<Deal> deals = new ArrayList<>();

    private Inventory() {
        this.products = new ArrayList<Product>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Product getProduct(Long productId){
        if(!checkIfProductIsInInventory(productId)) return null;
        return this.products.stream()
                .filter(x -> x.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public static Inventory getInstance(){
        if(INV_INSTANCE == null){
            synchronized (Inventory.class){
                if(INV_INSTANCE == null)
                    INV_INSTANCE = new Inventory();
            }
        }
        return INV_INSTANCE;
    }

    public void addProduct(Product product) throws RuntimeException{
        // If product exists in inventory throw an exception
        if(checkIfProductIsInInventory(product.getId()))
            throw new RuntimeException("Product already exists in the inventory!");
        products.add(product);
    }

    public void modifyProductPrice(Long productId, Double updatedPrice) throws RuntimeException{
        if(!checkIfProductIsInInventory(productId))
            throw new RuntimeException("Product is not present in the inventory, try creating a new product");
        for(Product product : products){
            if(product.getId().equals(productId)) {
                product.setPrice(updatedPrice);
                break;
            }
        }
    }

    public void removeProduct(Long productId){
        int i = 0;
        int indexToBeRemoved = -1;
        for(i=0; i<products.size(); i++){
            if(products.get(i).getId().equals(productId)) {
                indexToBeRemoved = i;
                break;
            }
        }
        if(indexToBeRemoved != -1)
            products.remove(indexToBeRemoved);
    }

    public boolean checkIfProductIsInInventory(Long productId){
        for(Product p : products){
            if(p.getId().equals(productId))
                return true;
        }
        return false;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public void listDeals(){
        this.deals.forEach(x -> System.out.println(x));
    }

    public void listProducts(){
        this.products.forEach(x -> System.out.println(x));
    }

    public void addDiscountDealToDeals(Deal deal){
//        LOGGER.info("Adding discount deal to list of deals " + deal.toString());
        this.deals.add(deal);
    }

    public void addBundleDealToDeals(Deal deal){
//        LOGGER.info("Adding bundle deal to list of deals " + deal.toString());
        this.deals.add(deal);
    }

    public void clearProducts(){
        this.products = new ArrayList<>();
    }

}
