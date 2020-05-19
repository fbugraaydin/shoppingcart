package org.ttech.delivery;

import org.ttech.cart.Cart;

public class DefaultDeliveryCostCalculator implements DeliveryCostCalculator {

    private final double FIXED_COST = 2.99;
    private final double costPerDelivery;
    private final double costPerProduct;

    public DefaultDeliveryCostCalculator(double costPerDelivery, double costPerProduct) {
        if(costPerDelivery < 0)
            throw new IllegalArgumentException("Cost Per Delivery mustn't be negative");
        if(costPerProduct < 0)
            throw new IllegalArgumentException("Cost Per Product mustn't be negative");
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
    }

    /**
     * Formula is (CostPerDelivery * NumberOfDeliveries) + (CostPerProduct * NumberOfProducts) + FixedCost
     * @param cart
     * @return
     */
    public double calculateFor(Cart cart){
        return (costPerDelivery * cart.numberOfDeliveries()) + (costPerProduct * cart.numberOfProducts()) + FIXED_COST;
    }

}
