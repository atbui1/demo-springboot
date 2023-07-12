package com.edu.apidemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "tblProduct")
public class ProductEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    @Column(nullable = false, unique = false, length = 300)
    private String productName;
    private int yearOfBirth;
    private double price;
    private String description;
    private boolean published;
    @Transient
    private int age;
    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CategoryEntity category;

    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - yearOfBirth;
    }


    public ProductEntity() {
    }

    public ProductEntity(String productName, int yearOfBirth, double price, String description, boolean published) {
        this.productName = productName;
        this.yearOfBirth = yearOfBirth;
        this.price = price;
        this.description = description;
        this.published = published;
    }

    public ProductEntity(String productName, int yearOfBirth, double price, String description, boolean published, CategoryEntity category) {
        this.productName = productName;
        this.yearOfBirth = yearOfBirth;
        this.price = price;
        this.description = description;
        this.published = published;
        this.category = category;
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

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", published=" + published +
                ", age=" + age +
                ", category=" + category +
                '}';
    }


}