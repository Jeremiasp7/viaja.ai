import React from 'react';
import { Outlet } from 'react-router-dom';


import './index.css';

export default function MainLayout() {

  return (
    <>
      <div className='app-header'></div>
      <div className="main-content">
        <div className='main-content-body'>
          <Outlet />
        </div>
      </div>
    </>
  )
}