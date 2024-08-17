package org.grocery.groceryshop.service;

import org.grocery.groceryshop.entity.Invoices;
import org.grocery.groceryshop.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public BigDecimal getTotalRevenue() {
        return invoiceRepository.findTotalRevenue();
    }

    public BigDecimal getTotalRevenueBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return invoiceRepository.findTotalRevenueBetweenDates(startDate, endDate);
    }

    public BigDecimal getTotalProfit(){
        return invoiceRepository.findTotalProfit();
    }

    public BigDecimal getTotalProfitBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return invoiceRepository.findTotalProfitBetweenDates(startDate, endDate);
    }

    public List<Invoices> getInvoicesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return invoiceRepository.findByInvoiceDateBetween(startDate, endDate);
    }
}
