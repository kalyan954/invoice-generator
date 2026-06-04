package com.kalyan.invoice_generator.service.impl;

import com.kalyan.invoice_generator.dto.*;
import com.kalyan.invoice_generator.entity.Invoice;
import com.kalyan.invoice_generator.entity.InvoiceItem;
import com.kalyan.invoice_generator.exception.ResourceNotFoundException;
import com.kalyan.invoice_generator.repository.InvoiceRepository;
import com.kalyan.invoice_generator.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public InvoiceResponse createInvoice(CreateInvoiceRequest request) {

        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setCustomerName(request.getCustomerName());
        invoice.setCreatedAt(LocalDateTime.now());

        BigDecimal subtotal = BigDecimal.ZERO;

        for (InvoiceItemRequest itemRequest : request.getItems()) {

            BigDecimal amount = itemRequest.getPrice()
                    .multiply(
                            BigDecimal.valueOf(
                                    itemRequest.getQuantity()));

            subtotal = subtotal.add(amount);

            InvoiceItem item = InvoiceItem.builder()
                    .productName(itemRequest.getProductName())
                    .quantity(itemRequest.getQuantity())
                    .price(itemRequest.getPrice())
                    .amount(amount)
                    .invoice(invoice)
                    .build();

            invoice.getItems().add(item);
        }

        BigDecimal tax = subtotal.multiply(
                BigDecimal.valueOf(0.18));

        BigDecimal total = subtotal.add(tax);

        invoice.setSubtotal(subtotal);
        invoice.setTax(tax);
        invoice.setTotal(total);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        return mapToResponse(savedInvoice);
    }

    private InvoiceResponse mapToResponse(Invoice invoice) {

        List<InvoiceItemResponse> items = invoice.getItems()
                .stream()
                .map(item -> InvoiceItemResponse
                        .builder()
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .amount(item.getAmount())
                        .build())
                .toList();

        return InvoiceResponse.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .customerName(invoice.getCustomerName())
                .subtotal(invoice.getSubtotal())
                .tax(invoice.getTax())
                .total(invoice.getTotal())
                .createdAt(invoice.getCreatedAt())
                .items(items)
                .build();
    }

    @Override
    public List<InvoiceResponse> getAllInvoices() {

        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public InvoiceResponse getInvoiceById(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Invoice not found"));

        return mapToResponse(invoice);
    }

    private String generateInvoiceNumber() {

        long count = invoiceRepository.count();

        return "INV-" + (1001 + count);
    }
}