package Entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EndUserTest {
    Checkout cart;
    EndUser endUser;
    Inventory inventory;

    @BeforeEach
    void setUp(){
        cart = Checkout.getInstance();
        endUser = new EndUser();
        inventory = Inventory.getInstance();
        init();
    }

    @AfterEach
    void cleanUp(){
        cart.emptyCart();
    }

    void init(){
        List<Product> list = inventory.getProducts();
        list.add(new Product(12345L, "Macbook Pro", "Apple Macbook Pro laptop with 512GB SSD and 8GB RAM", 1299.34));
        list.add(new Product(12346L, "Macbook Air", "Apple Macbook Air laptop with 256GB SSD and 8GB RAM", 899.34));
        list.add(new Product(12347L, "Lightning keyboard", "Apple Wireless Keyboard", 250.50));
        list.add(new Product(12348L, "Lightning Mouse", "Apple Wireless Mouse", 150.50));
        list.add(new Product(12349L, "Airpods", "Apple Airpods", 89.57));
    }

    @Test
    @DisplayName("Test to add an item and validate by checking if the item exists in cart")
    void addItem() {
        // test method to add a default item from inventory say "12345" into cart
        // check by getting cart items to see if the product exists
        Long productId1 = 12345L;
        Long productId2 = 12346L;
        endUser.addItem(productId1);
        endUser.addItem(productId2);
        assertTrue(cart.getProductPriceMapper().containsKey(productId1));
        assertTrue(cart.getProductPriceMapper().containsKey(productId2));
    }

    @Test
    @DisplayName("Test if quantity of product 12345 is changed as expected")
    void changeQuantity() {
        Long productId1 = 12345L;
        Long productId2 = 12346L;
        int quantity1 = 2;
        int quantity2 = 0;
        endUser.addItem(productId1);
        endUser.addItem(productId2);
        endUser.changeQuantity(productId1, quantity1);
        endUser.changeQuantity(productId2, quantity2);
        if(cart.getProductPriceMapper().containsKey(productId1)){
            assertTrue(cart.getProductPriceMapper().get(productId1).getSecond() == quantity1);
        }
        assertTrue(!cart.getProductPriceMapper().containsKey(productId2));
    }

    @Test
    @DisplayName("Test to check if a product is removed from the cart")
    void removeProduct() {
        Long productId = 12345L;
        endUser.addItem(productId);
        endUser.removeProduct(productId);
        assertTrue(!cart.getProductPriceMapper().containsKey(productId));
    }

    @Test
    @DisplayName("Test to check if calculate logic is performed and total price is updated after applying deals and discounts")
    void calculate() {
        // here need to create deal and apply on it
        Long productId1 = 12345L;
        Long productId2 = 12346L;
        endUser.addItem(productId1);
        endUser.addItem(productId2);
        endUser.calculate();
        assertEquals(cart.getTotalPrice(), 2198.68);
    }
}