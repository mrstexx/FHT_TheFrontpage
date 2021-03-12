import React from 'react';
import { Button, Form, Grid, Header } from 'semantic-ui-react';

const Register = () => {
  return (
    <Grid textAlign="center" style={{ height: '100vh' }} verticalAlign="middle">
      <Grid.Column width={5}>
        <Header as="h2">Create an Account. It&apos;s easy and free.</Header>
        <Form>
          <Form.Field>
            <label htmlFor="username">Username</label>
            <input id="username" placeholder="Enter your username..." />
          </Form.Field>
          <Form.Field>
            <label htmlFor="email">E-Mail</label>
            <input id="email" type="email" placeholder="Enter your email..." />
          </Form.Field>
          <Form.Field>
            <label htmlFor="pwd">Password</label>
            <input
              id="pwd"
              type="password"
              placeholder="Enter your password..."
            />
          </Form.Field>
          <Button type="submit" primary fluid>
            Sign Up
          </Button>
        </Form>
      </Grid.Column>
    </Grid>
  );
};

export default Register;
