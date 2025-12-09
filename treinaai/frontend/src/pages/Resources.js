import React, { useState } from 'react';
import API from '../services/api';

export default function Resources({ token }) {
  const [userId, setUserId] = useState('');
  const [planId, setPlanId] = useState('');
  const [items, setItems] = useState([]);
  const [name, setName] = useState('');

  const listByUser = async () => {
    try {
      const res = await API.get(`/resources/user/${userId}`, token);
      setItems(res || []);
    } catch (e) { alert('Erro: ' + e.message); }
  };

  const listByPlan = async () => {
    try {
      const res = await API.get(`/resources/plan/${planId}`, token);
      setItems(res ? [res] : []);
    } catch (e) { alert('Erro: ' + e.message); }
  };

  const create = async () => {
    try {
      const payload = { name, planId: planId || null, userId: userId || null };
      const res = await API.post('/resources', payload, token);
      setItems(prev => [...prev, res]);
      setName('');
    } catch (e) { alert('Erro: ' + e.message); }
  };

  return (
    <div className="card">
      <h3>Resources</h3>
      <div>
        <input placeholder="userId" value={userId} onChange={e=>setUserId(e.target.value)} />
        <button onClick={listByUser}>Listar por usuário</button>
      </div>
      <div>
        <input placeholder="planId" value={planId} onChange={e=>setPlanId(e.target.value)} />
        <button onClick={listByPlan}>Listar por plano</button>
      </div>

      <div style={{marginTop:12}}>
        <input placeholder="Nome do recurso" value={name} onChange={e=>setName(e.target.value)} />
        <button onClick={create}>Criar recurso</button>
      </div>

      <ul>
        {items.map((it, idx) => <li key={idx}>{it.name || JSON.stringify(it)}</li>)}
      </ul>
    </div>
  );
}
