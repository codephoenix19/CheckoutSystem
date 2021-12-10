# CheckoutSystem
A checkout system for an online electronics store owner.

To run the application
----------------------
mvn compile exec:java

To run test cases
-----------------
mvn test



Classes - state and behavior
-----------------------------

Listed below are the model classes created to keep track of application state:

Product
-------
Product entity has the fields:
product Id, Name, Description and Price.

Inventory
---------
To maintain the list of products and list of deals
And methods like addProduct(), removeProduct(), listOfProducts() to read and modify the state of inventory and deals.

Deals
-----
Interface which dictates that logic of apply is to be implemented by its implemented classes.
Two types of Deals exists 
1. Discount Deal
2. Bundle Deal

1. Discount Deal
----------------
Discount deal defines the product, quantity required so that given percentage of discount is to be applied on the product.
Example: Buy 2 airpods and get 25% off

2. Bundle Deal
--------------
Bundle deal defines that when a product A of quantity A is added into the cart, you get a quantityB of product B for free of cost.
Example: buy 1 laptop, get a mouse for free

Checkout
--------
Checkout is the class to maintain the list of cart items added from the Inventory and perform other operations like addProductIntoCart(), amendQuantityOfPCartItem(), amendPriceofCartItem(), removeProductFromCart()
