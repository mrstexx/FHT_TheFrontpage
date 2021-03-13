import React from 'react';
import { Router } from '@reach/router';
import { Container } from 'semantic-ui-react';

import Header from './components/header/Header';
import FrontPage from './components/frontpage/FrontPage';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import NotFound from './NotFound';
import PostPage from './components/post/PostPage';
import CommunityPage from './components/community/CommunityPage';

import 'semantic-ui-css/semantic.min.css';
import './App.css';

function App() {
  return (
    <div className="App">
      <div
        style={{
          paddingBottom: '70px'
        }}
      >
        <Container fluid>
          <Header />
        </Container>
      </div>
      <Container>
        <Router>
          <FrontPage path="/" />
          <FrontPage path="/feed" />
          <PostPage path="/post" />
          <CommunityPage path="/community" />
          <Login path="/login" />
          <Register path="/signup" />
          <NotFound default />
        </Router>
      </Container>
    </div>
  );
}

export default App;
