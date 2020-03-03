package com.tranquocdai.freshmarket.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Province {
    @Id
    @GeneratedValue
    private Long id;
    private String nameProvince;

    public Province() {
    }

    public Province(Long id, String nameProvince) {
        this.id = id;
        this.nameProvince = nameProvince;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProvince() {
        return nameProvince;
    }

    public void setNameProvince(String nameProvince) {
        this.nameProvince = nameProvince;
    }
}
