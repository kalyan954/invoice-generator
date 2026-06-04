import axios from "axios";

const getBaseURL = () => {
  // In Docker container, use backend service name
  // In localhost development, use localhost:8081
  if (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1') {
    return 'http://localhost:8081/api';
  }
  // For Docker or other deployments, derive from current hostname
  return `http://${window.location.hostname}:8081/api`;
};

const api = axios.create({
  baseURL: getBaseURL(),
});

export default api;