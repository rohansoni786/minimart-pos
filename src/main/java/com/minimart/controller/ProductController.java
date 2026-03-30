package com.minimart.controller;

import com.minimart.model.Product;
import com.minimart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<Product> getProductByBarcode(@PathVariable String barcode) {
        return productRepository.findByBarcode(barcode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String term) {
        if (term == null || term.trim().isEmpty()) {
            return List.of();
        }
        return productRepository.findByNameContainingIgnoreCase(term);
    }

    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockItems();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        return ResponseEntity.ok(productRepository.save(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}