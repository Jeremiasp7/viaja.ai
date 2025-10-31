import api from '../api';

export const getRoteirosByUser = (userId) => {
  return api.get(`/planos/usuario/${userId}`);
}