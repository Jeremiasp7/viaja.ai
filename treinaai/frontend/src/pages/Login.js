import React, { useState } from 'react';
import API from '../services/api';

export default function Login({ onLogin }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [msg, setMsg] = useState('');

  const submit = async (e) => {
    e.preventDefault();
    try {
      const body = { username, password };
      // framework provides /login endpoint
      const data = await API.post('/login', body);
      if (data && data.token) {
        localStorage.setItem('treinaai_token', data.token);
        onLogin(data.token);
        setMsg('Logado com sucesso');
      } else if (typeof data === 'string') {
        // sometimes backend returns plain text
        setMsg(data);
      } else setMsg('Resposta inesperada');
    } catch (err) {
      setMsg('Erro: ' + err.message);
    }
  };

  return (
    <div className="card">
      <h3>Login</h3>
      <form onSubmit={submit}>
        <input placeholder="Usuário" value={username} onChange={e=>setUsername(e.target.value)} />
        <input placeholder="Senha" type="password" value={password} onChange={e=>setPassword(e.target.value)} />
        <button type="submit">Entrar</button>
      </form>
      <div>{msg}</div>
    </div>
  );
}
