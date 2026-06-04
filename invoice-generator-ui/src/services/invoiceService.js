import axios from "axios";

const getBaseURL = () => {
  // In Docker container, use backend service name
  // In localhost development, use localhost:8081
  if (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1') {
    return 'http://localhost:8081/api/invoices';
  }
  // For Docker or other deployments, derive from current hostname
  return `http://${window.location.hostname}:8081/api/invoices`;
};

const API_URL = getBaseURL();

export const createInvoice = async (invoice) => {
  const response = await axios.post(API_URL, invoice);
  return response.data;
};

export const getAllInvoices = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const downloadInvoicePdf = (id) => {
  window.open(`${API_URL}/${id}/pdf`, "_blank");
};