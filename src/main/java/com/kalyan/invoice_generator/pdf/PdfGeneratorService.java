package com.kalyan.invoice_generator.pdf;

import com.kalyan.invoice_generator.entity.Invoice;
import com.kalyan.invoice_generator.entity.InvoiceItem;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    public byte[] generateInvoicePdf(Invoice invoice) {

        try {

            Document document = new Document();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PdfWriter.getInstance(document, outputStream);

            document.open();

            document.add(
                    new Paragraph("Invoice"));

            document.add(
                    new Paragraph(
                            "Invoice Number: "
                                    + invoice.getInvoiceNumber()));

            document.add(
                    new Paragraph(
                            "Customer: "
                                    + invoice.getCustomerName()));

            document.add(
                    new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);

            table.addCell("Product");
            table.addCell("Quantity");
            table.addCell("Price");
            table.addCell("Amount");

            for (InvoiceItem item : invoice.getItems()) {

                table.addCell(item.getProductName());

                table.addCell(
                        String.valueOf(
                                item.getQuantity()));

                table.addCell(
                        item.getPrice().toString());

                table.addCell(
                        item.getAmount().toString());
            }

            document.add(table);

            document.add(
                    new Paragraph(" "));

            document.add(
                    new Paragraph(
                            "Subtotal: "
                                    + invoice.getSubtotal()));

            document.add(
                    new Paragraph(
                            "Tax (18%): "
                                    + invoice.getTax()));

            document.add(
                    new Paragraph(
                            "Total: "
                                    + invoice.getTotal()));

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Error generating PDF", ex);
        }
    }
}