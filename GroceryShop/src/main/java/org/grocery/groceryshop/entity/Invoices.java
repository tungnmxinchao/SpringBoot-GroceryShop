package org.grocery.groceryshop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long invoiceId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Orders order;

    @Column(nullable = false)
    Timestamp invoiceDate = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    BigDecimal amount;

    @Column(nullable = false)
    BigDecimal profit;
}
