package com.tranquocdai.freshmarket.model;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postName;

    private String description;

    private Double unitPrice;

    //private Long districtId;

    private String address;

   // private Double weightOfItem;

    private LocalDateTime dateOfPost;

    @OneToOne(targetEntity = User.class)
    private User user;

    @OneToOne(targetEntity = CalculationUnit.class)
    private CalculationUnit calculationUnit;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER,targetEntity = ImagePost.class)
    private List<ImagePost> imagePosts;
    /*@OneToOne(targetEntity = TypePost.class)
    private TypePost typePost;*/

    @OneToOne(targetEntity = Category.class)
    private Category category;

    @OneToOne(targetEntity = Province.class)
    private Province province;

    public List<ImagePost> getImagePosts() {
        return imagePosts;
    }

    public void setImagePosts(List<ImagePost> imagePosts) {
        this.imagePosts = imagePosts;
    }

//    public Double getWeightOfItem() {
//        return weightOfItem;
//    }
//
//    public void setWeightOfItem(Double weightOfItem) {
//        this.weightOfItem = weightOfItem;
//    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

//    public ImagePost getImagePost() {
//        return imagePost;
//    }
//
//    public void setImagePost(ImagePost imagePost) {
//        this.imagePost = imagePost;
//    }

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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

//    public Long getDistrictId() {
//        return districtId;
//    }
//
//    public void setDistrictId(Long districtId) {
//        this.districtId = districtId;
//    }

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

   /* public TypePost getTypePost() {
        return typePost;
    }

    public void setTypePost(TypePost typePost) {
        this.typePost = typePost;
    }*/

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
