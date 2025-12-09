import React, { useState } from 'react';
import API from '../services/api';

export default function Checklists({ token }) {
  const [planId, setPlanId] = useState('');
  const [items, setItems] = useState([]);
  const [text, setText] = useState('');

  const listByPlan = async () => {
    try {
      const res = await API.get(`/checklists/plan/${planId}`, token);
      setItems(res || []);
    } catch (e) { alert('Erro: ' + e.message); }
  };

  const create = async () => {
    try {
      const payload = { planId: planId || null, text };
      const res = await API.post('/checklists', payload, token);
      setItems(prev => [...prev, res]);
      setText('');
    } catch (e) { alert('Erro: ' + e.message); }
  };

  return (
    <div className="card">
      <h3>Checklists</h3>
      <input placeholder="planId" value={planId} onChange={e=>setPlanId(e.target.value)} />
      <button onClick={listByPlan}>Listar por plano</button>

      <div style={{marginTop:12}}>
        <input placeholder="Texto do item" value={text} onChange={e=>setText(e.target.value)} />
        <button onClick={create}>Criar item</button>
      </div>

      <ul>
        {items.map((it, idx) => <li key={idx}>{it.text || JSON.stringify(it)}</li>)}
      </ul>
    </div>
  );
}
