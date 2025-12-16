package com.systemdesign.controller;

import com.systemdesign.model.Product;
import com.systemdesign.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getProduct(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }
}

