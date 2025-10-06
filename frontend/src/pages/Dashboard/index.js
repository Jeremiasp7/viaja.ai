import React from 'react';
import { FaSearch, FaHome, FaHeart, FaSuitcase, FaCog } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

import './index.css';


const Dashboard = () => {
  const userName = "Ana";

  const navigate = useNavigate();

  return (
    <div className="home-screen">
      <header className="home-header">
        <div className="header-content">
          <h1>Olá, {userName}!</h1>
          <h2>Para onde vamos viajar hoje?</h2>
          <div className="search-bar">
            <FaSearch className="search-icon" />
            <input type="text" placeholder="Digite um destino para sua próxima viagem" />
          </div>
        </div>
      </header>

      <main className="container">
        <section className="trips-section">
          <h3>Suas Próximas Viagens</h3>
          <div className="cards-container">
            {/* Card de uma viagem existente */}
            <div className="trip-card" onClick={() => navigate('/itinerary')}>
              <img src="https://images.unsplash.com/photo-1524413840807-0c3cb6fa808d?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDE0fHxreW90b3xlbnwwfHx8fDE2MzI5MDYwNTA&ixlib=rb-1.2.1&q=80&w=400" alt="Kyoto" />
              <div className="trip-info">
                <h4>Viagem ao Japão</h4>
                <p>15/10 - 30/10</p>
                <div className="progress-bar">
                  <div className="progress" style={{ width: '70%' }}></div>
                </div>
              </div>
            </div>
            {/* Card para criar uma nova viagem */}
            <div className="trip-card new-trip">
              <button className="btn-create" onClick={() => navigate('/itinerary')}>+ Criar Roteiro</button>
            </div>
          </div>
        </section>

        <section className="suggestions-section">
          <h3>Inspire-se (Sugestões para você )</h3>
          <div className="cards-container">
            <div className="suggestion-card">
              <img src="https://media.gettyimages.com/id/1772867295/pt/foto/wat-arun-buddhist-temple-and-chao-phraya-river-on-a-sunny-day-bangkok-thailand.jpg?s=612x612&w=0&k=20&c=V2GG0c9QGnZP4MjO-vXkyaPk32g3zNaa5NZ-_t4r2ws=" alt="Tailândia" />
              <p>Praias e Templos na Tailândia</p>
            </div>
            <div className="suggestion-card">
              <img src="https://media.gettyimages.com/id/2157285424/pt/foto/fitz-roy-mountain-at-patagonia-national-park-in-argentina.jpg?s=612x612&w=0&k=20&c=dJDwq6bmg4CyobAP60LoM7hWB_l1K9J21AqHd70Bka8=" alt="Patagônia" />
              <p>Natureza na Patagônia</p>
            </div>
            <div className="suggestion-card">
              <img src="https://media.gettyimages.com/id/171588928/pt/foto/porto-adega.jpg?s=612x612&w=0&k=20&c=2_J1vT9cTsvXes0SocIcvW7Pd67CdKxxWrLctDjKCNQ=" alt="Lisboa" />
              <p>Cultura e Gastronomia em Portugal</p>
            </div>
          </div>
        </section>
      </main>

      <footer className="app-footer">
        <div className="footer-icon active"><FaHome /><p>Início</p></div>
        <div className="footer-icon"><FaSuitcase /><p>Roteiros</p></div>
        <div className="footer-icon"><FaHeart /><p>Favoritos</p></div>
        <div className="footer-icon"><FaCog /><p>Ajustes</p></div>
      </footer>


    </div>
   );
};

export default Dashboard;
