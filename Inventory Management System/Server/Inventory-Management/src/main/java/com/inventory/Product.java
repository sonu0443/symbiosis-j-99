package com.inventory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", sku=" + sku + ", price="
				+ price + ", stockQuantity=" + stockQuantity + ", category=" + category + "]";
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID will be auto-generated
    private Long id;
    private String name;
    private String description;
    private String sku;
    private double price;
    private int stockQuantity;
    private String category;

    // Default constructor
    public Product() {}

    // Parameterized constructor
    public Product(Long id, String name, String description, String sku, double price, int stockQuantity, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

