package com.systemdesign.service;

import com.systemdesign.model.Product;
import com.systemdesign.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ProductService {
    private final RedisTemplate<String, Product> redisTemplate;
    private final ProductRepository repo;

    public ProductService(RedisTemplate<String, Product> redisTemplate,
                          ProductRepository repo) {
        this.redisTemplate = redisTemplate;
        this.repo = repo;
    }

    public Product getProduct(Long id) {
        String key = "product:" + id;

        Product cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return cached;
        }

        Product product = repo.findById(id)
                .orElseThrow();

        redisTemplate.opsForValue().set(key, product, Duration.ofMinutes(10));
        return product;
    }

    public Product create(Product product) {
        // Denormalized field computation
        product.setDisplayText(
                product.getName() + " | " + product.getCategory()
        );

        Product saved = repo.save(product);

        // Cache write-through (optional but clean)
        redisTemplate.opsForValue()
                .set("product:" + saved.getId(), saved, Duration.ofMinutes(10));

        return saved;
    }
}
