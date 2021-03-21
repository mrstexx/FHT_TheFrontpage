import { navigate } from '@reach/router';
import React, { useState } from 'react';
import { Button, Form, Grid, Header } from 'semantic-ui-react';

import { AuthService } from '../../services/DataService';

const Register = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');

  const onUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const onPasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const onEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const onRegister = async () => {
    const res = await AuthService.register({
      username,
      password,
      email
    });
    if (res) {
      navigate('/login');
    } else {
      alert('Registration failed. Please try again');
    }
  };
  return (
    <Grid textAlign="center" style={{ height: '100vh' }} verticalAlign="middle">
      <Grid.Column width={6}>
        <Header as="h2">Create an Account. It&apos;s easy and free.</Header>
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
            <label htmlFor="email">E-Mail</label>
            <input
              id="email"
              type="email"
              placeholder="Enter your email..."
              onChange={onEmailChange}
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
          <Button type="submit" primary fluid onClick={onRegister}>
            Sign Up
          </Button>
        </Form>
      </Grid.Column>
    </Grid>
  );
};

export default Register;
