package com.kalyan.invoice_generator.service;

import com.kalyan.invoice_generator.dto.CreateInvoiceRequest;
import com.kalyan.invoice_generator.dto.InvoiceResponse;

import java.util.List;

public interface InvoiceService {

    InvoiceResponse createInvoice(CreateInvoiceRequest request);

    List<InvoiceResponse> getAllInvoices();

    InvoiceResponse getInvoiceById(Long id);
}