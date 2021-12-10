import Model.*;
import UserControls.EndUser;
import UserControls.StoreOwner;

import java.util.List;
import java.util.Scanner;

public class ApplicationContext {
    private static Inventory INVENTORY = Inventory.getInstance();
    private static Checkout CART = Checkout.getInstance();
    static int no_of_visits = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StoreOwner storeOwner = null;
        EndUser user = null;
        try {
            initProducts();
            welcomeInteraction(sc);
        } catch(Exception ex){
            System.out.println("Record the exception here : " + ex.getMessage());
        } finally {
            sc.close();
        }
    }

    private static void welcomeInteraction(Scanner sc) {
        System.out.println("Welcome " + ((no_of_visits > 1) ? "back" : " ") + " to the ecommerce application" +
                "\n" +
                "Please select number according to your role :" +
                "1. Store Owner   2. End User   3. Exit");
        int userOption = Integer.parseInt(sc.nextLine());
        if(userOption == 1){
            storeOwnerInteraction();
            no_of_visits++;
        } else if(userOption == 2){
            userInteraction();
            no_of_visits++;
        } else if(userOption == 3){
            System.out.println("Program has exited");
        }
        System.out.println("Would you like to continue : Press Y or y to say yes and press N or n to exit");
        String answer = sc.nextLine();
        if(answer.equals("Y") || answer.equals("y"))
            welcomeInteraction(sc);
    }

    static void storeOwnerInteraction(){
        StoreOwner storeOwner = new StoreOwner();
        System.out.println("Hi Store Owner");
        System.out.println("Below are the listed functions of your role " +
                "\n" +
                "1. List Products" +
                "\n" +
                "2. Create Product" +
                "\n" +
                "3. Create Bundle Deal" +
                "\n" +
                "4. Create Discount Deal" +
                "\n" +
                "5. List of deals");
        Scanner sc = new Scanner(System.in);
        int option = Integer.parseInt(sc.nextLine());
        switch(option){
            case 1:
                INVENTORY.listProducts();
                repeatStoreOwnerInteraction(sc);
                break;
            case 2:
                createProductInteraction(storeOwner, sc);
                repeatStoreOwnerInteraction(sc);
                break;
            case 3:
                createBundleDealInteraction(storeOwner, sc);
                repeatStoreOwnerInteraction(sc);
                break;
            case 4:
                createDiscountDealInteraction(storeOwner, sc);
                repeatStoreOwnerInteraction(sc);
                break;
            case 5:
                INVENTORY.listDeals();
                repeatStoreOwnerInteraction(sc);
                break;
            default:
                break;
        }
    }

    static void userInteraction(){
        EndUser endUser = new EndUser();
        System.out.println("Hi End User");
        System.out.println("Below are the listed functions of your role " +
                "\n" +
                "1. List Cart Products" +
                "\n" +
                "2. Add Products to Basket" +
                "\n" +
                "3. Amend Quantity of Basket" +
                "\n" +
                "4. Remove product from Basket" +
                "\n" +
                "5. Calculate total price after applying both bundle and discount deals");
        Scanner sc = new Scanner(System.in);
        int option = Integer.parseInt(sc.nextLine());
        switch(option){
            case 1:
                CART.printCartItems();
                repeatEndUserInteraction(sc);
                break;
            case 2:
                addProductToCartInteraction(endUser, sc);
                repeatEndUserInteraction(sc);
                break;
            case 3:
                amendQtyOfProductInCartInteraction(endUser, sc);
                repeatEndUserInteraction(sc);
                break;
            case 4:
                removeProductFromCartInteraction(endUser, sc);
                repeatEndUserInteraction(sc);
                break;
            case 5:
                endUser.calculate();
                repeatEndUserInteraction(sc);
            default:
                break;
        }
    }

    private static void removeProductFromCartInteraction(EndUser endUser, Scanner sc) {
        endUser.removeProduct(12345L);
    }

    private static void amendQtyOfProductInCartInteraction(EndUser endUser, Scanner sc) {
        endUser.changeQuantity(12345L, 3);
        endUser.changeQuantity(12346L, 2);
    }

    private static void addProductToCartInteraction(EndUser endUser, Scanner sc) {
        endUser.addItem(12345L);
        endUser.addItem(12346L);
    }

    static void initProducts(){
        List<Product> list = INVENTORY.getProducts();
        list.add(new Product(12345L, "Macbook Pro", "Apple Macbook Pro laptop with 512GB SSD and 8GB RAM", 1299.34));
        list.add(new Product(12346L, "Macbook Air", "Apple Macbook Air laptop with 256GB SSD and 8GB RAM", 899.34));
        list.add(new Product(12347L, "Lightning keyboard", "Apple Wireless Keyboard", 250.50));
        list.add(new Product(12348L, "Lightning Mouse", "Apple Wireless Mouse", 150.50));
        list.add(new Product(12349L, "Airpods", "Apple Airpods", 89.57));
    }

    static void createProductInteraction(StoreOwner storeOwner, Scanner sc){
        System.out.println("Enter product parameters - ");
        System.out.println("Product id : ");
        Long productId = Long.valueOf(sc.nextLine());
        System.out.println("Product Name : ");
        String name = sc.nextLine();
        System.out.println("Product Description : ");
        String description = sc.nextLine();
        System.out.println("Product price : ");
        Double price = Double.valueOf(sc.nextLine());
        storeOwner.createProduct(productId, name, description, price);
    }

    static void createBundleDealInteraction(StoreOwner storeOwner, Scanner sc){
        System.out.println("Bundle deal creation parameters");
        System.out.println("Enter Product id A : ");
        Long productIdA = Long.valueOf(sc.nextLine());
        System.out.println("Enter Quantity A : ");
        int quantityA = Integer.parseInt(sc.nextLine());
        System.out.println("Enter Product id B : ");
        Long productIdB = Long.valueOf(sc.nextLine());
        System.out.println("Enter Quantity B : ");
        int quantityB = Integer.parseInt(sc.nextLine());
        storeOwner.createBundleDeal(productIdA, quantityA, productIdB, quantityB);
    }

    static void createDiscountDealInteraction(StoreOwner storeOwner, Scanner sc){
        System.out.println("Discount deal creation parameters");
        System.out.println("Enter Product id A : ");
        Long productId = Long.getLong(sc.nextLine());
        System.out.println("Enter Quantity A : ");
        int quantity = Integer.parseInt(sc.nextLine());
        System.out.println("Enter percentage discount : ");
        int percentage = Integer.parseInt(sc.nextLine());
        storeOwner.createDiscountDeal(productId, quantity, percentage);
    }

    static void repeatStoreOwnerInteraction(Scanner sc){
        System.out.println("Would you like to do more of store owner operations? Press Y or y to continue. Press N or n to stop");
        String result = sc.nextLine();
        if(result.equals("y") || result.equals("Y"))
            storeOwnerInteraction();
    }

    static void repeatEndUserInteraction(Scanner sc){
        System.out.println("Would you like to do more of End User operations? Press Y or y to continue. Press N or n to stop");
        String result = sc.nextLine();
        if(result.equals("y") || result.equals("Y"))
            userInteraction();
        System.out.println("waiting for input");
    }
}
