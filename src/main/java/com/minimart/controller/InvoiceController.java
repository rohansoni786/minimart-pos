package com.minimart.controller;

import com.minimart.model.Invoice;
import com.minimart.model.InvoiceItem;
import com.minimart.model.Product;
import com.minimart.repository.InvoiceRepository;
import com.minimart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody Map<String, Object> invoiceData) {
        try {
            System.out.println("📦 Received invoice data: " + invoiceData);

            // Validate required fields
            if (!invoiceData.containsKey("customerName") || invoiceData.get("customerName") == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Customer name is required"));
            }
            if (!invoiceData.containsKey("items") || invoiceData.get("items") == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Items are required"));
            }

            // Create invoice
            Invoice invoice = new Invoice();
            invoice.setCustomerName((String) invoiceData.get("customerName"));
            invoice.setTotalAmount(new BigDecimal(invoiceData.get("totalAmount").toString()));
            invoice.setCreatedAt(LocalDateTime.now());

            // Generate invoice number
            String invoiceNumber = "INV-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            invoice.setInvoiceNumber(invoiceNumber);

            // Process items
            List<Map<String, Object>> items = (List<Map<String, Object>>) invoiceData.get("items");
            for (Map<String, Object> itemData : items) {
                InvoiceItem item = new InvoiceItem();

                Integer productId = (Integer) itemData.get("productId");
                item.setProductId(productId);
                item.setProductName((String) itemData.get("productName"));
                item.setQuantity((Integer) itemData.get("quantity"));
                item.setUnitPrice(new BigDecimal(itemData.get("unitPrice").toString()));
                item.setSubtotal(new BigDecimal(itemData.get("subtotal").toString()));
                item.setInvoice(invoice);

                invoice.getItems().add(item);

                // Update product stock
                Product product = productRepository.findById(productId).orElse(null);
                if (product != null) {
                    product.setQuantity(product.getQuantity() - item.getQuantity());
                    productRepository.save(product);
                    System.out.println("📉 Updated stock for " + product.getName() + ": new quantity = " + product.getQuantity());
                } else {
                    System.out.println("⚠️ Product not found with ID: " + productId);
                }
            }

            Invoice savedInvoice = invoiceRepository.save(invoice);
            System.out.println("✅ Invoice saved successfully! ID: " + savedInvoice.getId());
            return ResponseEntity.ok(savedInvoice);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Integer id) {
        return invoiceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}