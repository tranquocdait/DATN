package com.tranquocdai.freshmarket.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postName;

    private String description;

    private String unitPrice;

    private String districtId;

    private String address;

    private LocalDateTime dateOfPost;

    @OneToOne(targetEntity = User.class)
    private User user;

    @OneToOne(targetEntity = CalculationUnit.class)
    private CalculationUnit calculationUnit;

    @OneToOne(targetEntity = TypePost.class)
    private TypePost typePost;

    @OneToOne(targetEntity = Category.class)
    private Category category;

    @OneToOne(targetEntity = ImagePost.class)
    private ImagePost imagePost;

    @OneToOne(targetEntity = Province.class)
    private Province province;

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public ImagePost getImagePost() {
        return imagePost;
    }

    public void setImagePost(ImagePost imagePost) {
        this.imagePost = imagePost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CalculationUnit getCalculationUnit() {
        return calculationUnit;
    }

    public void setCalculationUnit(CalculationUnit calculationUnit) {
        this.calculationUnit = calculationUnit;
    }

    public LocalDateTime getDateOfPost() {
        return dateOfPost;
    }

    public void setDateOfPost(LocalDateTime dateOfPost) {
        this.dateOfPost = dateOfPost;
    }

    public TypePost getTypePost() {
        return typePost;
    }

    public void setTypePost(TypePost typePost) {
        this.typePost = typePost;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
