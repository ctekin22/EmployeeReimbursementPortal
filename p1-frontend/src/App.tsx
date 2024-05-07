import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Register } from './components/Login/Register'
import { Login } from './components/Login/Login';
import { Submit } from './components/Submit/Submit';
import { Collection } from './components/Collection/Collection';
import { state } from './globalData/store';
import { UpdateManager } from './components/Update/UpdateManager';
import { ManagerPortal } from './components/Login/ManagerPortal';
import { Employees } from './components/Login/Employees';
import { UpdateEmployee } from './components/Update/UpdateEmployee';


/**
 * The main component of the application.
 * It handles routing and renders different components based on the URL path.
 */
function App() {
  return (
    <div className="App">
      {/* BrowserRouter provides routing functionality to the app */}
      <BrowserRouter>
      {/* Routes component defines all the possible routes of the app */}
          <Routes>
             {/* Route for the login page, rendered when no path is specified */}
            <Route path="" element={<Login/>}/> 
            <Route path="/submit" element={<Submit/>}/> 
            <Route path="/register" element={<Register/>}/>
            <Route path="/collection" element={<Collection/>}/>
            {/* Route for updating manager information */}
            <Route path="/update-manager" element={<UpdateManager/>} />
            {/* Route for updating employee information */}
            <Route path="/update-employee" element={<UpdateEmployee/>} />
            <Route path="/manager-portal" element={<ManagerPortal/>} />
            <Route path="/employees" element={<Employees/>} />
          </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;