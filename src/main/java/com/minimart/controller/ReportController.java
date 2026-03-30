package com.minimart.controller;

import com.minimart.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardReport() {
        return reportService.getDashboardReport();
    }
}