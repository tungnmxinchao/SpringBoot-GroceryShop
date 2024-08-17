package org.grocery.groceryshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column()
    int quantity;

    @Column()
    BigDecimal price;


}
