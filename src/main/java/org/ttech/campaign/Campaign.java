package org.ttech.campaign;

import org.ttech.model.Category;

/**
 * This abstract class is implemented for various Campaign implementations.
 * Contains mandatory common properties.
 * Contains abstract methods that must be implemented: isApplicable,calculateDiscount.
 */
public abstract class Campaign {
    protected Category category;
    protected int minQuantity;

    public Campaign(Category category, int minQuantity) {
        this.category = category;
        if(minQuantity < 0)
            throw new IllegalArgumentException("Minimum Quantity mustn't be negative");
        this.minQuantity = minQuantity;
    }

    /**
     * campaign can be applied by productQuantity
     * @param productQuantity
     * @return
     */
    public abstract boolean isApplicable(int productQuantity);

    /**
     * calculate Discount by categoryPrice.
     * @param categoryPrice
     * @return
     */
    public abstract double calculateDiscount(double categoryPrice);

    public Category getCategory() {
        return category;
    }

}