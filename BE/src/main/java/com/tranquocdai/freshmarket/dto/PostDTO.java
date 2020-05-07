package com.tranquocdai.freshmarket.dto;

public class PostDTO {

    private Long id;

    private String postName;

    private String description;

    private Double unitPrice;

    private Long districtId;

    private String address;

    private Long calculationUnitID;

    //private Long typePostID;

    private Long categoryID;

    private Long provinceID;

    private Double weightOfItem;

    private String imageBase64;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeightOfItem() {
        return weightOfItem;
    }

    public void setWeightOfItem(Double weightOfItem) {
        this.weightOfItem = weightOfItem;
    }

    public Long getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(Long provinceID) {
        this.provinceID = provinceID;
    }

    public Long getCalculationUnitID() {
        return calculationUnitID;
    }

    public void setCalculationUnitID(Long calculationUnitID) {
        this.calculationUnitID = calculationUnitID;
    }

   /* public Long getTypePostID() {
        return typePostID;
    }

    public void setTypePostID(Long typePostID) {
        this.typePostID = typePostID;
    }*/

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
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

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
