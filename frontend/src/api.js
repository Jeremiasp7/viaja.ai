import axios from 'axios';

const baseURL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL,
});

function normalizeStoredToken(raw) {
  if (!raw) return null;
  // If token was saved with JSON.stringify previously, try to parse it
  try {
    const parsed = JSON.parse(raw);
    if (typeof parsed === 'string') return parsed;
  } catch (e) {
    // not JSON, continue
  }
  // strip surrounding quotes if present
  if (raw.startsWith('"') && raw.endsWith('"')) {
    return raw.slice(1, -1);
  }
  return raw;
}

const existingToken = normalizeStoredToken(localStorage.getItem('token'));
if (existingToken) {
  api.defaults.headers.common.Authorization = `Bearer ${existingToken}`;
}

export function setAuthToken(token) {
  if (token) {
    api.defaults.headers.common.Authorization = `Bearer ${token}`;
    // store as plain string to avoid double-encoding issues
    localStorage.setItem('token', token);
  } else {
    delete api.defaults.headers.common.Authorization;
    localStorage.removeItem('token');
  }
}

export default api;

// Global response interceptor: on 401, remove token and navigate to login
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      try {
        localStorage.removeItem('token');
        delete api.defaults.headers.common.Authorization;
      } catch (e) {
        // ignore
      }
      // Redirect to login page to re-authenticate
      if (typeof window !== 'undefined') {
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);