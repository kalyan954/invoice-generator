package com.kalyan.invoice_generator.repository;

import com.kalyan.invoice_generator.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}