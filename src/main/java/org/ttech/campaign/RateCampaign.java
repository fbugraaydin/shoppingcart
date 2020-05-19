package org.ttech.campaign;

import org.ttech.model.Category;

public class RateCampaign extends Campaign {
    private double discountRate;

    public RateCampaign(Category category, double discountRate, int minQuantity) {
        super(category,minQuantity);
        if(discountRate < 0)
            throw new IllegalArgumentException("Discount Rate mustn't be negative & must be bigger than 0");
        this.discountRate = discountRate;
    }

    /**
     * campaign can be applied by productQuantity
     * @param productQuantity
     * @return
     */
    @Override
    public boolean isApplicable(int productQuantity) {
        return productQuantity >= this.minQuantity;
    }

    /**
     * Returns calculation result of using discountRate & categoryPrice as Rate.
     * @param categoryPrice
     * @return
     */
    @Override
    public double calculateDiscount(double categoryPrice) {
        return categoryPrice * (discountRate / 100);
    }
}
