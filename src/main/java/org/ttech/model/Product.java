package org.ttech.model;

import java.util.Objects;

public class Product {

    private String title;
    private double price;
    private Category category;

    public Product(String title, double price, Category category) {
        if(title==null || title.isEmpty())
            throw new IllegalArgumentException("Product Title mustn't be null or empty string");
        if(price < 0)
            throw new IllegalArgumentException("Price mustn't be negative & must be bigger than 0");
        if(category==null){
            throw new IllegalArgumentException("Product must have a category");
        }
        this.price = price;
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * Multiplies quantity and price. Thus returns totalPrice of product as quantity.
     * @param quantity
     * @return
     */
    public double calculateTotalPrice(int quantity){
        return quantity * price;
    }

    /**
     * Implemented for two Product object comparison.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Objects.equals(title, product.title) && Objects.equals(category, product.category);
    }

    /**
     * Implemented for two Product object comparison.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, price, category);
    }

}
