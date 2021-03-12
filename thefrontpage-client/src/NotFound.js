import React from 'react';
import { Grid, Header } from 'semantic-ui-react';

const NotFound = () => {
  return (
    <Grid textAlign="center" style={{ height: '100vh' }} verticalAlign="middle">
      <Grid.Column width={5}>
        <Header as="h2">#404. The page could not be found.</Header>
      </Grid.Column>
    </Grid>
  );
};

export default NotFound;
