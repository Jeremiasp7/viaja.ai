// src/components/TravelMap.js
import React, { useState } from 'react';
import './index.css';
import 'leaflet/dist/leaflet.css';
import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  useMap,
} from 'react-leaflet';
import { geocodeAddress, getRoute } from '../../services/maps';
import L from 'leaflet'; // Importa o Leaflet para corrigir ícones

// Correção para o ícone padrão do Marker (bug comum no React-Leaflet)
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

// --- Componente auxiliar para mudar a visão do mapa dinamicamente ---
function ChangeView({ center, zoom }) {
  const map = useMap();
  map.setView(center, zoom);
  return null;
}

// --- Componente auxiliar para desenhar e limpar a rota ---
function RouteLayer({ route }) {
  const map = useMap();

  React.useEffect(() => {
    if (!route) return;

    const layer = L.geoJSON(route, {
      color: 'var(--primary-color)',
      weight: 5,
    }).addTo(map);

    // Centraliza o mapa na rota
    map.fitBounds(layer.getBounds());

    // Remove a camada anterior quando a rota mudar
    return () => {
      map.removeLayer(layer);
    };
  }, [route, map]);

  return null;
}

function TravelMap() {
  const [position, setPosition] = useState([-15.793889, -47.882778]); 
  const [zoom, setZoom] = useState(5);
  const [address, setAddress] = useState('');
  const [origin, setOrigin] = useState('');
  const [destination, setDestination] = useState('');
  const [route, setRoute] = useState(null); 
  const [originMarker, setOriginMarker] = useState(null); // marcador do ponto de origem
  const [loadingGeocode, setLoadingGeocode] = useState(false);
  const [loadingRoute, setLoadingRoute] = useState(false);

  // --- Função para buscar endereço (Geocode) ---
  const handleSearchAddress = async () => {
    try {
      setLoadingGeocode(true);
      const response = await geocodeAddress(address);
      if (response.data && response.data.length > 0) {
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

  // --- Função para buscar rota entre endereços ---
  const handleFindRoute = async () => {
    if (!origin || !destination) {
      alert("Por favor, informe o endereço de origem e destino.");
      return;
    }

    try {
      setLoadingRoute(true);
      const response = await getRoute(origin, destination);

      const encodedOrigin = encodeURIComponent(origin);
      const encodedDestination = encodeURIComponent(destination);
      const url = `http://localhost:8080/maps/route?origin=${encodedOrigin}&destination=${encodedDestination}`;
      //console.log("Requisição da rota:", url);

      if (response.data && response.data.routes && response.data.routes.length > 0) {
        const geometry = response.data.routes[0].geometry;
        setRoute(geometry);

        // Pega a posição de origem do OSRM para colocar o marcador
        const [lon1, lat1] = response.data.waypoints[0].location;
        setOriginMarker([lat1, lon1]); // marcador da origem
        setPosition([lat1, lon1]); // move o ícone do "Buscar Endereço" para a origem

      } else {
        console.error("Rota não encontrada:", response.data);
        alert("Não foi possível encontrar a rota entre os endereços informados.");
      }
    } catch (error) {
      console.error("Erro ao buscar rota:", error);
      alert("Erro ao buscar rota. Verifique os endereços e tente novamente.");
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

        <div className="controls-row">
          <input
            className="half"
            type="text"
            value={origin}
            onChange={(e) => setOrigin(e.target.value)}
            placeholder="Digite a origem"
            aria-label="Endereço de origem"
          />
          <input
            className="half"
            type="text"
            value={destination}
            onChange={(e) => setDestination(e.target.value)}
            placeholder="Digite o destino"
            aria-label="Endereço de destino"
          />
        </div>
        <button onClick={handleFindRoute} className="secondary" aria-busy={loadingRoute}>
          {loadingRoute ? 'Carregando rota...' : 'Buscar rota'}
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
          {/* Atualiza o centro do mapa */}
          <ChangeView center={position} zoom={zoom} />

          {/* Camada de fundo */}
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">
              OpenStreetMap
            </a> contributors'
          />

          {/* Marcador de localização atual */}
          <Marker position={position}>
            <Popup>Localização atual.</Popup>
          </Marker>

          {/* Marcador do ponto de origem da rota */}
          {originMarker && (
            <Marker position={originMarker}>
              <Popup>Origem da rota</Popup>
            </Marker>
          )}

          {/* Camada de rota */}
          {route && <RouteLayer route={route} />}
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
