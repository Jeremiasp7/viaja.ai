import { useState, useEffect } from 'react';

import api, { setAuthToken } from '../api';

export default function useAuth() {
  const [authenticated, setAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');

    if (token) {
      api.defaults.headers.common.Authorization = `Bearer ${token}`;
      setAuthenticated(true);
    }

    setLoading(false);
  }, []);

  // handleLogin now expects (email, password) and posts to /login
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
  }

  return { authenticated, loading, handleLogin, handleLogout };
}