import React, { useEffect, useState } from "react";

import {
  getAllInvoices,
  downloadInvoicePdf,
} from "../services/invoiceService";

const InvoiceHistory = ({
  refreshHistory
}) => {

  const [invoices, setInvoices] = useState([]);

    useEffect(() => {
    loadInvoices();
    }, [refreshHistory]);

  const loadInvoices = async () => {
    const data = await getAllInvoices();
    setInvoices(data);
  };

  return (
    <div>
      <h2>Invoice History</h2>

      <table border="1" width="100%">
        <thead>
          <tr>
            <th>Invoice Number</th>
            <th>Customer</th>
            <th>Total</th>
            <th>PDF</th>
          </tr>
        </thead>

        <tbody>
          {invoices.map((invoice) => (
            <tr key={invoice.id}>
              <td>{invoice.invoiceNumber}</td>
              <td>{invoice.customerName}</td>
              <td>{invoice.total}</td>

              <td>
                <button
                  onClick={() =>
                    downloadInvoicePdf(invoice.id)
                  }
                >
                  Download PDF
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default InvoiceHistory;