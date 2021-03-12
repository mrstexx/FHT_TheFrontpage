import React from 'react';
import { Router } from '@reach/router';
import { Container } from 'semantic-ui-react';

import Header from './components/header/Header';
import FrontPage from './components/frontpage/FrontPage';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import NotFound from './NotFound';

import 'semantic-ui-css/semantic.min.css';
import './App.css';

function App() {
  return (
    <div className="App">
      <Container fluid>
        <Header />
      </Container>
      <Container>
        <Router>
          <FrontPage path="/" />
          <Login path="/login" />
          <Register path="/signup" />
          <NotFound default />
        </Router>
      </Container>
    </div>
  );
}

export default App;
