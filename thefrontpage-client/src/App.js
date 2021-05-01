import React from 'react';
import { Router } from '@reach/router';
import { Container } from 'semantic-ui-react';

import Header from './components/header/Header';
import FrontPage from './components/frontpage/FrontPage';
import Login from './components/auth/Login';
import ProfilePage from './components/auth/ProfilePage';
import Register from './components/auth/Register';
import UserPage from './components/auth/UserPage';
import NotFound from './NotFound';
import PostPage from './components/post/PostPage';
import CommunityList from './components/community/CommunityList';
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
          <CommunityList path="/community" />
          <CommunityPage path="/community/:communityName" />
          <Login path="/login" />
          <Register path="/signup" />
          <UserPage path="/user/:username" />
          <ProfilePage path="/profile" />
          <NotFound default />
        </Router>
      </Container>
    </div>
  );
}

export default App;
