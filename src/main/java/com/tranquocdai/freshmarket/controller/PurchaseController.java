package com.tranquocdai.freshmarket.controller;

import com.tranquocdai.freshmarket.dto.PostDTO;
import com.tranquocdai.freshmarket.model.*;
import com.tranquocdai.freshmarket.repository.*;
import com.tranquocdai.freshmarket.response.ErrorResponse;
import com.tranquocdai.freshmarket.response.SuccessfulResponse;
import com.tranquocdai.freshmarket.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class PurchaseController {
    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    StatusPurchaseRepository statusPurchaseRepository;

    @Autowired
    BaseService baseService;

    @GetMapping("/purchases")
    public ResponseEntity getAllPurchase() {
        try {
            Collection<Purchase> purchaseList = purchaseRepository.findAll();
            return new ResponseEntity(new SuccessfulResponse(purchaseList), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/purchases/{postId}")
    public ResponseEntity readAllPurchase(@PathVariable("postId") Long id) {
        try {
            Post post = postRepository.findById(id).get();
            Collection<Purchase> purchaseList = purchaseRepository.findAllByPost(post);
            return new ResponseEntity(new SuccessfulResponse(purchaseList), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/purchases/create")
    public ResponseEntity createPost(Authentication authentication, @Valid @RequestBody AddPurchaseDTO addPurchaseDTO) {
        try {
            User user = baseService.getUser(authentication).get();
            Post post = postRepository.findById(addPurchaseDTO.getPostId()).get();
            StatusPurchase statusPurchase = statusPurchaseRepository.findById(addPurchaseDTO.getStatusPurchaseId()).get();
            Purchase purchase = new Purchase();
            purchase.setBuyer(user);
            purchase.setStatusPurchase(statusPurchase);
            purchase.setDateOfOrder(LocalDateTime.now());
            purchase.setPost(post);
            purchase.setPurchaseNumber(addPurchaseDTO.getPurchaseNumber());
            purchaseRepository.save(purchase);
            return new ResponseEntity(new SuccessfulResponse(purchase), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/purchases")
    public ResponseEntity updatePost(Authentication authentication, @Valid @RequestBody UpdatePurchaseDTO updatePurchaseDTO) {
        try {
            if (!baseService.getUser(authentication).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "username has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            Purchase purchase = purchaseRepository.findById(updatePurchaseDTO.getPurchaseId()).get();
            if (updatePurchaseDTO.getPurchaseNumber() != null)
                purchase.setPurchaseNumber(updatePurchaseDTO.getPurchaseNumber());
            if(updatePurchaseDTO.getStatusPurchaseId()!=null) {
                StatusPurchase statusPurchase=statusPurchaseRepository.findById(updatePurchaseDTO.getPurchaseId()).get();
                purchase.setStatusPurchase(statusPurchase);
            }
            purchaseRepository.save(purchase);
            return new ResponseEntity(new SuccessfulResponse(purchase), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/purchases/{purchasesID}")
    public ResponseEntity deletePurchase(@PathVariable("purchasesID") Long purchasesID) {
        try {
            if (!purchaseRepository.findById(purchasesID).isPresent()) {
                Map<String, String> errors = new HashMap<>();
                errors.put("message", "purchase has not existed");
                return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
            }
            purchaseRepository.deleteById(purchasesID);
            return new ResponseEntity(new SuccessfulResponse("delete successfully"), HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "get data not successfully");
            return new ResponseEntity(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
    }
}

class AddPurchaseDTO {
    private Double purchaseNumber;

    private Long postId;

    private Long statusPurchaseId;

    public Long getStatusPurchaseId() {
        return statusPurchaseId;
    }

    public void setStatusPurchaseId(Long statusPurchaseId) {
        this.statusPurchaseId = statusPurchaseId;
    }

    public Double getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Double purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}

class UpdatePurchaseDTO {
    private Long purchaseId;

    private Double purchaseNumber;

    private Long statusPurchaseId;

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getStatusPurchaseId() {
        return statusPurchaseId;
    }

    public void setStatusPurchaseId(Long statusPurchaseId) {
        this.statusPurchaseId = statusPurchaseId;
    }

    public Double getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Double purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

}
