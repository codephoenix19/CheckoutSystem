package Model;

import DiscountingStrategies.BundleDeal;
import DiscountingStrategies.Deal;
import DiscountingStrategies.DiscountDeal;
import UserControls.StoreOwner;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StoreOwnerTest {
    StoreOwner storeOwner;
    Inventory inventory;

    @BeforeEach
    void setUp(){
        inventory = Inventory.getInstance();
        storeOwner = new StoreOwner();
    }

    @Test
    void createProduct() {
        Long productId = 12999L;
        String name = "filter";
        String description = "mac filter cloth";
        Double price = (double)45;
        storeOwner.createProduct(productId, name, description, price);
        assertTrue(inventory.getProducts().stream().anyMatch(product -> product.getId().equals(productId) && product.getName().equals(name) && product.getDescription().equals(description) && product.getPrice() == price));
    }

    @Test
    void amendPrice() {
        Long productId = 12999L;
        String name = "filter";
        String description = "mac filter cloth";
        Double origPrice = (double)45;
        Double price = 45.77;
        storeOwner.createProduct(productId, name, description, origPrice);
        inventory.modifyProductPrice(productId, price);
        if(inventory.checkIfProductIsInInventory(productId)){
            assertTrue(inventory.getProduct(productId).getPrice() == price);
        } else{
            assertTrue(false);
        }
    }

    @Test
    void removeProduct() {
        createTestProduct();
        Long productId = 12999L;
        if(inventory.checkIfProductIsInInventory(productId)){
            inventory.removeProduct(productId);
            assertTrue(!inventory.checkIfProductIsInInventory(productId));
        } else
            assertTrue(false);

    }

    @Test
    void createDiscountDeal() {
        createTestProduct();
        Long productId = 12999L;
        int quantity = 2;
        int percentage = 25;
        Deal deal = new DiscountDeal(productId, quantity, percentage);
        inventory.addDiscountDealToDeals(deal);
        assertTrue(inventory.getDeals().stream().anyMatch(offer -> offer.equals(deal)));
    }

    @Test
    void createBundleDeal() {
        createTestProduct();
        Long productIdA = 12345L;
        String name = "macbook air";
        String description = "macbook air";
        Double price = (double)965.35;
        storeOwner.createProduct(productIdA, name, description, price);
        Long productIdB = 12999L;
        int quantityB = 1;
        Deal deal = new BundleDeal(productIdA, 1, productIdB, quantityB);
        inventory.addBundleDealToDeals(deal);
        assertTrue(inventory.getDeals().stream().anyMatch(offer -> offer.equals(deal)));
    }

    @AfterEach
    void cleanUp(){
        inventory.clearProducts();
    }

    void createTestProduct(){
        Long productId = 12999L;
        String name = "filter";
        String description = "mac filter cloth";
        Double price = (double)45;
        storeOwner.createProduct(productId, name, description, price);
    }
}