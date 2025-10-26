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
 * @param {string} origin - Ponto de origem
 * @param {string} detination - Ponto de destino
 * @returns {Promise<object>} A resposta da API OSRM com a geometria da rota.
 */
export const getRoute = (origin, destination) => {
  return api.get('/maps/route', {
    params: { origin, destination },
  });
};