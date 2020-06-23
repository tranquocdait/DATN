package com.tranquocdai.freshmarket.model;

import javax.persistence.*;

@Entity
@Table(name = "type_posts")
public class TypePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typePostName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypePostName() {
        return typePostName;
    }

    public void setTypePostName(String typePostName) {
        this.typePostName = typePostName;
    }
}
