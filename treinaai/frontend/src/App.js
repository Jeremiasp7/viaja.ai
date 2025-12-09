import React, { useState } from 'react';
import Login from './pages/Login';
import Suggestions from './pages/Suggestions';
import Recommendations from './pages/Recommendations';
import Plans from './pages/Plans';
import Resources from './pages/Resources';
import Checklists from './pages/Checklists';

export default function App() {
  const [route, setRoute] = useState('login');
  const [token, setToken] = useState(() => localStorage.getItem('treinaai_token'));

  const handleLogin = (t) => {
    setToken(t);
    localStorage.setItem('treinaai_token', t);
  };

  const logout = () => {
    setToken(null);
    localStorage.removeItem('treinaai_token');
  };

  return (
    <div style={{fontFamily:'Arial, sans-serif', padding:20}}>
      <header style={{display:'flex', gap:10, marginBottom:20}}>
        <button onClick={() => setRoute('login')}>Login</button>
        <button onClick={() => setRoute('suggestions')}>Suggestions</button>
        <button onClick={() => setRoute('recommendations')}>Recommendations</button>
        <button onClick={() => setRoute('plans')}>Plans</button>
        <button onClick={() => setRoute('resources')}>Resources</button>
        <button onClick={() => setRoute('checklists')}>Checklists</button>
        {token ? <button onClick={logout}>Logout</button> : null}
      </header>

      <main>
        {route === 'login' && <Login onLogin={handleLogin} />}
        {route === 'suggestions' && <Suggestions token={token} />}
        {route === 'recommendations' && <Recommendations token={token} />}
        {route === 'plans' && <Plans token={token} />}
        {route === 'resources' && <Resources token={token} />}
        {route === 'checklists' && <Checklists token={token} />}
      </main>
    </div>
  );
}
