import axios from 'axios';

const baseURL = process.env.REACT_APP_API_URL || 'http://localhost:8082/treinaai';

const api = axios.create({
  baseURL,
});

function normalizeStoredToken(raw) {
  if (!raw) return null;
  try {
    const parsed = JSON.parse(raw);
    if (typeof parsed === 'string') return parsed;
  } catch (e) {
    // not JSON, continue
  }
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
    localStorage.setItem('token', token);
  } else {
    delete api.defaults.headers.common.Authorization;
    localStorage.removeItem('token');
  }
}

export default api;

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      try {
        localStorage.removeItem('token');
        delete api.defaults.headers.common.Authorization;
        window.location.href = '/login';
      } catch (e) {
        console.error('Erro ao fazer logout:', e);
      }
    }
    return Promise.reject(error);
  }
);
