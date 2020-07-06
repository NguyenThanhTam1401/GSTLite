package com.gstlite.mobilestore.controllers;

import com.gstlite.mobilestore.entities.Product;
import com.gstlite.mobilestore.entities.ProductImage;
import com.gstlite.mobilestore.exceptions.ResourceNotFoundException;
import com.gstlite.mobilestore.repositories.ProductImageRepository;
import com.gstlite.mobilestore.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/image")

public class ProductImageController {

    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductRepository productRepository;
    
    
    
    @PostMapping("/upload")
    public ProductImage uploadImage(@RequestParam("myFile") MultipartFile file) throws IOException {
    	
    	
    	 System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
    	List<Product> lst = productRepository.findAll();
    	
    	long id = 0;
    	Product p = new Product();
    	for (Product item: lst) 
    	{ 
    	    if(id < item.getId()) {
    	    	p = item;
    	    	id = item.getId();
    	    }
    	}
    	
        ProductImage img = new ProductImage(file.getOriginalFilename(),file.getContentType(),file.getBytes(), id, p);

        final ProductImage savedImage = productImageRepository.save(img);

        System.out.println("Image saved");

        return savedImage;
    }

    
    
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductImage> getImageById(@PathVariable(value = "id") Long productImageId) throws ResourceNotFoundException {

        ProductImage productImage = productImageRepository.findById(productImageId).orElseThrow(()->new ResourceNotFoundException("Product Image not found on:" + productImageId));

        return ResponseEntity.ok().body(productImage);
    }
    
    @GetMapping("/list")
    public List<ProductImage> getAllProductImage() {

        return productImageRepository.findAll();
    }

}
