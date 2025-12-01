import { useState, useEffect } from 'react';
import api, { setAuthToken } from '../api';

export default function useAuth() {
  const [authenticated, setAuthenticated] = useState(false);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const raw = localStorage.getItem('token');
    if (!raw) {
      // delay artificial de 1.5 segundos
      setTimeout(() => setLoading(false), 1500);
      return;
    }

    let token = raw;
    try {
      const parsed = JSON.parse(raw);
      if (typeof parsed === 'string') token = parsed;
    } catch (e) {}

    if (token.startsWith('"') && token.endsWith('"')) {
      token = token.slice(1, -1);
    }

    if (token) {
      api.defaults.headers.common.Authorization = `Bearer ${token}`;
      setAuthenticated(true);

      const payload = decodeJwt(token);
      const userId = payload?.sub;
      if (userId) {
        api.get(`/usuarios/${userId}`)
          .then((response) => {
            setUser(response.data);
            // delay artificial de 1.5 segundos
            setTimeout(() => setLoading(false), 1500);
          })
          .catch(() => {
            setTimeout(() => setLoading(false), 1500);
          });
      } else {
        setTimeout(() => setLoading(false), 1500);
      }
    } else {
      setTimeout(() => setLoading(false), 1500);
    }
  }, []);

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
    } catch {
      return null;
    }
  }

  async function handleLogin(email, password) {
    try {
      const response = await api.post('/login', { email, senha: password });
      const data = response.data || {};
      const token = data.token || data.accessToken || data.jwt || data.tokenValue || data.token_value || data.access_token || null;

      if (!token) throw new Error('Resposta de login inválida: token não encontrado');

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

  const handleRegister = async (name, email, password) => {
    try {
      const response = await api.post('/register', { nome: name, email, senha: password });
      const data = response.data || {};
      const token = data.token || data.accessToken || null;

      if (!token) {
        setAuthenticated(false);
        return data;
      }

      setAuthToken(token);
      setAuthenticated(true);

      return data;
    } catch (err) {
      setAuthToken(null);
      setAuthenticated(false);
      if (err.response && err.response.data) {
        const serverMsg = err.response.data.error || err.response.data.message || JSON.stringify(err.response.data);
        throw new Error(serverMsg);
      }
      throw err;
    }
  };

  return { authenticated, loading, user, handleLogin, handleLogout, handleRegister };
}
