package com.kalyan.invoice_generator.controller;

import com.kalyan.invoice_generator.dto.CreateInvoiceRequest;
import com.kalyan.invoice_generator.dto.InvoiceResponse;
import com.kalyan.invoice_generator.entity.Invoice;
import com.kalyan.invoice_generator.exception.ResourceNotFoundException;
import com.kalyan.invoice_generator.pdf.PdfGeneratorService;
import com.kalyan.invoice_generator.repository.InvoiceRepository;
import com.kalyan.invoice_generator.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;
    private final PdfGeneratorService pdfGeneratorService;

    @PostMapping
    public InvoiceResponse createInvoice(
            @Valid @RequestBody CreateInvoiceRequest request) {

        return invoiceService.createInvoice(request);
    }

    @GetMapping
    public List<InvoiceResponse> getAllInvoices() {

        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public InvoiceResponse getInvoiceById(
            @PathVariable Long id) {

        return invoiceService.getInvoiceById(id);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(
            @PathVariable Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invoice not found"));

        byte[] pdf = pdfGeneratorService.generateInvoicePdf(invoice);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename="
                                + invoice.getInvoiceNumber()
                                + ".pdf")
                .contentType(
                        MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}