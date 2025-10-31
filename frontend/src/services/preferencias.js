import api from '../api';

export const getPreferenciasByUser = (userId) => {
  return api.get(`/preferencias/${userId}`);
}

export const updatePreferencias = (userId, preferenciasData) => {
  return api.put(`/preferencias/${userId}`, preferenciasData);
}

export const createPreferencias = (userId, preferenciasData) => {
  return api.post(`/preferencias/${userId}`, preferenciasData);
}