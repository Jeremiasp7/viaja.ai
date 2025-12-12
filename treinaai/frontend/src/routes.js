import React, { useContext } from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import { Context } from "./context/AuthContext";

import Login from "./pages/Login/index";
import Register from "./pages/Register/index";
import Dashboard from "./pages/Dashboard/index";
import Loading from "./pages/Loading/index";
import MainLayout from "./layouts/MainLayout";

export default function AppRoutes() {
  const { loading, authenticated } = useContext(Context);

  if (loading) {
    return <Loading />;
  }

  return (
    <Routes>
      <Route exact path="/" element={<Navigate to={authenticated ? "/dashboard" : "/login"} replace />} />
      <Route element={<MainLayout />}>
        <Route path="/login" element={!authenticated ? <Login /> : <Navigate to="/dashboard" replace />} />
        <Route path="/register" element={!authenticated ? <Register /> : <Navigate to="/dashboard" replace />} />
      </Route>
      <Route
        path="/dashboard"
        element={authenticated ? <Dashboard /> : <Navigate to="/login" replace />}
      />
      <Route path="*" element={<h1>404 - Not Found</h1>} />
    </Routes>
  );
}
