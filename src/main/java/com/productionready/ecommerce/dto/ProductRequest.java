package com.productionready.ecommerce.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be 255 character or fewer")
        String name,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", message = "Price cannot be negative")
        BigDecimal price,


        @NotNull(message = "Stock quantity is required")
        @Min(value = 0, message = "stock quantity cannot be negative")
        Integer stockQuantity
) {
}
