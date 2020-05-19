package org.ttech.cart;

import org.ttech.campaign.Campaign;
import org.ttech.coupon.Coupon;
import org.ttech.delivery.DeliveryCostCalculator;
import org.ttech.model.Category;
import org.ttech.model.Product;
import java.util.stream.Collectors;
import java.util.*;

public class ShoppingCart implements Cart {

    private final DeliveryCostCalculator deliveryCostCalculator;
    private final Map<Product, Integer> itemList;
    private final List<Campaign> campaignList;
    private Coupon coupon;

    public ShoppingCart(DeliveryCostCalculator deliveryCostCalculator) {
        this.deliveryCostCalculator = deliveryCostCalculator;
        itemList =  new HashMap<>();
        campaignList = new ArrayList<>();
    }

    /**
     * Adds Product with their quantity.
     * Keeps items in a hashMap(key=Product,value=Quantity)
     * Controls quantity is valid if is not throws IllegalArgumentException
     * If it tries to add same product to cart, sum up quantities as product.
     * @param product
     * @param quantity
     */
    @Override
    public void addProduct(Product product, int quantity) {
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity mustn't be negative & must be bigger than 0");
        }
        if (null != itemList.get(product)) {
            itemList.put(product, itemList.get(product) + quantity);
        } else {
            itemList.put(product, quantity);
        }
    }

    /**
     * Finds product list for each as campaign category and calculates total quantity as productList.
     * If campaign is applicable for this totalQuantity adds campaign to Cart.
     * @param campaignList
     */
    @Override
    public void applyDiscount(List<Campaign> campaignList) {
        campaignList.stream().forEach(campaign -> {
            Map<Product, Integer> productList = this.getProductListBelongsToCategory(campaign.getCategory());
            int totalQuantity = calculateTotalQuantityInProductList(productList);
            if (campaign.isApplicable(totalQuantity)) {
                this.campaignList.add(campaign);
            }
        });
    }

    /**
     * Applies coupon by calculating total amount, then assign.
     * @param coupon
     */
    @Override
    public void applyCoupon(Coupon coupon) {
        if (coupon.isApplicable(getTotalAmountAppliedCampaignDiscounts())) {
            this.coupon = coupon;
        }
    }

    /**
     * Calculates coupon discount by using total amount that applied campaign discounts
     * @return
     */
    @Override
    public double getCouponDiscount() {
        return this.coupon != null ? this.coupon.calculateDiscount(getTotalAmountAppliedCampaignDiscounts()) : 0;
    }

    /**
     * Calculates all campaigns by grouping as category.
     * @return
     */
    @Override
    public double getCampaignDiscount() {
        return categoryList().stream().mapToDouble(this::calculateMaximumCampaignDiscountByCategory).sum();
    }

    /**
     * Returns number of distinct categories in the cart
     * @return
     */
    @Override
    public int numberOfDeliveries() {
        return categoryList().size();
    }

    /**
     * Returns different products in the cart. It is not the quantity of products
     * @return
     */
    @Override
    public double numberOfProducts() {
        return itemList.size();
    }

    /**
     * Returns total amount that applied campaign discounts and coupon discounts.
     * @return
     */
    @Override
    public double getTotalAmountAfterDiscounts() {
        return getTotalAmountAppliedCampaignDiscounts() - this.getCouponDiscount();
    }

    /**
     * Calculates delivery by cart info.
     * @return
     */
    @Override
    public double getDeliveryCost() {
        return this.deliveryCostCalculator.calculateFor(this);
    }

    /**
     * Returns total amount that applied just campaign discounts.
     * @return
     */
    private double getTotalAmountAppliedCampaignDiscounts(){
        return this.calculateTotalAmount() - this.getCampaignDiscount();
    }

    /**
     * Calculates raw total amount without any discount.
     * @return
     */
    private double calculateTotalAmount() {
        return itemList.entrySet().stream()
                .mapToDouble(item -> item.getKey().calculateTotalPrice(item.getValue())).sum();
    }

    /**
     * Takes total category price and campaign list by category.
     * Finds maximum discount iterating in campaign list and returns calculated discount.
     * @param category
     * @return
     */
    private double calculateMaximumCampaignDiscountByCategory(Category category) {
        double categoryPrice = this.calculateAmountForProductListByCategory(category);
        List<Campaign> campaignList = this.getCampaignListByCategory(category);

        if (!campaignList.isEmpty()) {
            double maxDiscountAmount = -9999;
            for (Campaign campaign : campaignList) {
                double discount = campaign.calculateDiscount(categoryPrice);
                if (maxDiscountAmount < discount) {
                    maxDiscountAmount = discount;
                }
            }
            return maxDiscountAmount;
        }
        return 0;
    }

    /**
     * Calculate amount for specific category.
     * @param category
     * @return
     */
    private double calculateAmountForProductListByCategory(Category category) {
        Map<Product, Integer> productList = this.getProductListBelongsToCategory(category);
        return calculateTotalAmountInProductList(productList);
    }

    /**
     * Returns applied campaign list to a category. Also looks that campaign category includes category.
     * @param category
     * @return
     */
    private List<Campaign> getCampaignListByCategory(Category category) {
        return campaignList.stream().filter(campaign ->
                campaign.getCategory() == category || category.isSubCategoryOf(campaign.getCategory()))
                .collect(Collectors.toList());
    }

    /**
     * Returns product list belongs to category. Also looks product category is subCategory of category.
     * Product list under the category.
     * @param category
     * @return
     */
    private Map<Product, Integer> getProductListBelongsToCategory(Category category) {
        return itemList.entrySet().stream().filter(product ->
                product.getKey().getCategory() == category ||
                        product.getKey().getCategory().isSubCategoryOf(category)).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Calculate tatal amount using quantity & price for given product list.
     * @param productList
     * @return
     */
    private double calculateTotalAmountInProductList(Map<Product, Integer> productList) {
        return productList.entrySet().stream().mapToDouble(product ->
                product.getKey().calculateTotalPrice(product.getValue())).sum();
    }

    /**
     * Calculates total quantity amount by summing quantity of each product.
     * @param productList
     * @return
     */
    private int calculateTotalQuantityInProductList(Map<Product, Integer> productList) {
        return productList.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Collect categories in Set collection by iterating in itemList.
     * @return
     */
    private Set<Category> categoryList() {
        Set<Category> categoryList = new LinkedHashSet<>();
        itemList.keySet().stream().forEach(item -> categoryList.add(item.getCategory()));
        return categoryList;
    }

    /**
     * Returns product list by controlling product category equals to category
     * @param category
     * @return
     */
    private Map<Product, Integer> getProductListByCategory(Category category) {
        return itemList.entrySet().stream().filter(product ->
                product.getKey().getCategory() == category).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Prints:
     *  - Product Name, Quantity,Unit Price for each product
     *  - Category Total Price,Category Campaign Discount for each Category
     *  - Total Campaign Discount, Coupon Discount
     *  - Total Amount,Delivery Cost
     */
    @Override
    public void print() {
        for (Category category : this.categoryList()) {
            System.out.println("-> Category Name : " + category.toString());
            Map<Product, Integer> productList = this.getProductListByCategory(category);

            productList.entrySet().forEach(
                    product -> {
                        System.out.println("  --- Product Name: " + product.getKey().getTitle());
                        System.out.println("  --- Quantity: " + product.getValue());
                        System.out.println("  --- Unit Price: " + product.getKey().getPrice() + " TL");
                        System.out.println(" ");
                    });

            double totalPrice = this.calculateTotalAmountInProductList(productList);

            System.out.println("  * Category Total Price : " + totalPrice + " TL");
            System.out.println("  * Category Campaign Discount : " + calculateMaximumCampaignDiscountByCategory(category) + " TL");
        }

        System.out.println(" ");
        System.out.println(" -- -- -- -- -- -- -- -- -- -- --");
        System.out.println(" ");
        System.out.println("Total Campaign Discount : " + this.getCampaignDiscount() + " TL");
        System.out.println("Coupon Discount : " + this.getCouponDiscount() + " TL");
        System.out.println(" ");
        System.out.println("Total Amount: " + this.getTotalAmountAfterDiscounts() + " TL");
        System.out.print("Delivery Cost: " + this.getDeliveryCost() + " TL");
    }

}