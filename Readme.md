## Shopping Cart
Shopping Cart on the e-commerce domain is a console application. When products added to cart, can be applied multiple campaigns 
to a category or more category.

In addition, coupons can be applied to cart. Then, the delivery cost is calculated for the cart.
As a result, it is printed all info about this operation.

## Tech / Framework
- [Maven](https://maven.apache.org/)
- [JUnit5](https://junit.org/junit5/)
- [Java 8](https://docs.oracle.com/javase/8/docs/)

## Features

- When a campaign applied to a category, is applied subcategories of the campaign category too.
- Example: If campaign is for Home category, this campaign is applicable for Electronic, MobilePhone, Garden categories:
```
Home -> Electronic -> Mobile Phone
       \-> Garden
```

## Project Structure

Abstractions and their implementations grouped in package as domain. Structure:

![Project Structure](src/main/resources/project_structure.png?raw=true)

## Coding Standards

- Variable and function naming is **camel case**.
- Functions and classes has single responsibility.
- In test scenarios, test naming is **given_when_then**.
- Applied abstraction for classes that may be various implementation (cart,campaign,coupon,delivery)
- Every function has javadoc to understand why created or what does.
- Arguments that passing to object are validated. If invalid throws IllegalArgumentException. Thus, is prevented invalid data.

## Tests
- Test scenario naming is **given_when_then**.
- There are 57 test scenarios.
- Tests System.out for print() method.
- Test scenarios grouped like:
    - Valid Data verification Scenarios
    - Object Hash Scenarios
    - NumberOfProducts Scenarios
    - NumberOfDeliveries Scenarios
    - Campaign Discount Scenarios
    - Coupon Discount Scenarios
    - Total Calculation Scenarios
    - Delivery Calculation Scenarios
    - Console Output Scenarios
- Test coverage 100%

![Test Coverage](src/main/resources/test_coverage.png?raw=true)

- Run test scenarios with maven by using command line:
```bash
mvn test
```
## Example Output

```
-> Category Name : Clothing
  --- Product Name: Blue Dress
  --- Quantity: 3
  --- Unit Price: 200.0 TL
 
  --- Product Name: Red Dress
  --- Quantity: 4
  --- Unit Price: 100.0 TL
 
  * Category Total Price : 1000.0 TL
  * Category Campaign Discount : 500.0 TL
-> Category Name : Food
  --- Product Name: Pasta
  --- Quantity: 4
  --- Unit Price: 100.0 TL
 
  * Category Total Price : 400.0 TL
  * Category Campaign Discount : 80.0 TL
-> Category Name : Shoes
  --- Product Name: Nike Shoes
  --- Quantity: 2
  --- Unit Price: 300.0 TL
 
  * Category Total Price : 600.0 TL
  * Category Campaign Discount : 5.0 TL
-> Category Name : Home > Garden
  --- Product Name: Vase
  --- Quantity: 4
  --- Unit Price: 1000.0 TL
 
  * Category Total Price : 4000.0 TL
  * Category Campaign Discount : 0.0 TL
-> Category Name : Home > Electronic > Mobile Phone
  --- Product Name: IPhone
  --- Quantity: 2
  --- Unit Price: 1000.0 TL
 
  * Category Total Price : 2000.0 TL
  * Category Campaign Discount : 400.0 TL
 
 -- -- -- -- -- -- -- -- -- -- --
 
Total Campaign Discount : 985.0 TL
Coupon Discount : 701.5 TL
 
Total Amount: 6313.5 TL
Delivery Cost: 36.99 TL
```

## Licence
Developed by © [Fuat Buğra AYDIN](https://www.linkedin.com/in/fuatbugraaydin/)