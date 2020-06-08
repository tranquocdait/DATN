package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.model.TransportationType;
import com.tranquocdai.freshmarket.repository.TransportationTypeRepository;
import com.tranquocdai.freshmarket.response.ErrorResponse;
import com.tranquocdai.freshmarket.response.SuccessfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TransportationTypeController {
    @Autowired
    TransportationTypeRepository transportationTypeRepository;
    @GetMapping("/transportationTypes")
    public ResponseEntity getProvince() {
        try {
            List<TransportationType> statusPurchaseList = transportationTypeRepository.findAll();
            return new ResponseEntity(new SuccessfulResponse(statusPurchaseList), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
}
