package org.grocery.groceryshop.repository;

import org.grocery.groceryshop.entity.Invoices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoices, Long> {
    Page<Invoices> findInvoicesByInvoiceId(Invoices id, Pageable pageable);
    @Query("SELECT SUM(i.amount) FROM Invoices i")
    BigDecimal findTotalRevenue();

    @Query("SELECT SUM(i.amount) FROM Invoices i WHERE i.invoiceDate BETWEEN :startDate AND :endDate")
    BigDecimal findTotalRevenueBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(i.profit) FROM Invoices i")
    BigDecimal findTotalProfit();

    @Query("SELECT SUM(i.profit) FROM Invoices i WHERE i.invoiceDate BETWEEN :startDate AND :endDate")
    BigDecimal findTotalProfitBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    List<Invoices> findByInvoiceDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
