package org.grocery.groceryshop.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @NotNull
    Long userId;
    @NotNull
    List<OrderDetailRequest> orderDetails;
}
