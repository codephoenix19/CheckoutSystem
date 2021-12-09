package DiscountingStrategies;

import Custom.Tuple3;

import java.util.Map;

public interface Deal {
    /*
    Here logic of apply is to be implemented by custom classes that takes in the cart and apply the respective discount over total.
     */
    void apply();
}
