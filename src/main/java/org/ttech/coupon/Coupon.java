package org.ttech.coupon;

public abstract class Coupon {

    public abstract boolean isApplicable(double totalPrice);

    public abstract double calculateDiscount(double totalPrice);

}