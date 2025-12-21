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

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // Cache all products for 5 minutes
    @Cacheable(value = "productsCache")
    public List<Product> getAllProducts() {
        System.out.println("Fetching products from DB...");
        return repository.findAll();
    }

    // When adding a new product, evict cache
    @CacheEvict(value = "productsCache", allEntries = true)
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    // When updating product, evict cache
    @CacheEvict(value = "productsCache", allEntries = true)
    public Product updateProduct(Product product) {
        return repository.save(product);
    }

    // Optional: cache single product by id
    @Cacheable(value = "productCache", key = "#id")
    public Product getProductById(Long id) {
        System.out.println("Fetching product " + id + " from DB...");
        return repository.findById(id).orElse(null);
    }

    /*@Autowired
    private  ProductRepository repo;

    @Autowired
    private  RedisTemplate<String, Product> redisTemplate;

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
    }*/
}
