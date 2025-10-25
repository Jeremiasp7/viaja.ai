// src/components/TravelMap.js
import React, { useState, useEffect } from 'react';
import './index.css';
import 'leaflet/dist/leaflet.css';
import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  GeoJSON,
  useMap,
} from 'react-leaflet';
import { geocodeAddress, getRoute } from '../../services/maps'
import L from 'leaflet'; // Importa o Leaflet para corrigir ícones

// Correção para o ícone padrão do Marker (bug comum no React-Leaflet)
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

// Componente auxiliar para mudar a visão do mapa dinamicamente
function ChangeView({ center, zoom }) {
  const map = useMap();
  map.setView(center, zoom);
  return null;
}

function TravelMap() {
  const [position, setPosition] = useState([-15.793889, -47.882778]); // Posição inicial (Brasília)
  const [zoom, setZoom] = useState(5);
  const [address, setAddress] = useState('');
  const [route, setRoute] = useState(null); // Armazena a geometria da rota (GeoJSON)
  const [loadingGeocode, setLoadingGeocode] = useState(false);
  const [loadingRoute, setLoadingRoute] = useState(false);

  // --- Função para buscar endereço (Geocode) ---
  const handleSearchAddress = async () => {
    try {
      setLoadingGeocode(true);
      const response = await geocodeAddress(address);
      if (response.data && response.data.length > 0) {
        // A API Nominatim retorna um array
        const { lat, lon } = response.data[0];
        const newPos = [parseFloat(lat), parseFloat(lon)];
        setPosition(newPos);
        setZoom(13);
      } else {
        alert('Endereço não encontrado.');
      }
    } catch (error) {
      console.error('Erro ao buscar endereço:', error);
      alert('Erro ao conectar ao servidor.');
    } finally {
      setLoadingGeocode(false);
    }
  };

  // --- Função para buscar Rota (Exemplo) ---
  const handleFindRoute = async () => {
    // Exemplo fixo: De São Paulo para Rio de Janeiro
    const pointA = { lat: -23.55052, lon: -46.633308 }; // SP
    const pointB = { lat: -22.906847, lon: -43.172896 }; // RJ

    try {
      setLoadingRoute(true);
      const response = await getRoute(
        pointA.lat,
        pointA.lon,
        pointB.lat,
        pointB.lon
      );
      // A API OSRM retorna a geometria em response.data.routes[0].geometry
      if (response.data && response.data.routes) {
        // Try to extract GeoJSON geometry; if server returns encoded polyline, leave as-is
        const geometry = response.data.routes[0].geometry;
        setRoute(geometry);
        // Centraliza o mapa entre os dois pontos (opcional)
        setPosition([
          (pointA.lat + pointB.lat) / 2,
          (pointA.lon + pointB.lon) / 2,
        ]);
        setZoom(7);
      }
    } catch (error) {
      console.error('Erro ao buscar rota:', error);
    } finally {
      setLoadingRoute(false);
    }
  };

  return (
    <div className="maps-wrapper">
      {/* Controles de Busca */}
      <div className="maps-controls">
        <input
          type="text"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
          placeholder="Digite um endereço"
          aria-label="Endereço para busca"
        />
        <button onClick={handleSearchAddress} aria-busy={loadingGeocode}>
          {loadingGeocode ? 'Buscando...' : 'Buscar Endereço'}
        </button>
        <button
          onClick={handleFindRoute}
          className="secondary"
          aria-busy={loadingRoute}
        >
          {loadingRoute ? 'Carregando rota...' : 'Mostrar Rota (SP-RJ)'}
        </button>
      </div>

      {/* Container do Mapa */}
      <div className="map-container-custom map-loading">
      <MapContainer
        center={position}
        zoom={zoom}
        className="leaflet-container"
        style={{ height: '100%', width: '100%' }}
      >
        {/* Componente para atualizar o centro do mapa */}
        <ChangeView center={position} zoom={zoom} />

        {/* Camada de "fundo" do mapa (OpenStreetMap) */}
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        />

        {/* Marcador na posição buscada */}
        <Marker position={position}>
          <Popup>Localização atual.</Popup>
        </Marker>

        {/* Renderiza a rota se ela existir no estado */}
        {route && (
          // If route is GeoJSON object, render as GeoJSON with a style
          typeof route === 'object' ? (
            <GeoJSON data={route} style={() => ({ color: 'var(--primary-color)', weight: 5 })} />
          ) : (
            // If server returns encoded polyline string, you may decode it on the server or here with polyline lib
            // For now, attempt to render nothing and log it
            console.warn('Rota retornada não está em GeoJSON (é uma string codificada).')
          )
        )}
      </MapContainer>

      {/* Loading overlays */}
      {(loadingGeocode || loadingRoute) && (
        <div className="loading-overlay">
          <div className="loading-spinner" aria-hidden="true"></div>
        </div>
      )}
      </div>
    </div>
  );
}

export default TravelMap;