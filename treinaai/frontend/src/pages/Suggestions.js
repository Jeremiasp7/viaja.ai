import React, { useState } from 'react';
import API from '../services/api';

export default function Suggestions({ token }) {
  const [userId, setUserId] = useState('');
  const [prompt, setPrompt] = useState('');
  const [output, setOutput] = useState('');

  const submit = async (e) => {
    e.preventDefault();
    try {
      const body = { userId: userId || null, prompt };
      const res = await API.post('/suggestions/plan', body, token);
      setOutput(typeof res === 'string' ? res : JSON.stringify(res, null, 2));
    } catch (err) {
      setOutput('Erro: ' + err.message);
    }
  };

  return (
    <div className="card">
      <h3>Gerar Sugestão de Plano (LLM)</h3>
      <form onSubmit={submit}>
        <input placeholder="userId (UUID) - opcional" value={userId} onChange={e=>setUserId(e.target.value)} />
        <textarea placeholder="Prompt / contexto" value={prompt} onChange={e=>setPrompt(e.target.value)} rows={4} />
        <button type="submit">Gerar</button>
      </form>
      <pre>{output}</pre>
    </div>
  );
}
