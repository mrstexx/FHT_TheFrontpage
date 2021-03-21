import { Link, navigate } from '@reach/router';
import React, { useState } from 'react';
import { Button, Form, Grid, Header } from 'semantic-ui-react';

import { AuthService } from '../../services/DataService';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const onUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const onPasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const onLogin = async () => {
    const res = await AuthService.login({
      username,
      password
    });
    if (res) {
      navigate('/');
      window.location.reload();
    } else {
      alert('Login failed. Please try again');
    }
  };

  return (
    <Grid textAlign="center" style={{ height: '100vh' }} verticalAlign="middle">
      <Grid.Column width={6}>
        <Header as="h2">Welcome to TheFrontpage</Header>
        <Form>
          <Form.Field>
            <label htmlFor="username">Username</label>
            <input
              id="username"
              placeholder="Enter your username..."
              onChange={onUsernameChange}
            />
          </Form.Field>
          <Form.Field>
            <label htmlFor="pwd">Password</label>
            <input
              id="pwd"
              type="password"
              placeholder="Enter your password..."
              onChange={onPasswordChange}
            />
          </Form.Field>
          <Button type="submit" onClick={onLogin} primary fluid>
            Login
          </Button>
          <div className="ui divider"></div>
          Don&apos;t have an account?{' '}
          <Link to="/signup">
            Sign Up <i className="arrow right icon"></i>
          </Link>
        </Form>
      </Grid.Column>
    </Grid>
  );
};

export default Login;
