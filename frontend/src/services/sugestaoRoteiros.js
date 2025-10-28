import api from '../api';

/**
 * Busca sugestão de roteiro baseada nas preferências do usuário
 * @param {string} userId - UUID do usuário
 */
export const getSuggestionByPreferences = (userId) => {
  return api.get(`/sugestao-de-roteiros/preferencias/${userId}`);
};

/**
 * Busca sugestão de roteiro baseada em um travelPlan
 * @param {string} travelPlanId - UUID do travel plan
 */
export const getSuggestionByTravelPlan = (travelPlanId) => {
  return api.get(`/sugestao-de-roteiros/planos/${travelPlanId}`);
};
