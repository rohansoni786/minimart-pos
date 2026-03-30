package com.minimart.repository;

import com.minimart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByBarcode(String barcode);

    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Product p WHERE p.quantity <= p.lowStockThreshold")
    List<Product> findLowStockItems();

    @Query("SELECT COUNT(p) FROM Product p")
    long getTotalProductCount();
}