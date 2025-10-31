import api from '../api';

/**
 * Busca as preferências do usuário
 * @param {string} userId
 */
export const getUserPreferences = (userId) => {
  return api.get(`/preferencias/${userId}`);
};

/**
 * Atualiza/cria as preferências do usuário
 * @param {string} userId
 * @param {object} dto - CreateUserPreferencesDto shape
 */
export const updateUserPreferences = (userId, dto) => {
  return api.post(`/preferencias/${userId}`, dto);
};

export default {
  getUserPreferences,
  updateUserPreferences,
};
