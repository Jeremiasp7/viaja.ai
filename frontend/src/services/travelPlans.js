import api from '../api';

/**
 * Travel Plan service
 */
export const getTravelPlansByUser = (userId) => {
  return api.get(`/planos/usuario/${userId}`);
};

export const createTravelPlan = (dto) => {
  return api.post('/planos', dto);
};

export const getTravelPlanById = (id) => {
  return api.get(`/planos/${id}`);
};

export const updateTravelPlan = (id, dto) => {
  return api.put(`/planos/${id}`, dto);
};

export const deleteTravelPlan = (id) => {
  return api.delete(`/planos/${id}`);
};

export default {
  getTravelPlansByUser,
  createTravelPlan,
  getTravelPlanById,
  updateTravelPlan,
  deleteTravelPlan,
};
