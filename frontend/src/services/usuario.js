import api from '../api';

export const updateUser = (userId, userData) => {
  return api.put(`/usuarios/${userId}`, userData);
}

export const deleteUser = (userId) => {
  return api.delete(`/usuarios/${userId}`);
}