import React, { useState } from 'react';
import { FaArrowLeft, FaHotel, FaUtensils, FaLandmark, FaPlus } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

import './index.css';

const ItineraryManager = () => {
  const [activeTab, setActiveTab] = useState('manual');

  const navigate = useNavigate();

  return (
    <div className="planner-screen">
      <header className="planner-header">
        <button onClick={() => navigate('/dashboard')} className="back-button"><FaArrowLeft /> Voltar</button>
        <h3>Roteiro: Viagem ao Japão</h3>
        <div className="tabs">
          <button 
            className={activeTab === 'manual' ? 'active' : ''} 
            onClick={() => setActiveTab('manual')}>
            Planejamento Manual
          </button>
          <button 
            className={activeTab === 'ia' ? 'active' : ''} 
            onClick={() => setActiveTab('ia')}>
            Sugestão da IA
          </button>
        </div>
      </header>

      <div className="planner-content">
        <div className="itinerary-column">
          {/* Dia 1 */}
          <div className="day-card">
            <h4>Dia 1: 15/10 - Chegada em Tóquio</h4>
            <div className="activity-card">
              <FaHotel className="activity-icon" />
              <p>Check-in no hotel em Shinjuku</p>
            </div>
            <div className="activity-card">
              <FaUtensils className="activity-icon" />
              <p>Jantar: Ichiran Ramen</p>
            </div>
            <button className="add-activity-btn"><FaPlus /> Adicionar Atividade</button>
          </div>

          {/* Dia 2 */}
          <div className="day-card">
            <h4>Dia 2: 16/10 - Cultura e Neon</h4>
            <div className="activity-card">
              <FaLandmark className="activity-icon" />
              <p>Visita ao Templo Senso-ji</p>
            </div>
            <div className="activity-card">
              <FaLandmark className="activity-icon" />
              <p>Cruzamento de Shibuya</p>
            </div>
            <button className="add-activity-btn"><FaPlus /> Adicionar Atividade</button>
          </div>
        </div>

        <div className="map-column">
          <div className="map-placeholder">
            <p>Mapa Interativo</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ItineraryManager;
