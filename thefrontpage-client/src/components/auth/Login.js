import { Link } from '@reach/router';
import React from 'react';
import { Button, Form, Grid, Header } from 'semantic-ui-react';

const Login = () => {
  return (
    <Grid textAlign="center" style={{ height: '100vh' }} verticalAlign="middle">
      <Grid.Column width={5}>
        <Header as="h2">Welcome to TheFrontpage</Header>
        <Form>
          <Form.Field>
            <label htmlFor="username">Username</label>
            <input id="username" placeholder="Enter your username..." />
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
            Login
          </Button>
          <div className="ui divider"></div>
          <Link to="/signup" textAlign="right">
            Sign Up <i className="arrow right icon"></i>
          </Link>
        </Form>
      </Grid.Column>
    </Grid>
  );
};

export default Login;
