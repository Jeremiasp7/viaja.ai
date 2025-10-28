import React, { useState, useContext } from 'react';
import { Context } from '../../context/AuthContext';
import { getSuggestionByPreferences, getSuggestionByTravelPlan } from '../../services/sugestaoRoteiros';
import './index.css';

export default function SugestaoRoteiros() {
  const { user, authenticated } = useContext(Context);
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState('');
  const [error, setError] = useState(null);
  const [travelPlanId, setTravelPlanId] = useState('');

  function decodeJwt(token) {
    try {
      const parts = token.split('.');
      if (parts.length < 2) return null;
      const payload = parts[1];
      const base64 = payload.replace(/-/g, '+').replace(/_/g, '/');
      const json = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));
      return JSON.parse(json);
    } catch (e) {
      return null;
    }
  }

  const fetchByPreferences = async () => {
    setError(null);
    setResult('');

    // determine userId: prefer user.id, otherwise try decode token sub
    let userId = user?.id;
    if (!userId) {
      const raw = localStorage.getItem('token');
      if (raw) {
        let token = raw;
        try { const parsed = JSON.parse(raw); if (typeof parsed === 'string') token = parsed; } catch(e){}
        if (token.startsWith('"') && token.endsWith('"')) token = token.slice(1, -1);
        const payload = decodeJwt(token);
        userId = payload?.sub;
      }
    }

    if (!authenticated || !userId) {
      setError('Você precisa estar logado para gerar sugestões pelas preferências.');
      return;
    }

    setLoading(true);
    try {
      console.log('Fetching suggestion by preferences for userId=', userId);
      const resp = await getSuggestionByPreferences(userId);
      console.log('Suggestion response', resp);
      setResult(resp.data || JSON.stringify(resp));
    } catch (err) {
      console.error('Erro ao buscar sugestão (preferencias):', err);
      setError(err.response?.data || err.message || 'Erro ao buscar sugestão.');
    } finally {
      setLoading(false);
    }
  };

  const fetchByTravelPlan = async () => {
    setError(null);
    setResult('');
    if (!travelPlanId) {
      setError('Informe o ID do Travel Plan.');
      return;
    }
    setLoading(true);
    try {
      const resp = await getSuggestionByTravelPlan(travelPlanId);
      setResult(resp.data || JSON.stringify(resp));
    } catch (err) {
      console.error('Erro ao buscar sugestão (travelPlan):', err);
      setError(err.response?.data || err.message || 'Erro ao buscar sugestão por plano.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="suggestions-page">
      <h2>Sugestão de Roteiros</h2>

      <section className="suggestion-controls">
        <div className="by-pref">
          <h3>Pelo seu perfil</h3>
          <p>Gere uma sugestão com base nas suas preferências.</p>
          <button onClick={fetchByPreferences} disabled={loading}>{loading ? 'Gerando...' : 'Gerar sugestão para mim'}</button>
        </div>

        <div className="by-plan">
          <h3>Pelo Travel Plan</h3>
          <p>Informe o ID do roteiro salvo para gerar uma sugestão adaptada.</p>
          <input type="text" value={travelPlanId} onChange={(e) => setTravelPlanId(e.target.value)} placeholder="UUID do Travel Plan" />
          <button onClick={fetchByTravelPlan} disabled={loading}>{loading ? 'Gerando...' : 'Gerar sugestão por plano'}</button>
        </div>
      </section>

      <section className="suggestion-result">
        {error && <div className="error">{String(error)}</div>}
        {result ? (
          <div className="result-card">
            <pre>{typeof result === 'string' ? result : JSON.stringify(result, null, 2)}</pre>
          </div>
        ) : (
          <p className="hint">Nenhuma sugestão gerada ainda.</p>
        )}
      </section>
    </div>
  );
}
