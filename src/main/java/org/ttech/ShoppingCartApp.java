package org.ttech;

import org.ttech.campaign.Campaign;
import org.ttech.cart.Cart;
import org.ttech.coupon.Coupon;
import org.ttech.campaign.AmountCampaign;
import org.ttech.campaign.RateCampaign;
import org.ttech.cart.ShoppingCart;
import org.ttech.coupon.RateCoupon;
import org.ttech.delivery.DefaultDeliveryCostCalculator;
import org.ttech.model.Category;
import org.ttech.model.Product;

import java.util.Arrays;
import java.util.List;

public class ShoppingCartApp {

    /**
     * 1. Creates categories,products,campaigns,coupon,
     * 2. Creates ShoppingCart with DeliveryCostCalculator
     * 3. Adds products
     * 4. Applies campaign discounts & coupon
     * 5. Result: Gives console output with maximum various data.
     * @param args
     */
    public static void main(String[] args) {
        Category clothing = new Category("Clothing");
        Category shoes = new Category("Shoes");
        Category food = new Category("Food");
        Category home = new Category("Home");
        Category electronic = new Category(home,"Electronic");
        Category garden = new Category(home,"Garden");
        Category mobilePhone = new Category(electronic,"Mobile Phone");

        Product redDress = new Product("Red Dress",100.0,clothing);
        Product blueDress = new Product("Blue Dress",200.0,clothing);
        Product nikeShoes = new Product("Nike Shoes",300.0,shoes);
        Product pasta = new Product("Pasta",100,food);
        Product iPhoneX = new Product("IPhone", 1000,mobilePhone);
        Product vase = new Product("Vase", 1000,garden);

        Campaign campaign1 = new RateCampaign(clothing,20.0,3);
        Campaign campaign2 = new RateCampaign(clothing,50.0,5);
        Campaign campaign3 = new AmountCampaign(shoes,5.0,2);
        Campaign campaign4 = new RateCampaign(electronic,20.0,2);
        Campaign campaign5 = new RateCampaign(food,20.0,2);

        List<Campaign> campaignList = Arrays.asList(campaign1,campaign2,campaign3,campaign4,campaign5);

        Coupon coupon = new RateCoupon(1500,10);

        Cart shoppingCart = new ShoppingCart(new DefaultDeliveryCostCalculator(2,4.0));

        shoppingCart.addProduct(redDress,4);
        shoppingCart.addProduct(blueDress,3);
        shoppingCart.addProduct(nikeShoes,2);
        shoppingCart.addProduct(iPhoneX,2);
        shoppingCart.addProduct(pasta,4);
        shoppingCart.addProduct(vase,4);

        shoppingCart.applyDiscount(campaignList);
        shoppingCart.applyCoupon(coupon);

        shoppingCart.print();
    }

}
