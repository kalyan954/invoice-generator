package com.kalyan.invoice_generator.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateInvoiceRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Valid
    @NotEmpty(message = "At least one product is required")
    private List<InvoiceItemRequest> items;
}