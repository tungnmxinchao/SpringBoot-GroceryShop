package org.grocery.groceryshop.dto.request;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Image is mandatory")
    String image;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    String description;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    BigDecimal price;

    @Min(value = 0, message = "Stock must be zero or positive")
    int stock;

    @NotNull(message = "Category ID is mandatory")
    Long categoryId;

    @NotNull(message = "Status is mandatory")
    @Min(value = 0, message = "Status must be either 0 or 1")
    @Max(value = 1, message = "Status must be either 0 or 1")
    int status;
}
