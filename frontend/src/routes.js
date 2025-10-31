import React, { useContext } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

import { Context } from './context/AuthContext';

import Login from './pages/Login/index';
import Dashboard from './pages/Dashboard/index';
import MainLayout from './layouts/MainLayout';
import ItineraryEditor from './pages/ItineraryEditor';
import Maps from './pages/Maps'
import SugestaoRoteiros from './pages/SugestaoRoteiros'
import SugestaoDestino from './pages/SugestaoDestino';
import Settings from './pages/Settings'

function CustomRoute({ isPrivate, element }) {
  const { loading, authenticated } = useContext(Context);

  if (loading) {
    return <h1>Loading...</h1>;
  }

  if (isPrivate && !authenticated) {
    return <Navigate to="/login" replace />;
  }

  return element;
}

export default function AppRoutes() {
  return (
    <Routes>
      <Route element={<MainLayout />} >
        <Route path="/login" element={<CustomRoute element={<Login />} />} />
      </Route>
      <Route exact path="/" element={<Navigate to="/dashboard" />} />
      <Route
          path="/dashboard"
          element={<CustomRoute isPrivate element={<Dashboard />} />}
      />
  <Route path="/itinerary" element={<CustomRoute isPrivate element={<ItineraryEditor />} />} />
  <Route path="/itinerary/:id" element={<CustomRoute isPrivate element={<ItineraryEditor />} />} />
      <Route path="*" element={<h1>404 - Not Found</h1>} />
      <Route path="/maps" element={<CustomRoute isPrivate element={<Maps />} />} />
      <Route path="/sugestoes" element={<CustomRoute isPrivate element={<SugestaoRoteiros />} />} />
      <Route path="/ajustes" element={<CustomRoute isPrivate element={<Settings />} />} />      
      <Route path="/destinos" element={<CustomRoute isPrivate element={<SugestaoDestino />} />} />
    </Routes>
  );
}
