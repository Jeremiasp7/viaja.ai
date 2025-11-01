import React, { useState, useEffect, useContext } from "react";
import {
  FaSearch,
  FaHome,
  FaHeart,
  FaSuitcase,
  FaCog,
  FaMapMarkerAlt,
  FaCalendarAlt,
} from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { Context } from "../../context/AuthContext";
import {
  getTravelPlansByUser,
  createTravelPlan,
} from "../../services/travelPlans";
import { deleteTravelPlan } from "../../services/travelPlans";
import { getRoteirosByUser } from "../../services/roteiros";
import "./index.css";

const Dashboard = () => {
  const { user, authenticated } = useContext(Context);
  const userName = user?.nome || user?.name;
  const userId = user?.id;
  const [roteiros, setRoteiros] = useState([]);
  const [showCreate, setShowCreate] = useState(false);
  const [creating, setCreating] = useState(false);
  const [form, setForm] = useState({ title: '', startDate: '', endDate: '' });
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchRoteiros = async () => {
      if (!user?.id) return;
      const response = await getRoteirosByUser(user.id);
      setRoteiros(response.data || []);
      //console.log('Roteiros carregados:', response.data);
    };
    fetchRoteiros();
  }, [user?.id]);

  const calcularProgresso = (startDate, endDate) => {
    //console.log('Calculando progresso para datas:', startDate, endDate);
    startDate = startDate.split("-").join("/");
    endDate = endDate.split("-").join("/");

    const inicio = new Date(startDate);
    const fim = new Date(endDate);
    const hoje = new Date();

    if (hoje < inicio) return 0;
    if (hoje > fim) return 100;

    const progresso = ((hoje - inicio) / (fim - inicio)) * 100;
    return Math.min(Math.max(progresso, 0), 100);
  };

  // Retorna imagem genérica por país
  const getImageForDestination = (country) => {
    const images = {
      Portugal:
        "https://media.gettyimages.com/id/171588928/pt/foto/porto-adega.jpg?s=612x612&w=0&k=20&c=2_J1vT9cTsvXes0SocIcvW7Pd67CdKxxWrLctDjKCNQ=",
      Espanha:
        "https://media.gettyimages.com/id/2157285424/pt/foto/fitz-roy-mountain-at-patagonia-national-park-in-argentina.jpg?s=612x612&w=0&k=20&c=dJDwq6bmg4CyobAP60LoM7hWB_l1K9J21AqHd70Bka8=",
      Japão:
        "https://images.unsplash.com/photo-1524413840807-0c3cb6fa808d?auto=format&fit=crop&w=800&q=80",
    };
    return (
      images[country] ||
      "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80"
    );
  };

  const handleCreateToggle = () => {
    setError(null);
    setShowCreate((s) => !s);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    setError(null);
    if (!form.title || !form.startDate || !form.endDate) {
      setError('Preencha todos os campos obrigatórios.');
      return;
    }
    const payload = {
      title: form.title,
      startDate: form.startDate,
      endDate: form.endDate,
      userId: userId,
      destinations: [],
      dayItinerary: [],
    };
    setCreating(true);
    try {
      const resp = await createTravelPlan(payload);
      // refresh list
      const updated = await getRoteirosByUser(userId);
      setRoteiros(updated.data || []);
      setShowCreate(false);
      setForm({ title: '', startDate: '', endDate: '' });
    } catch (err) {
      console.error('Erro ao criar travel plan:', err);
      setError(err.response?.data || err.message || 'Erro ao criar roteiro.');
    } finally {
      setCreating(false);
    }
  };

  return (
    <div className="home-screen">
      <header className="home-header">
        <div className="header-content">
          {authenticated && userName ? <h1>Olá, {userName}!</h1> : <h1>Olá</h1>}
          <h2>Para onde vamos viajar hoje?</h2>
          <div className="search-bar">
            <FaSearch className="search-icon" />
            <input
              type="text"
              placeholder="Digite um destino para sua próxima viagem"
            />
          </div>
        </div>
      </header>

      <main className="container">
        <section className="trips-section">
          <h3>Seus Roteiros de Viagens</h3>
          <div className="cards-container">
            {roteiros.length > 0 ? (
              <>
                {roteiros
                  .slice(-3)
                  .reverse()
                  .map((roteiro) => {
                    const firstDestination = roteiro.destinations?.[0];
                    const image = getImageForDestination(
                      firstDestination?.country,
                    );
                    const progresso = calcularProgresso(
                      roteiro.startDate,
                      roteiro.endDate,
                    );

                    return (
                      <div
                        key={roteiro.id}
                        className="trip-card"
                        onClick={() => navigate(`/itinerary/${roteiro.id}`)}
                      >
                        <img src={image} alt={roteiro.title} />
                        <div className="trip-info">
                          <h4>{roteiro.title}</h4>
                          <p>
                            <FaMapMarkerAlt /> {firstDestination?.city},{" "}
                            {firstDestination?.country}
                          </p>
                          <p>
                            <FaCalendarAlt />{" "}
                            {roteiro.startDate.split("-").reverse().join("/")} -{" "}
                            {roteiro.endDate.split("-").reverse().join("/")}
                          </p>
                          <div className="progress-bar">
                            <div
                              className="progress"
                              style={{ width: `${progresso}%` }}
                            ></div>
                          </div>
                        </div>
                      </div>
                    );
                  })}

                {/* Card para criar nova viagem */}
                <div className="trip-card new-trip">
                  {!showCreate ? (
                    <>
                      <h4>Novo Roteiro</h4>
                      <button
                        className="btn-create"
                        onClick={handleCreateToggle}
                      >
                        + Criar Roteiro
                      </button>
                    </>
                  ) : (
                    <form className="create-form" onSubmit={handleCreate}>
                      <input 
                        name="title" 
                        value={form.title} 
                        onChange={handleChange} 
                        placeholder="Título do roteiro" 
                      />
                      <input 
                        name="startDate" 
                        value={form.startDate} 
                        onChange={handleChange} 
                        type="date" 
                      />
                      <input 
                        name="endDate" 
                        value={form.endDate} 
                        onChange={handleChange} 
                        type="date" 
                      />
                      {error && <div className="error">{String(error)}</div>}
                      <div className="form-actions">
                        <button 
                          type="button" 
                          className="btn-secondary" 
                          onClick={handleCreateToggle}
                        >
                          Cancelar
                        </button>
                        <button 
                          type="submit" 
                          className="btn-create" 
                          disabled={creating}
                        >
                          {creating ? 'Criando...' : 'Criar'}
                        </button>
                      </div>
                    </form>
                  )}
                </div>
              </>
            ) : (
              <div className="trip-card new-trip">
                {!showCreate ? (
                  <>
                    <h4>Novo Roteiro</h4>
                    <button
                      className="btn-create"
                      onClick={handleCreateToggle}
                    >
                      + Criar Roteiro
                    </button>
                  </>
                ) : (
                  <form className="create-form" onSubmit={handleCreate}>
                    <input 
                      name="title" 
                      value={form.title} 
                      onChange={handleChange} 
                      placeholder="Título do roteiro" 
                    />
                    <input 
                      name="startDate" 
                      value={form.startDate} 
                      onChange={handleChange} 
                      type="date" 
                    />
                    <input 
                      name="endDate" 
                      value={form.endDate} 
                      onChange={handleChange} 
                      type="date" 
                    />
                    {error && <div className="error">{String(error)}</div>}
                    <div className="form-actions">
                      <button 
                        type="button" 
                        className="btn-secondary" 
                        onClick={handleCreateToggle}
                      >
                        Cancelar
                      </button>
                      <button 
                        type="submit" 
                        className="btn-create" 
                        disabled={creating}
                      >
                        {creating ? 'Criando...' : 'Criar'}
                      </button>
                    </div>
                  </form>
                )}
              </div>
            )}
          </div>
        </section>

        <section className="suggestions-section">
          <h3>Inspire-se (Sugestões para você)</h3>
          <div className="cards-container">
            <div className="suggestion-card">
              <img
                src="https://media.gettyimages.com/id/1772867295/pt/foto/wat-arun-buddhist-temple-and-chao-phraya-river-on-a-sunny-day-bangkok-thailand.jpg?s=612x612&w=0&k=20&c=V2GG0c9QGnZP4MjO-vXkyaPk32g3zNaa5NZ-_t4r2ws="
                alt="Tailândia"
              />
              <p>Praias e Templos na Tailândia</p>
            </div>
            <div className="suggestion-card">
              <img
                src="https://media.gettyimages.com/id/2157285424/pt/foto/fitz-roy-mountain-at-patagonia-national-park-in-argentina.jpg?s=612x612&w=0&k=20&c=dJDwq6bmg4CyobAP60LoM7hWB_l1K9J21AqHd70Bka8="
                alt="Patagônia"
              />
              <p>Natureza na Patagônia</p>
            </div>
            <div className="suggestion-card">
              <img
                src="https://media.gettyimages.com/id/171588928/pt/foto/porto-adega.jpg?s=612x612&w=0&k=20&c=2_J1vT9cTsvXes0SocIcvW7Pd67CdKxxWrLctDjKCNQ="
                alt="Lisboa"
              />
              <p>Cultura e Gastronomia em Portugal</p>
            </div>
          </div>
        </section>
      </main>

      <footer className="app-footer">
        <div className="footer-icon active" onClick={() => navigate("/")}>
          <FaHome />
          <p>Início</p>
        </div>
        <div className="footer-icon" onClick={() => navigate("/roteiros")}>
          <FaSuitcase />
          <p>Roteiros</p>
        </div>
        <div className="footer-icon" onClick={() => navigate("/favoritos")}>
          <FaHeart />
          <p>Favoritos</p>
        </div>
        <div className="footer-icon" onClick={() => navigate("/ajustes")}>
          <FaCog />
          <p>Ajustes</p>
        </div>
      </footer>
    </div>
  );
};

export default Dashboard;
