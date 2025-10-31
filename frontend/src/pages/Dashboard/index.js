import React, { useEffect, useState } from 'react';
import { FaSearch, FaHome, FaHeart, FaSuitcase, FaCog } from 'react-icons/fa';
import { FaTrash } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import { Context } from '../../context/AuthContext';
import { getTravelPlansByUser, createTravelPlan } from '../../services/travelPlans';
import { deleteTravelPlan } from '../../services/travelPlans';

import './index.css';


const Dashboard = () => {
  const { user, authenticated } = React.useContext(Context);
  const userName = user?.nome || user?.name;
  const userId = user?.id;

  const navigate = useNavigate();

  const [plans, setPlans] = useState([]);
  const [loadingPlans, setLoadingPlans] = useState(false);
  const [showCreate, setShowCreate] = useState(false);
  const [creating, setCreating] = useState(false);
  const [form, setForm] = useState({ title: '', startDate: '', endDate: '' });
  const [error, setError] = useState(null);

  const getInitials = (title) => {
    if (!title) return 'VA';
    return title
      .split(' ')
      .filter(Boolean)
      .map((s) => s[0])
      .slice(0, 2)
      .join('')
      .toUpperCase();
  };

  // deterministic color generator for a given key (id or title)
  const colorFromString = (key) => {
    const str = String(key || 'viajaai');
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
      hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }
    const hue = Math.abs(hash) % 360;
    const h1 = hue;
    const h2 = (hue + 40) % 360;
    return `linear-gradient(135deg, hsl(${h1},70%,48%) 0%, hsl(${h2},70%,60%) 100%)`;
  };

  useEffect(() => {
    async function fetchPlans() {
      if (!authenticated || !userId) return;
      setLoadingPlans(true);
      try {
        const resp = await getTravelPlansByUser(userId);
        setPlans(resp.data || []);
      } catch (err) {
        console.error('Erro ao buscar travel plans:', err);
      } finally {
        setLoadingPlans(false);
      }
    }
    fetchPlans();
  }, [authenticated, userId]);

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
      const updated = await getTravelPlansByUser(userId);
      setPlans(updated.data || []);
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
          {authenticated && userName ? (
            <h1>Olá, {userName}!</h1>
          ) : (
            <h1>Olá</h1>
          )}
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
            {loadingPlans ? (
              <p>Carregando seus roteiros...</p>
            ) : (
              plans.map((p) => (
                <div key={p.id} className="trip-card" onClick={() => navigate(`/itinerary/${p.id}`)} style={{ position: 'relative' }}>
                  <button className="btn-icon delete-plan" title="Remover roteiro" onClick={async (e) => {
                    e.stopPropagation();
                    if (!window.confirm('Deseja realmente remover este roteiro?')) return;
                    try {
                      await deleteTravelPlan(p.id);
                      const updated = await getTravelPlansByUser(userId);
                      setPlans(updated.data || []);
                    } catch (err) {
                      console.error('Erro ao deletar roteiro:', err);
                      alert('Não foi possível deletar o roteiro');
                    }
                  }}><FaTrash /></button>
                      {p.coverImage ? (
                        <img src={p.coverImage} alt={p.title} />
                      ) : (
                        <div className="plan-cover" aria-hidden style={{ background: colorFromString(p.id || p.title) }}>
                          <span className="plan-cover-title" title={p.title}>{p.title}</span>
                        </div>
                      )}
                  <div className="trip-info">
                    <h4>{p.title}</h4>
                    <p>{p.startDate} - {p.endDate}</p>
                  </div>
                </div>
              ))
            )}

            {/* Card para criar uma nova viagem */}
            <div className="trip-card new-trip">
              {!showCreate ? (
                <>
                  <h4>Novo Roteiro</h4>
                  <button className="btn-create" onClick={handleCreateToggle}>+ Criar Roteiro</button>
                </>
              ) : (
                <form className="create-form" onSubmit={handleCreate}>
                  <input name="title" value={form.title} onChange={handleChange} placeholder="Título do roteiro" />
                  <input name="startDate" value={form.startDate} onChange={handleChange} type="date" />
                  <input name="endDate" value={form.endDate} onChange={handleChange} type="date" />
                  {error && <div className="error">{String(error)}</div>}
                  <div className="form-actions">
                    <button type="button" className="btn-secondary" onClick={handleCreateToggle}>Cancelar</button>
                    <button type="submit" className="btn-create" disabled={creating}>{creating ? 'Criando...' : 'Criar'}</button>
                  </div>
                </form>
              )}
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
        <div className="footer-icon" onClick={() => navigate('/ajustes')} title="Preferências"><FaCog /><p>Preferências</p></div>
      </footer>


    </div>
   );
};

export default Dashboard;
