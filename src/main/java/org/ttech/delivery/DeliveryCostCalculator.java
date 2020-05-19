package org.ttech.delivery;

import org.ttech.cart.Cart;

public interface DeliveryCostCalculator {

    double calculateFor(Cart cart);

}