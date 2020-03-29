package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.model.Category;
import com.tranquocdai.freshmarket.model.Province;
import com.tranquocdai.freshmarket.repository.CategoryRepository;
import com.tranquocdai.freshmarket.repository.ProvinceRepository;
import com.tranquocdai.freshmarket.response.ErrorResponse;
import com.tranquocdai.freshmarket.response.SuccessfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @GetMapping("/provinces")
    public ResponseEntity getProvince() {
        try {
            List<Category> categoryList = categoryRepository.findAll();
            return new ResponseEntity(new SuccessfulResponse(categoryList), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
}
