const API = {
  post: async (path, body, token) => {
    const finalToken = token || localStorage.getItem('treinaai_token');
    const prefix = path.startsWith('/api') ? '' : '/api';
    const res = await fetch(prefix + path, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        ...(finalToken ? { Authorization: 'Bearer ' + finalToken } : {})
      },
      body: JSON.stringify(body)
    });
    if (!res.ok) throw new Error('Request failed: ' + res.status);
    const text = await res.text();
    try {
      return JSON.parse(text);
    } catch (e) {
      return text;
    }
  }
  ,
  get: async (path, token) => {
    const finalToken = token || localStorage.getItem('treinaai_token');
    const prefix = path.startsWith('/api') ? '' : '/api';
    const res = await fetch(prefix + path, {
      method: 'GET',
      headers: {
        ...(finalToken ? { Authorization: 'Bearer ' + finalToken } : {})
      }
    });
    if (!res.ok) throw new Error('Request failed: ' + res.status);
    const text = await res.text();
    try { return JSON.parse(text); } catch (e) { return text; }
  },
  put: async (path, body, token) => {
    const finalToken = token || localStorage.getItem('treinaai_token');
    const prefix = path.startsWith('/api') ? '' : '/api';
    const res = await fetch(prefix + path, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json', ...(finalToken ? { Authorization: 'Bearer ' + finalToken } : {}) },
      body: JSON.stringify(body)
    });
    if (!res.ok) throw new Error('Request failed: ' + res.status);
    const text = await res.text();
    try { return JSON.parse(text); } catch (e) { return text; }
  },
  delete: async (path, token) => {
    const finalToken = token || localStorage.getItem('treinaai_token');
    const prefix = path.startsWith('/api') ? '' : '/api';
    const res = await fetch(prefix + path, { method: 'DELETE', headers: { ...(finalToken ? { Authorization: 'Bearer ' + finalToken } : {}) } });
    if (!res.ok) throw new Error('Request failed: ' + res.status);
    return;
  }
};

export default API;

