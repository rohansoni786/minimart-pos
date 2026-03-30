package com.minimart.service;

import com.minimart.repository.ProductRepository;
import com.minimart.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Map<String, Object> getDashboardReport() {
        Map<String, Object> report = new HashMap<>();

        report.put("totalProducts", productRepository.getTotalProductCount());
        report.put("totalSales", invoiceRepository.getTotalRevenue());
        report.put("lowStockItems", (long) productRepository.findLowStockItems().size());
        report.put("todayRevenue", invoiceRepository.getTodayRevenue());
        report.put("lastWeekRevenue", invoiceRepository.getRevenueSince(LocalDateTime.now().minusWeeks(1)));
        report.put("lastMonthRevenue", invoiceRepository.getRevenueSince(LocalDateTime.now().minusMonths(1)));

        return report;
    }
}