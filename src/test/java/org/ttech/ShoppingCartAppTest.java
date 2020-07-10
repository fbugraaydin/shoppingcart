package org.ttech;

import org.junit.jupiter.api.*;
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ShoppingCartAppTest {

    Cart shoppingCart;
    Category clothing,shoes,food,home,electronic,mobilePhone,garden;
    Product redDress,blueDress,nikeShoes,pasta, iPhone;
    Campaign campaign1,campaign2,campaign3,campaign4,campaign5;
    List<Campaign> campaignList;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void before(){
        System.setOut(new PrintStream(outContent));

        this.shoppingCart = new ShoppingCart(new DefaultDeliveryCostCalculator(2,4.0));

        this.clothing = new Category("Clothing");
        this.shoes = new Category("Shoes");
        this.food = new Category("Food");
        this.home = new Category("Home");
        this.electronic = new Category(home,"Electronic");
        this.garden = new Category(home,"Garden");
        this.mobilePhone = new Category(electronic,"Mobile Phone");

        this.redDress = new Product("Red Dress",100.0,clothing);
        this.blueDress = new Product("Blue Dress",200.0,clothing);
        this.nikeShoes = new Product("Nike Shoes",300.0,shoes);
        this.pasta = new Product("Pasta",100,food);
        this.iPhone = new Product("IPhone X", 1000,mobilePhone);

        this.campaign1 = new RateCampaign(clothing,20.0,3);
        this.campaign2 = new RateCampaign(clothing,50.0,5);
        this.campaign3 = new AmountCampaign(shoes,5.0,2);
        this.campaign4 = new RateCampaign(electronic,20.0,2);
        this.campaign5 = new RateCampaign(food,20.0,2);

        this.campaignList = Arrays.asList(campaign1,campaign2,campaign3,campaign4,campaign5);
    }

    @AfterEach
    public void after(){
        System.setOut(originalOut);
    }

    // # Valid Data verification Scenarios

    @Test
    public void given_creationOfProductWithNegativePrice_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Product("title",-20,food));
    }

    @Test
    public void given_creationOfProductWithEmptyTitle_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Product("",10.0,food));
    }

    @Test
    public void given_creationOfProductWithNullCategory_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Product("Product 1",10.0,null));
    }

    @Test
    public void given_creationOfAmountCampaignWithNegativeMinimumQuantity_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AmountCampaign(food,100.0,-10));
    }

    @Test
    public void given_creationOfAmountCampaignWithNegativeDiscountAmount_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AmountCampaign(food,-10,10));
    }

    @Test
    public void given_creationOfRateCampaignWithNegativeDiscountRate_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RateCampaign(food,-10,10));
    }

    @Test
    public void given_aProduct_when_addedToCardWithNegativeQuantity_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.shoppingCart.addProduct(pasta,-10));
    }

    @Test
    public void given_creationOfCategoryWithEmptyTitle_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Category(""));
    }

    @Test
    public void given_creationOfCategoryWithCategoryAndEmptyTitle_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Category(food,""));
    }

    @Test
    public void given_creationOfRateCouponWithNegativeMinPurchaseAmount_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RateCoupon(-10,10));
    }

    @Test
    public void given_creationOfRateCouponWithNegativeMinimumDiscountRate_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RateCoupon(100,-10));
    }

    @Test
    public void given_creationOfDefaultDeliveryCostCalculatorWithNegativeCostPerDelivery_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new DefaultDeliveryCostCalculator(-10,10));
    }

    @Test
    public void given_creationOfDefaultDeliveryCostCalculatorWithNegativeCostPerProduct_then_mustBeThrownIllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new DefaultDeliveryCostCalculator(10,-10));
    }


    // # Object Hash Scenarios

    @Test
    public void given_2ProductObjectHasSameAttributes_when_mustBeTheSame(){
        assertEquals(redDress,redDress);
    }

    @Test
    public void given_2ProductObjectHasDifferentTitleOtherSame_when_mustBeTheSame(){
        Product product1 = new Product("Product1",100.0,clothing);
        Product product2 = new Product("Product2",100.0,clothing);
        assertNotEquals(product1,product2);
    }

    @Test
    public void given_2ProductObjectHasDifferentPriceOtherSame_when_mustBeTheSame(){
        Product product1 = new Product("Product1",100.0,clothing);
        Product product2 = new Product("Product2",200.0,clothing);
        assertNotEquals(product1,product2);
    }

    @Test
    public void given_2ProductObjectHasDifferentCategoryOtherSame_when_mustBeTheSame(){
        Product product1 = new Product("Product1",100.0,clothing);
        Product product2 = new Product("Product2",100.0,shoes);
        assertNotEquals(product1,product2);
    }

    @Test
    public void given_2CategoriesObjectHasSameAttributes_when_mustBeTheSame(){
        Category category1 = new Category(food,"Category");
        Category category2 = new Category(food,"Category");
        assertEquals(category1,category2);
    }

    @Test
    public void given_2CategoriesObjectHasMissingAttributes_when_mustBeTheSame(){
        Category category1 = new Category("Category");
        Category category2 = new Category(food,"Category");
        assertNotEquals(category1,category2);
    }

    // # NumberOfProducts Scenarios

    @Test
    public void given_3DifferentProducts_when_addedToCard_then_numberOfProductsMustBe3(){
        shoppingCart.addProduct(redDress,1);
        shoppingCart.addProduct(blueDress,1);
        shoppingCart.addProduct(pasta,1);
        assertEquals(3,this.shoppingCart.numberOfProducts());
    }

    @Test
    public void given_3DifferentProductsAndQuantityOfOneOfThemIs3_when_addedToCard_then_numberOfProductsMustBe3(){
        shoppingCart.addProduct(redDress,1);
        shoppingCart.addProduct(blueDress,1);
        shoppingCart.addProduct(pasta,3);

        assertEquals(3,this.shoppingCart.numberOfProducts());
    }

    @Test
    public void given_2Same1DifferentProducts_when_addedToCard_when_calculatedNumberOfProducts_then_numberOfProductsMustBe2(){
        shoppingCart.addProduct(redDress,1);
        shoppingCart.addProduct(redDress,1);
        shoppingCart.addProduct(blueDress,1);

        assertEquals(2,this.shoppingCart.numberOfProducts());
    }

    // # NumberOfDeliveries Scenarios
    @Test
    public void given_2DifferentProductsBelongsToDifferentCategory_when_addedToCard_when_calculatedDeliveries_then_numberOfDeliveriesMustBe2(){
        shoppingCart.addProduct(redDress,1);
        shoppingCart.addProduct(pasta,1);
        assertEquals(2,this.shoppingCart.numberOfDeliveries());
    }

    @Test
    public void given_2DifferentProductsBelongsToDifferentCategoryAndQuantityIs2_when_addedToCard_when_calculatedDeliveries_then_numberOfDeliveriesMustBe2(){
        shoppingCart.addProduct(redDress,2);
        shoppingCart.addProduct(pasta,2);
        assertEquals(2,this.shoppingCart.numberOfDeliveries());
    }

    @Test
    public void given_2DifferentProductBelongsToSameCategory_when_addedToCard_when_calculatedDeliveries_then_numberOfDeliveriesMustBe1(){
        shoppingCart.addProduct(redDress,2);
        shoppingCart.addProduct(blueDress,2);
        assertEquals(1,this.shoppingCart.numberOfDeliveries());
    }

    // # Campaign Discount Scenarios

    @Test
    public void given_1ProductAndCampaignForAnotherCategory_when_appliedCampaignDiscount_then_campaignDiscountMustBe0(){
        this.shoppingCart.addProduct(iPhone,3);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign1));
        assertEquals(0,this.shoppingCart.getCampaignDiscount());
    }

    @Test
    public void given_1ProductWithQuantity3AndRateCampaign_when_appliedCampaignDiscount_then_mustBeSuccessfulCalculation(){
        Campaign campaign = new RateCampaign(food,10,2);
        this.shoppingCart.addProduct(pasta,3);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign));
        assertEquals(30,this.shoppingCart.getCampaignDiscount());
    }

    @Test
    public void given_1ProductWithQuantity3AndAmountCampaign_when_appliedCampaignDiscount_then_campaignDiscountMustBe5(){
        this.shoppingCart.addProduct(nikeShoes,3);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign3));
        assertEquals(5,this.shoppingCart.getCampaignDiscount());
    }

    @Test
    public void given_ProductWithQuantity3AndRateCampaignThatRequiresMin5Quantities_when_appliedCampaignDiscount_then_campaignDiscountMustBe0(){
        Campaign campaign = new RateCampaign(food,10,5);
        this.shoppingCart.addProduct(pasta,3);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign));
        assertEquals(0,this.shoppingCart.getCampaignDiscount());
    }

    @Test
    public void given_2DifferentProductThatBelongsToSameCategoryAndTotalQuantityIs6AndMinQuantityIs5_when_appliedCampaignDiscount_then_mustBeSuccessfulCalculation(){
        Product product1 = new Product("Product1",100.0,food);
        Product product2 = new Product("Product2",200.0,food);
        Campaign campaign = new RateCampaign(food,10,5);
        this.shoppingCart.addProduct(product1,4);
        this.shoppingCart.addProduct(product2,3);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign));
        assertEquals(100,this.shoppingCart.getCampaignDiscount());
    }

    @Test
    public void given_2DifferentProductsThatHasTheSameCategory_when_appliedCampaignDiscount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(redDress,4);
        this.shoppingCart.addProduct(blueDress,1);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign1));
        assertEquals(120,this.shoppingCart.getCampaignDiscount());
    }

    @Test
    public void given_2DifferentProductsThatHasTheSameCategoryAndAppliedMoreThan1Campaign_when_appliedCampaignDiscount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(redDress,4);
        this.shoppingCart.addProduct(blueDress,6);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign1,campaign2));
        assertEquals(800,this.shoppingCart.getCampaignDiscount());
    }

    @Test
    public void given_1ProductAndAppliedRateAndAmountCampaign_when_appliedCampaignDiscount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(redDress,5);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign1,campaign2,campaign3));
        assertEquals(250,this.shoppingCart.getCampaignDiscount());
    }

    /**
     * Category Chain : Electronic -> Mobile Phone.
     * Campaign Applies to Electronic
     */
    @Test
    public void given_1ProductThatBelongsToCategoryOfSubCategoryOfCampaign_when_appliedCampaignDiscount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(this.iPhone,3);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign4));
        assertEquals(600,this.shoppingCart.getCampaignDiscount());
    }

    /**
     * Category Chain : Home -> Electronic -> Mobile Phone
     * Campaign applies to Home
     */
    @Test
    public void given_1ProductThatBelongsToCategoryOfSubCategoryOfSubCategoryOfCampaign_when_appliedCampaignDiscount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(this.iPhone,3);
        Campaign parentCampaign = new RateCampaign(home,20,3);
        this.shoppingCart.applyDiscount(Arrays.asList(parentCampaign));
        assertEquals(600,this.shoppingCart.getCampaignDiscount());
    }

    /**
     * Category Chain : Home -> Electronic -> Mobile Phone
     *                        \-> Garden
     */
    @Test
    public void given_2DifferentProductsThatBelongsToSubCategoryOfCampaign_when_appliedCampaignDiscount_then_mustBeSuccessfulCalculation(){
        Product vase = new Product("Vase",200,this.garden);
        this.shoppingCart.addProduct(this.iPhone,3);
        this.shoppingCart.addProduct(vase,3);
        Campaign parentCampaign = new RateCampaign(home,20,3);
        this.shoppingCart.applyDiscount(Arrays.asList(parentCampaign));
        assertEquals(720,this.shoppingCart.getCampaignDiscount());
    }

    // # Coupon Discount Scenarios

    @Test
    public void given_ProductAndRateCampaignRequires100TL_when_appliedCoupon_then_mustBeSuccessfulCalculation(){
        Coupon coupon = new RateCoupon(100,10);
        this.shoppingCart.addProduct(nikeShoes,3);
        this.shoppingCart.applyCoupon(coupon);
        assertEquals(90,this.shoppingCart.getCouponDiscount());
    }

    @Test
    public void given_ProductWithAndRateCampaignRequires1000TL_when_appliedCoupon_then_couponDiscountMustBe0(){
        Coupon coupon = new RateCoupon(1000,10);
        this.shoppingCart.addProduct(nikeShoes,3);
        this.shoppingCart.applyCoupon(coupon);
        assertEquals(0,this.shoppingCart.getCouponDiscount());
    }

    // # Total Calculation Scenarios

    @Test
    public void given_1ProductAddsTwiceWithDifferentQuantity_when_calculatedTotalAmount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(this.redDress,5);
        this.shoppingCart.addProduct(this.redDress,2);
        assertEquals(700,this.shoppingCart.getTotalAmountAfterDiscounts());
    }

    @Test
    public void given_1ProductAndItsQuantityIsBiggerThanRateCampaignQuantity_when_appliedCampaign_calculatedTotalAmount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(this.redDress,5);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign1));
        assertEquals(500-(500*0.2),this.shoppingCart.getTotalAmountAfterDiscounts());
    }

    @Test
    public void given_1ProductAndItsQuantityIsSmallerThanRateCampaignQuantity_when_appliedCampaign_calculatedTotalAmount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(this.redDress,1);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign1));
        assertEquals(100,this.shoppingCart.getTotalAmountAfterDiscounts());
    }

    @Test
    public void given_2ProductsThatBelongsToSameCategoryAndSumOfQuantityBiggerThanCampaigns_when_appliedCampaign_calculatedTotalAmount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(this.redDress,2);
        this.shoppingCart.addProduct(this.blueDress,2);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign1));
        assertEquals(480,this.shoppingCart.getTotalAmountAfterDiscounts());
    }

    @Test
    public void given_2DifferentProductsThatHasTheSameCategoryAndAppliedMoreThan1Campaign_when_appliedCampaignDiscount_calculatedTotalAmount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(redDress,4);
        this.shoppingCart.addProduct(blueDress,6);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign1,campaign2));
        assertEquals(1600-800,this.shoppingCart.getTotalAmountAfterDiscounts());
    }

    /**
     * Category Chain : Electronic -> Mobile Phone.
     * Campaign Applies to Electronic
     */
    @Test
    public void given_1ProductThatBelongsToCategoryOfSubCategoryOfCampaign_when_appliedCampaignDiscount_calculatedTotalAmount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(this.iPhone,3);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign4));
        assertEquals(3000-600,this.shoppingCart.getTotalAmountAfterDiscounts());
    }

    /**
     * Category Chain : Home -> Electronic -> Mobile Phone
     * Campaign applies to Home
     */
    @Test
    public void given_1ProductThatBelongsToCategoryOfSubCategoryOfSubCategoryOfCampaign_when_appliedCampaignDiscount_calculatedTotalAmount_then_mustBeSuccessfulCalculation(){
        this.shoppingCart.addProduct(this.iPhone,3);
        Campaign parentCampaign = new RateCampaign(home,20,3);
        this.shoppingCart.applyDiscount(Arrays.asList(parentCampaign));
        assertEquals(3000-600,this.shoppingCart.getTotalAmountAfterDiscounts());
    }

    /**
     * Category Chain : Home -> Electronic -> Mobile Phone
     *                        \-> Garden
     */
    @Test
    public void given_2DifferentProductsThatBelongsToSubCategoryOfCampaign_when_appliedCampaignDiscount_calculatedTotalAmount_then_mustBeSuccessfulCalculation(){
        Product vase = new Product("Vase",200,this.garden);
        this.shoppingCart.addProduct(this.iPhone,3);
        this.shoppingCart.addProduct(vase,3);
        Campaign parentCampaign = new RateCampaign(home,20,3);
        this.shoppingCart.applyDiscount(Arrays.asList(parentCampaign));
        assertEquals(3600-720,this.shoppingCart.getTotalAmountAfterDiscounts());
    }

    @Test
    public void given_1ProductAndRateCampaignThatRequires100TL_when_appliedCoupon_when_calculatedTotalAmount_then_mustBeSuccessfulCalculation(){
        Coupon coupon = new RateCoupon(100,10);
        this.shoppingCart.addProduct(nikeShoes,3);
        this.shoppingCart.applyCoupon(coupon);
        assertEquals(900-90,this.shoppingCart.getTotalAmountAfterDiscounts());
    }

    @Test
    public void given_1ProductAndRateCouponThatRequires1000TL_when_appliedCoupon_when_calculatedTotalAmount_then_couponDiscountMustNotBeApplied(){
        Coupon coupon = new RateCoupon(1000,10);
        this.shoppingCart.addProduct(nikeShoes,3);
        this.shoppingCart.applyCoupon(coupon);
        assertEquals(900,this.shoppingCart.getTotalAmountAfterDiscounts());
    }

    // # Delivery Calculation Scenarios

    @Test
    public void given_1Product_when_calculatedDeliveryCost_then_mustBeSuccessfulCalculation(){
        Cart shoppingCart = new ShoppingCart(new DefaultDeliveryCostCalculator(2,3));
        shoppingCart.addProduct(nikeShoes,1);
        assertEquals(7.99,shoppingCart.getDeliveryCost());
    }

    @Test
    public void given_2ProductThatBelongsToDifferentCategories_when_calculatedDeliveryCost_then_mustBeSuccessfulCalculation(){
        Cart shoppingCart = new ShoppingCart(new DefaultDeliveryCostCalculator(2,3));
        shoppingCart.addProduct(nikeShoes,1);
        shoppingCart.addProduct(iPhone,1);
        assertEquals(12.99,shoppingCart.getDeliveryCost());
    }

    @Test
    public void given_2ProductsBelongsToSameCategoryAnd1ProductDifferent_when_calculatedDeliveryCost_then_mustBeSuccessfulCalculation(){
        Cart shoppingCart = new ShoppingCart(new DefaultDeliveryCostCalculator(2,3));
        shoppingCart.addProduct(iPhone,2);
        shoppingCart.addProduct(redDress,2);
        shoppingCart.addProduct(blueDress,1);
        assertEquals(15.99,shoppingCart.getDeliveryCost());
    }

    /**
     * Applies Discount And Coupon for one campaign
     * Tests Collection of product,discount,calculation and delivery functions.
     */
    @Test
    public void given_oneCategoryAndProductAndCampaignAndCouponAdded_when_appliedDiscountsAndCoupon_then_calculationFunctionsSuccess(){
        List<Campaign> campaignList = Arrays.asList(campaign1);
        Coupon coupon = new RateCoupon(80,10);
        shoppingCart.addProduct(redDress,4);
        shoppingCart.applyDiscount(campaignList);
        shoppingCart.applyCoupon(coupon);

        assertEquals(288.0,this.shoppingCart.getTotalAmountAfterDiscounts());
        assertEquals(80,this.shoppingCart.getCampaignDiscount());
        assertEquals(32,this.shoppingCart.getCouponDiscount());
        assertEquals(8.99,this.shoppingCart.getDeliveryCost());
        assertEquals(1,this.shoppingCart.numberOfProducts());
    }

    // # Console Output Scenarios
    // info : adds replacing of \r to nothing on scenarios for windows os

    @Test
    public void given_1ProductAndCampaign_when_appliedDiscount_then_mustBeSuccessfulConsoleOutput() {
        Product product = new Product("Product",100.0,food);
        Campaign campaign = new RateCampaign(food,10,2);
        this.shoppingCart.addProduct(product,3);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign));
        this.shoppingCart.print();
        assertEquals("-> Category Name : Food\n" +
                "  --- Product Name: Product\n" +
                "  --- Quantity: 3\n" +
                "  --- Unit Price: 100.0 TL\n" +
                " \n" +
                "  * Category Total Price : 300.0 TL\n" +
                "  * Category Campaign Discount : 30.0 TL\n" +
                " \n" +
                " -- -- -- -- -- -- -- -- -- -- --\n" +
                " \n" +
                "Total Campaign Discount : 30.0 TL\n" +
                "Coupon Discount : 0.0 TL\n" +
                " \n" +
                "Total Amount: 270.0 TL\n" +
                "Delivery Cost: 8.99 TL",outContent.toString().replace("\r",""));
    }

    @Test
    public void given_2DifferentProductsThatHasTheSameCategory_when_appliedCampaignDiscount_then_mustBeSuccessfulConsoleOutput(){
        this.shoppingCart.addProduct(redDress,4);
        this.shoppingCart.addProduct(blueDress,1);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign1));
        this.shoppingCart.print();
        assertEquals("-> Category Name : Clothing\n" +
                "  --- Product Name: Blue Dress\n" +
                "  --- Quantity: 1\n" +
                "  --- Unit Price: 200.0 TL\n" +
                " \n" +
                "  --- Product Name: Red Dress\n" +
                "  --- Quantity: 4\n" +
                "  --- Unit Price: 100.0 TL\n" +
                " \n" +
                "  * Category Total Price : 600.0 TL\n" +
                "  * Category Campaign Discount : 120.0 TL\n" +
                " \n" +
                " -- -- -- -- -- -- -- -- -- -- --\n" +
                " \n" +
                "Total Campaign Discount : 120.0 TL\n" +
                "Coupon Discount : 0.0 TL\n" +
                " \n" +
                "Total Amount: 480.0 TL\n" +
                "Delivery Cost: 12.99 TL",outContent.toString().replace("\r",""));
    }

    /**
     * Category Chain : Home -> Electronic -> Mobile Phone
     *                        \-> Garden
     */
    @Test
    public void given_2DifferentProductsThatBelongsToSubCategoryOfCampaign_when_appliedCampaignDiscount_then_mustBeSuccessfulConsoleOutput(){
        Product vase = new Product("Vase",200,this.garden);
        this.shoppingCart.addProduct(this.iPhone,3);
        this.shoppingCart.addProduct(vase,3);
        Campaign parentCampaign = new RateCampaign(home,20,3);
        this.shoppingCart.applyDiscount(Arrays.asList(parentCampaign));
        this.shoppingCart.print();
        assertEquals("-> Category Name : Home > Garden\n" +
                "  --- Product Name: Vase\n" +
                "  --- Quantity: 3\n" +
                "  --- Unit Price: 200.0 TL\n" +
                " \n" +
                "  * Category Total Price : 600.0 TL\n" +
                "  * Category Campaign Discount : 120.0 TL\n" +
                "-> Category Name : Home > Electronic > Mobile Phone\n" +
                "  --- Product Name: IPhone X\n" +
                "  --- Quantity: 3\n" +
                "  --- Unit Price: 1000.0 TL\n" +
                " \n" +
                "  * Category Total Price : 3000.0 TL\n" +
                "  * Category Campaign Discount : 600.0 TL\n" +
                " \n" +
                " -- -- -- -- -- -- -- -- -- -- --\n" +
                " \n" +
                "Total Campaign Discount : 720.0 TL\n" +
                "Coupon Discount : 0.0 TL\n" +
                " \n" +
                "Total Amount: 2880.0 TL\n" +
                "Delivery Cost: 14.99 TL",outContent.toString().replace("\r",""));
    }

    /**
     * 2 Products belongs to same category
     * 1 Product belongs to another category
     * Apply 2 campaign discount for both category
     * Apply Coupon discount
     * Prints maximum case of print:
     *   - Product Name,Quantity,Unit Price for each product
     *   - Category Total Price,Category Campaign Discount for each Category
     *   - Total Campaign Discount, Coupon Discount
     *   - Total Amount,Delivery Cost
     */
    @Test
    public void given_2ProductsBelongsToSameCategoryAndAnotherProduct_when_appliedCampaignDiscountAndCoupon_when_mustBeSuccessfulConsoleOutput(){
        this.shoppingCart.addProduct(blueDress,3);
        this.shoppingCart.addProduct(redDress,4);
        this.shoppingCart.addProduct(pasta,3);
        this.shoppingCart.applyDiscount(Arrays.asList(campaign1,campaign5));
        Coupon coupon = new RateCoupon(100,10);
        this.shoppingCart.applyCoupon(coupon);
        this.shoppingCart.print();

        assertEquals("-> Category Name : Clothing\n" +
                "  --- Product Name: Blue Dress\n" +
                "  --- Quantity: 3\n" +
                "  --- Unit Price: 200.0 TL\n" +
                " \n" +
                "  --- Product Name: Red Dress\n" +
                "  --- Quantity: 4\n" +
                "  --- Unit Price: 100.0 TL\n" +
                " \n" +
                "  * Category Total Price : 1000.0 TL\n" +
                "  * Category Campaign Discount : 200.0 TL\n" +
                "-> Category Name : Food\n" +
                "  --- Product Name: Pasta\n" +
                "  --- Quantity: 3\n" +
                "  --- Unit Price: 100.0 TL\n" +
                " \n" +
                "  * Category Total Price : 300.0 TL\n" +
                "  * Category Campaign Discount : 60.0 TL\n" +
                " \n" +
                " -- -- -- -- -- -- -- -- -- -- --\n" +
                " \n" +
                "Total Campaign Discount : 260.0 TL\n" +
                "Coupon Discount : 104.0 TL\n" +
                " \n" +
                "Total Amount: 936.0 TL\n" +
                "Delivery Cost: 18.990000000000002 TL",outContent.toString().replace("\r",""));
    }

    @Test
    public void given_emptyStringArgs_when_callMain_then_mustBeSuccessfulConsoleOutput(){
        ShoppingCartApp.main(new String[]{});
        assertEquals("-> Category Name : Clothing\n" +
                "  --- Product Name: Blue Dress\n" +
                "  --- Quantity: 3\n" +
                "  --- Unit Price: 200.0 TL\n" +
                " \n" +
                "  --- Product Name: Red Dress\n" +
                "  --- Quantity: 4\n" +
                "  --- Unit Price: 100.0 TL\n" +
                " \n" +
                "  * Category Total Price : 1000.0 TL\n" +
                "  * Category Campaign Discount : 500.0 TL\n" +
                "-> Category Name : Food\n" +
                "  --- Product Name: Pasta\n" +
                "  --- Quantity: 4\n" +
                "  --- Unit Price: 100.0 TL\n" +
                " \n" +
                "  * Category Total Price : 400.0 TL\n" +
                "  * Category Campaign Discount : 80.0 TL\n" +
                "-> Category Name : Shoes\n" +
                "  --- Product Name: Nike Shoes\n" +
                "  --- Quantity: 2\n" +
                "  --- Unit Price: 300.0 TL\n" +
                " \n" +
                "  * Category Total Price : 600.0 TL\n" +
                "  * Category Campaign Discount : 5.0 TL\n" +
                "-> Category Name : Home > Garden\n" +
                "  --- Product Name: Vase\n" +
                "  --- Quantity: 4\n" +
                "  --- Unit Price: 1000.0 TL\n" +
                " \n" +
                "  * Category Total Price : 4000.0 TL\n" +
                "  * Category Campaign Discount : 0.0 TL\n" +
                "-> Category Name : Home > Electronic > Mobile Phone\n" +
                "  --- Product Name: IPhone\n" +
                "  --- Quantity: 2\n" +
                "  --- Unit Price: 1000.0 TL\n" +
                " \n" +
                "  * Category Total Price : 2000.0 TL\n" +
                "  * Category Campaign Discount : 400.0 TL\n" +
                " \n" +
                " -- -- -- -- -- -- -- -- -- -- --\n" +
                " \n" +
                "Total Campaign Discount : 985.0 TL\n" +
                "Coupon Discount : 701.5 TL\n" +
                " \n" +
                "Total Amount: 6313.5 TL\n" +
                "Delivery Cost: 36.99 TL",outContent.toString().replace("\r",""));
    }

}
