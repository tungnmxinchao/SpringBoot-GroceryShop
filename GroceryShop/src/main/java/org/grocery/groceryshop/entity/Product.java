package org.grocery.groceryshop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String image;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    BigDecimal price;

    @Column(nullable = false)
    int stock;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;


    @Column(nullable = false)
    int status;


}
