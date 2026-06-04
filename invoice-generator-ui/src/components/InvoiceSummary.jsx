import React from "react";

const InvoiceSummary = ({ products }) => {

  const subtotal = products.reduce(
    (sum, p) => sum + p.quantity * p.price,
    0
  );

  const tax = subtotal * 0.18;

  const total = subtotal + tax;

  return (
    <div>
      <h3>Invoice Summary</h3>

      <p>Subtotal: ₹{subtotal.toFixed(2)}</p>

      <p>Tax (18%): ₹{tax.toFixed(2)}</p>

      <p>Total: ₹{total.toFixed(2)}</p>
    </div>
  );
};

export default InvoiceSummary;