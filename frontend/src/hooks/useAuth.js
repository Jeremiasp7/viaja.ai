import { useState, useEffect } from 'react';

import api, { setAuthToken } from '../api';

export default function useAuth() {
  const [authenticated, setAuthenticated] = useState(false);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const raw = localStorage.getItem('token');
    if (!raw) {
      setLoading(false);
      return;
    }

    // normalize token if it was stored as JSON or with quotes
    let token = raw;
    try {
      const parsed = JSON.parse(raw);
      if (typeof parsed === 'string') token = parsed;
    } catch (e) {
      // ignore
    }
    if (token.startsWith('"') && token.endsWith('"')) {
      token = token.slice(1, -1);
    }

    if (token) {
      api.defaults.headers.common.Authorization = `Bearer ${token}`;
      setAuthenticated(true);

      // try to fetch user profile using token sub
      const payload = decodeJwt(token);
      const userId = payload?.sub;
      if (userId) {
        api.get(`/usuarios/${userId}`)
          .then((resp) => setUser(resp.data))
          .catch((err) => console.warn('Não foi possível buscar perfil do usuário:', err))
          .finally(() => setLoading(false));
      } else {
        setLoading(false);
      }
    } else {
      setLoading(false);
    }
  }, []);

  function decodeJwt(token) {
    try {
      const parts = token.split('.');
      if (parts.length < 2) return null;
      const payload = parts[1];
      // base64url -> base64
      const base64 = payload.replace(/-/g, '+').replace(/_/g, '/');
      const json = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));
      return JSON.parse(json);
    } catch (e) {
      return null;
    }
  }

  async function handleLogin(email, password) {
    try {
      const response = await api.post('/login', { email, senha: password });

      const data = response.data || {};
      const token = data.token || data.accessToken || data.jwt || data.tokenValue || data.token_value || data.access_token || null;

      if (!token) {
        throw new Error('Resposta de login inválida: token não encontrado');
      }

      setAuthToken(token);
      setAuthenticated(true);

      const payload = decodeJwt(token);
      const userId = payload?.sub;
      if (userId) {
        try {
          const userResp = await api.get(`/usuarios/${userId}`);
          setUser(userResp.data);
        } catch (e) {
          console.warn('Could not fetch user profile', e);
        }
      }

      return data;
    } catch (err) {
      if (err.response && err.response.data) {
        const serverMsg = err.response.data.error || err.response.data.message || JSON.stringify(err.response.data);
        throw new Error(serverMsg);
      }
      throw err;
    }
  }

  function handleLogout() {
    setAuthenticated(false);
    setAuthToken(null);
    setUser(null);
  }

  return { authenticated, loading, user, handleLogin, handleLogout };
}