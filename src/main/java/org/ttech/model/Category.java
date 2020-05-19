package org.ttech.model;

import java.util.Objects;
import java.util.Stack;

public class Category {

    private Category parentCategory;
    private String title;

    public Category(String title) {
        if(title==null || title.isEmpty())
            throw new IllegalArgumentException("Category title mustn't be null or empty string");
        this.title = title;
    }

    public Category(Category parentCategory, String title) {
        this.parentCategory = parentCategory;
        if(title==null || title.isEmpty())
            throw new IllegalArgumentException("Category title mustn't be null or empty string");
        this.title = title;
    }

    /**
     * If category has parent category, pushes categories to stack until category is null.
     * Returns category stack.
     * @return
     */
    private Stack<String> getParentCategories(){
        Stack<String> categoryStack = new Stack<>();
        Category currentCategory = this.parentCategory;
        while(currentCategory!=null){
            categoryStack.push(currentCategory.title);
            currentCategory = currentCategory.parentCategory;
        }
        return categoryStack;
    }

    /**
     * Controls this.category is subCategory of Category that passed as parameter.
     * @param category
     * @return
     */
    public boolean isSubCategoryOf(Category category){
        return this.getParentCategories().stream().anyMatch(c -> c.equals(category.getTitle()));
    }

    /**
     * Returns category chain string using using stack pop that returns from getParentCategories()
     * Example: Home > Electronic > Mobile Phone
     * @return
     */
    @Override
    public String toString() {
        Stack<String> categoryChain = getParentCategories();
        int categorySize = categoryChain.size();
        StringBuilder categoryChainToDisplay = new StringBuilder();
        for(int i = 0; i < categorySize; i++){
            categoryChainToDisplay.append(categoryChain.pop()).append(" > ");
        }
        return categoryChainToDisplay.append(this.title).toString();
    }

    public String getTitle() {
        return title;
    }

    /**
     * Implemented for two category object comparison.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(parentCategory, category.parentCategory) &&
                Objects.equals(title, category.title);
    }

    /**
     * Implemented for two category object comparison.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(parentCategory, title);
    }
}
