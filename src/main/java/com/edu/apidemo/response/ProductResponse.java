package com.edu.apidemo.response;

import com.edu.apidemo.models.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;

public class ProductResponse {
    private long id;
    private String productName;
    private int yearOfBirth;
    private double price;
    private String description;
    private boolean published;
    private int age;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CategoryEntity category;

    public ProductResponse() {}

    public ProductResponse(long id, String productName, int yearOfBirth, double price, String description, boolean published, int age) {
        this.id = id;
        this.productName = productName;
        this.yearOfBirth = yearOfBirth;
        this.price = price;
        this.description = description;
        this.published = published;
        this.age = age;
    }

    public ProductResponse(long id, String productName, int yearOfBirth, double price, String description, boolean published, int age, CategoryEntity categoryEntity) {
//        this.getCreateBy();
//        this.getCreateDate();
//        getLastModifiedBy();
//        getLastModifiedDate();
        this.id = id;
        this.productName = productName;
        this.yearOfBirth = yearOfBirth;
        this.price = price;
        this.description = description;
        this.published = published;
        this.age = age;
        this.category = categoryEntity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public int getAge() {
//        return age;
        return Calendar.getInstance().get(Calendar.YEAR) - yearOfBirth;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", published=" + published +
                ", age=" + age +
                ", categoryEntity=" + category +
                '}';
    }
}
