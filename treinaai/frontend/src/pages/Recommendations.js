import React, { useState } from 'react';
import API from '../services/api';

export default function Recommendations({ token }) {
  const [planId, setPlanId] = useState('');
  const [context, setContext] = useState('');
  const [count, setCount] = useState(5);
  const [items, setItems] = useState([]);

  const submit = async (e) => {
    e.preventDefault();
    try {
      const body = { planId: planId || null, context: context || null, count };
      const res = await API.post('/recommendations/object', body, token);
      setItems(res.items || res);
    } catch (err) {
      setItems(['Erro: ' + err.message]);
    }
  };

  return (
    <div className="card">
      <h3>Recomendar Objetos</h3>
      <form onSubmit={submit}>
        <input placeholder="planId (UUID) opcional" value={planId} onChange={e=>setPlanId(e.target.value)} />
        <input placeholder="contexto" value={context} onChange={e=>setContext(e.target.value)} />
        <input type="number" value={count} onChange={e=>setCount(Number(e.target.value))} />
        <button type="submit">Gerar</button>
      </form>
      <ul>
        {items.map((it, idx) => <li key={idx}>{it}</li>)}
      </ul>
    </div>
  );
}
