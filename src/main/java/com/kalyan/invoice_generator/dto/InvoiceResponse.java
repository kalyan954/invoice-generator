package com.kalyan.invoice_generator.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InvoiceResponse {

    private Long id;

    private String invoiceNumber;

    private String customerName;

    private BigDecimal subtotal;

    private BigDecimal tax;

    private BigDecimal total;

    private LocalDateTime createdAt;

    private List<InvoiceItemResponse> items;
}