import React, { createContext } from 'react';
import useAuth from '../hooks/useAuth';

const Context = createContext();

function AuthProvider({ children }) {
  const {
    authenticated, loading, user, userId, handleLogin, handleLogout, handleRegister
  } = useAuth();

  return (
    <Context.Provider value={{ loading, authenticated, user, userId, handleLogin, handleLogout, handleRegister }}>
      {children}
    </Context.Provider>
  );
}

export { Context, AuthProvider };
