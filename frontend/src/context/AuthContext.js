import React, { createContext/*, useState, useEffect*/ } from 'react';

import useAuth from '../hooks/useAuth';

const Context = createContext();

function AuthProvider({ children }) {
  const {
    authenticated, loading, user, handleLogin, handleLogout, handleRegister
  } = useAuth();

  return (
    <Context.Provider value={{ loading, authenticated, user, handleLogin, handleLogout, handleRegister }}>
      {children}
    </Context.Provider>
  );
}

export { Context, AuthProvider };
