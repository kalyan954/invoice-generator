import React from "react";

const InvoiceTable = ({ products, removeProduct }) => {

  return (
    <table border="1" width="100%">
      <thead>
        <tr>
          <th>Product</th>
          <th>Qty</th>
          <th>Price</th>
          <th>Amount</th>
          <th>Action</th>
        </tr>
      </thead>

      <tbody>
        {products.length === 0 ? (
          <tr>
            <td colSpan="5">
              No Products Added
            </td>
          </tr>
        ) : (
          products.map((product, index) => (
            <tr key={index}>
              <td>{product.productName}</td>
              <td>{product.quantity}</td>
              <td>{product.price}</td>

              <td>
                {product.quantity * product.price}
              </td>

              <td>
                <button
                  onClick={() =>
                    removeProduct(index)
                  }
                >
                  Remove
                </button>
              </td>
            </tr>
          ))
        )}
      </tbody>
    </table>
  );
};

export default InvoiceTable;