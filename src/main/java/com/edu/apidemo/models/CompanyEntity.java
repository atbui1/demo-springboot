package com.edu.apidemo.models;

import javax.persistence.*;

@Entity
@Table(name = "tblCompany")
public class CompanyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nameCompany")
    private String name;
    @Column
    private String country;

    public CompanyEntity() {}
    public CompanyEntity(String name, String country) {
        this.name = name;
        this.country = country;
    }

    /** init database company in category*/
    public CompanyEntity(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
