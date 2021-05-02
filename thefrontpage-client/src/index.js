import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { AuthService } from './services/DataService';

window.onload = () => {
  AuthService.startAutoLogoutTimer();
};

ReactDOM.render(<App />, document.getElementById('root'));