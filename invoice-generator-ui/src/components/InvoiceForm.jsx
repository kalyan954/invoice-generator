import React, { useState } from "react";

import InvoiceTable from "./InvoiceTable";
import InvoiceSummary from "./InvoiceSummary";

import { createInvoice } from "../services/invoiceService";

const InvoiceForm = ({ onInvoiceCreated }) => {

  const [customerName, setCustomerName] =
    useState("");

  const [productName, setProductName] =
    useState("");

  const [quantity, setQuantity] =
    useState("");

  const [price, setPrice] =
    useState("");

  const [products, setProducts] =
    useState([]);

  const removeProduct = (index) => {

  const updatedProducts =
    products.filter(
      (_, i) => i !== index
    );

  setProducts(updatedProducts);
};

  const addProduct = () => {

    const product = {
      productName,
      quantity: Number(quantity),
      price: Number(price),
    };

    setProducts([...products, product]);

    setProductName("");
    setQuantity("");
    setPrice("");
  };

  const handleGenerateInvoice = async () => {

    const invoice = {
      customerName,
      items: products,
    };

    try {

      await createInvoice(invoice);

      alert("Invoice Created Successfully");

      onInvoiceCreated();

      setCustomerName("");
      setProducts([]);

    } catch (error) {

      console.error(error);

      alert("Failed to create invoice");
    }
  };

  return (
    <div>

      <h1>Invoice Generator</h1>

      <input
        placeholder="Customer Name"
        value={customerName}
        onChange={(e) =>
          setCustomerName(e.target.value)
        }
      />

      <br />
      <br />

      <input
        placeholder="Product Name"
        value={productName}
        onChange={(e) =>
          setProductName(e.target.value)
        }
      />

      <input
        placeholder="Quantity"
        type="number"
        value={quantity}
        onChange={(e) =>
          setQuantity(e.target.value)
        }
      />

      <input
        placeholder="Price"
        type="number"
        value={price}
        onChange={(e) =>
          setPrice(e.target.value)
        }
      />

      <button onClick={addProduct}>
        Add Product
      </button>

      <br />
      <br />

      <InvoiceTable products={products} removeProduct={removeProduct}/>

      <br />

      <InvoiceSummary products={products} />

      <br />

      <button onClick={handleGenerateInvoice}>
        Generate Invoice
      </button>

    </div>
  );
};

const removeProduct = (index) => {

  const updatedProducts =
    products.filter(
      (_, i) => i !== index
    );

  setProducts(updatedProducts);
};

export default InvoiceForm;