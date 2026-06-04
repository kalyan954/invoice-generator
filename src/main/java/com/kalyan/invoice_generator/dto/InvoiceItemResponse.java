package com.kalyan.invoice_generator.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InvoiceItemResponse {

    private String productName;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal amount;
}