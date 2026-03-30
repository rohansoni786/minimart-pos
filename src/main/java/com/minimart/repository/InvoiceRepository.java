package com.minimart.repository;

import com.minimart.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COALESCE(SUM(i.totalAmount), 0) FROM Invoice i WHERE FUNCTION('DATE', i.createdAt) = CURRENT_DATE")
    BigDecimal getTodayRevenue();

    @Query("SELECT COALESCE(SUM(i.totalAmount), 0) FROM Invoice i WHERE i.createdAt >= :startDate")
    BigDecimal getRevenueSince(LocalDateTime startDate);

    @Query("SELECT COALESCE(SUM(i.totalAmount), 0) FROM Invoice i")
    BigDecimal getTotalRevenue();

    @Query("SELECT COUNT(i) FROM Invoice i WHERE FUNCTION('DATE', i.createdAt) = CURRENT_DATE")
    long getTodayInvoiceCount();
}