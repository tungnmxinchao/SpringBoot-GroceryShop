package org.grocery.groceryshop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    Users user;

    @CreationTimestamp
    @Column(nullable = false)
    Timestamp orderDate = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    int status;

    @Column(nullable = false)
    BigDecimal total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails;
}
