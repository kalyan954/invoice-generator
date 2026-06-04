package com.kalyan.invoice_generator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemRequest {

    @NotBlank(message = "Product name is required")
    private String productName;

    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;
}