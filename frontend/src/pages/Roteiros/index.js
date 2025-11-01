import React, { useContext, useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { FaArrowLeft, FaPlus, FaMapMarkerAlt, FaCalendarAlt, FaHome, FaCog, FaHeart, FaSuitcase } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import { Context } from '../../context/AuthContext';
import { getRoteirosByUser } from '../../services/roteiros';
import './index.css';

const Roteiros = () => {
  const navigate = useNavigate();
  const { user } = useContext(Context);
  const [roteiros, setRoteiros] = useState([]);
  const location = useLocation();
  const path = location.pathname || "/";
  const isActive = (p) => path === p || path.startsWith(p + "/");

  useEffect(() => {
    const fetchRoteiros = async () => {
      if (!user?.id) return;
      const response = await getRoteirosByUser(user.id);
      setRoteiros(response.data || []);
    };
    fetchRoteiros();
  }, [user?.id]);

  const calcularProgresso = (startDate, endDate) => {
    startDate = startDate.split('-').join('/');
    endDate = endDate.split('-').join('/');
    const inicio = new Date(startDate);
    const fim = new Date(endDate);
    const hoje = new Date();
    if (hoje < inicio) return 0;
    if (hoje > fim) return 100;
    return Math.min(Math.max(((hoje - inicio) / (fim - inicio)) * 100, 0), 100);
  };

  const getImageForDestination = (country) => {
    const images = {
      Portugal: 'https://media.gettyimages.com/id/171588928/pt/foto/porto-adega.jpg?s=612x612&w=0&k=20&c=2_J1vT9cTsvXes0SocIcvW7Pd67CdKxxWrLctDjKCNQ=',
      Espanha: 'https://media.gettyimages.com/id/2157285424/pt/foto/fitz-roy-mountain-at-patagonia-national-park-in-argentina.jpg?s=612x612&w=0&k=20&c=dJDwq6bmg4CyobAP60LoM7hWB_l1K9J21AqHd70Bka8=',
      Japão: 'https://images.unsplash.com/photo-1524413840807-0c3cb6fa808d?auto=format&fit=crop&w=800&q=80',
    };
    return images[country] || 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80';
  };

  return (
    <div className="trips-page">
      <header className="trips-header">
        <h1>Meus Roteiros</h1>
      </header>

      <main className="trips-container">
        {roteiros.length > 0 ? (
          <div className="trips-grid">
            {roteiros.map((roteiro) => {
              const firstDestination = roteiro.destinations?.[0];
              const image = getImageForDestination(firstDestination?.country);
              const progresso = calcularProgresso(roteiro.startDate, roteiro.endDate);

              return (
                <div
                  key={roteiro.id}
                  className="trip-card"
                  onClick={() => navigate(`/itinerary/${roteiro.id}`)}
                >
                  <img src={image} alt={roteiro.title} />
                  <div className="trip-details">
                    <h3>{roteiro.title}</h3>
                    <p><FaMapMarkerAlt /> {firstDestination?.city || firstDestination?.country}</p>
                    <p>
                      <FaCalendarAlt />{' '}
                      {roteiro.startDate.split('-').reverse().join('/')} -{' '}
                      {roteiro.endDate.split('-').reverse().join('/')}
                    </p>
                    <div className="progress-bar">
                      <div className="progress" style={{ width: `${progresso}%` }}></div>
                    </div>
                  </div>
                </div>
              );
            })}

            <div className="trip-card new-trip" onClick={() => navigate('/itinerary')}>
              <FaPlus className="icon-plus" />
              <p>Criar novo roteiro</p>
            </div>
          </div>
        ) : (
          <div className="empty-state">
            <p>Você ainda não possui roteiros criados.</p>
            <button className="btn-create" onClick={() => navigate('/itinerary')}>
              <FaPlus /> Criar seu primeiro roteiro
            </button>
          </div>
        )}
      </main>


      {/* ---- Rodapé ---- */}
      <footer className="app-footer">
        <div
          className={`footer-icon ${isActive("/dashboard") ? "active" : ""}`}
          onClick={() => navigate("/dashboard")}
        >
          <FaHome />
          <p>Início</p>
        </div>
        <div
          className={`footer-icon ${isActive("/roteiros") ? "active" : ""}`}
          onClick={() => navigate("/roteiros")}
        >
          <FaSuitcase />
          <p>Roteiros</p>
        </div>
        <div
          className={`footer-icon ${isActive("/favoritos") ? "active" : ""}`}
        >
          <FaHeart />
          <p>Favoritos</p>
        </div>
        <div
          className={`footer-icon ${isActive("/ajustes") ? "active" : ""}`}
          onClick={() => navigate("/ajustes")}
          title="Preferências"
        >
          <FaCog />
          <p>Preferências</p>
        </div>
      </footer>
    </div>
  );
};

export default Roteiros;
