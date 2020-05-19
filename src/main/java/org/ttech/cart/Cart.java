package org.ttech.cart;

import org.ttech.coupon.Coupon;
import org.ttech.campaign.Campaign;
import org.ttech.model.Product;

import java.util.List;

public interface Cart {

    void addProduct(Product product,int quantity);

    void applyDiscount(List<Campaign> campaignList);

    void applyCoupon(Coupon coupon);

    int numberOfDeliveries();

    double numberOfProducts();

    double getTotalAmountAfterDiscounts();

    double getCouponDiscount();

    double getCampaignDiscount();

    double getDeliveryCost();

    void print();
}