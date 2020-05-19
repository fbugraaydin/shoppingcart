package org.ttech.coupon;

public class RateCoupon extends Coupon {

    private double minPurchaseAmount;
    private double discountRate;

    public RateCoupon(double minPurchaseAmount, double discountRate) {
        if(minPurchaseAmount < 0)
            throw new IllegalArgumentException("Coupon Minimum Purchase amount mustn't be negative");
        if(discountRate < 0)
            throw new IllegalArgumentException("Coupon Discount Rate mustn't be negative");
        this.minPurchaseAmount = minPurchaseAmount;
        this.discountRate = discountRate;
    }

    /**
     * Controls totalPrice is bigger than minimum Purchase Amount then returns true.
     * @param totalPrice
     * @return
     */
    @Override
    public boolean isApplicable(double totalPrice) {
        return totalPrice >= this.minPurchaseAmount;
    }

    /**
     * Returns calculation result of using discountRate & totalPrice as Rate.
     * @param totalPrice
     * @return
     */
    @Override
    public double calculateDiscount(double totalPrice) {
        return totalPrice * (discountRate / 100);
    }
}
