package org.grocery.groceryshop.controller;

import org.grocery.groceryshop.entity.Invoices;
import org.grocery.groceryshop.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/total-revenue")
    public BigDecimal getTotalRevenue() {
        return invoiceService.getTotalRevenue();
    }

    @GetMapping("/total-revenue-by-date")
    public BigDecimal getTotalRevenueByDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return invoiceService.getTotalRevenueBetweenDates(startDate, endDate);
    }

    @GetMapping("/invoices-by-date")
    public List<Invoices> getInvoicesByDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return invoiceService.getInvoicesBetweenDates(startDate, endDate);
    }


    @GetMapping("/total-profit-by-date")
    public BigDecimal getTotalProfitByDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return invoiceService.getTotalProfitBetweenDates(startDate, endDate);
    }

    @GetMapping("/total-profit")
    public BigDecimal getTotalProfit() {
        return invoiceService.getTotalProfit();
    }
}