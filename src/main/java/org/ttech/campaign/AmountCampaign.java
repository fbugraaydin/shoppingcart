package org.ttech.campaign;

import org.ttech.model.Category;

public class AmountCampaign extends Campaign {
    private double discountAmount;

    public AmountCampaign(Category category, double discountAmount, int minQuantity) {
        super(category,minQuantity);
        if(discountAmount < 0)
            throw new IllegalArgumentException("Discount Amount mustn't be negative");
        this.discountAmount = discountAmount;
    }

    /**
     * Controls productQuantity is bigger than mininum Quantity then returns true.
     * @param productQuantity
     * @return
     */
    @Override
    public boolean isApplicable(int productQuantity) {
        return productQuantity >= this.minQuantity;
    }

    /**
     * Returns constant discountAmount as discount.
     * Takes categoryPrice as parameter but doesn't use it.
     * But it may be used on future implementations.
     * @param categoryPrice
     * @return
     */
    @Override
    public double calculateDiscount(double categoryPrice) {
        return discountAmount;
    }

}
