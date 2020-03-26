package com.tranquocdai.freshmarket.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class StorageServiceImpl implements StorageService {
    private Cloudinary cloudinary;

    public StorageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String store(String base64) {
        try {
            Map<String, String> result = cloudinary.uploader().upload(base64, ObjectUtils.emptyMap());
            return result.get("secure_url");

        } catch (IOException e) {
           throw new RuntimeException("Error");
        }
    }

    @Override
    public void delete(String fileUrl) {
        String[] splittedUrl = fileUrl.split("/");
        try {
            String[] splittedId=splittedUrl[splittedUrl.length-1].split("\\.");
            cloudinary.uploader().destroy(splittedId[0], ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
