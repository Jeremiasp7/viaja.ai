import React, { useState } from 'react';
import API from '../services/api';

export default function Plans({ token }) {
  const [userId, setUserId] = useState('');
  const [plans, setPlans] = useState([]);
  const [newTitle, setNewTitle] = useState('');

  const load = async () => {
    try {
      const res = await API.get(`/plans/${userId}`, token);
      setPlans(res);
    } catch (err) { setPlans([]); alert('Erro: '+err.message); }
  };

  const create = async () => {
    try {
      const payload = { title: newTitle };
      await API.post(`/plans/update/${userId}`, payload, token);
      setNewTitle('');
      await load();
    } catch (err) { alert('Erro: '+err.message); }
  };

  return (
    <div className="card">
      <h3>Plans</h3>
      <input placeholder="userId (UUID)" value={userId} onChange={e=>setUserId(e.target.value)} />
      <button onClick={load}>Listar planos</button>

      <div style={{marginTop:12}}>
        <input placeholder="Novo título" value={newTitle} onChange={e=>setNewTitle(e.target.value)} />
        <button onClick={create}>Criar / Atualizar plano</button>
      </div>

      <ul>
        {Array.isArray(plans) && plans.map((p, idx)=> <li key={idx}>{p.title || JSON.stringify(p)}</li>)}
      </ul>
    </div>
  );
}
