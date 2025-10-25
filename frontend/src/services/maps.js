// src/services/mapService.js
import api from '../api';

/**
 * Busca coordenadas (latitude, longitude) para um endereço.
 * @param {string} address - O endereço a ser geocodificado.
 * @returns {Promise<object>} A resposta da API Nominatim (geralmente um array).
 */
export const geocodeAddress = (address) => {
  return api.get('/maps/geocode', {
    params: { address },
  });
};

/**
 * Busca um endereço a partir de coordenadas.
 * @param {number} lat - Latitude
 * @param {number} lon - Longitude
 * @returns {Promise<object>} A resposta da API Nominatim.
 */
export const reverseGeocode = (lat, lon) => {
  return api.get('/maps/reverse', {
    params: { lat, lon },
  });
};

/**
 * Busca uma rota entre dois pontos.
 * @param {number} lat1 - Latitude do Ponto A
 * @param {number} lon1 - Longitude do Ponto A
 * @param {number} lat2 - Latitude do Ponto B
 * @param {number} lon2 - Longitude do Ponto B
 * @returns {Promise<object>} A resposta da API OSRM com a geometria da rota.
 */
export const getRoute = (lat1, lon1, lat2, lon2) => {
  return api.get('/maps/route', {
    params: { lat1, lon1, lat2, lon2 },
  });
};