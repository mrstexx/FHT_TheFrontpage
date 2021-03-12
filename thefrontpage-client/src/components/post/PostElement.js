import React from 'react';
import { Grid } from 'semantic-ui-react';

const PostElement = () => {
  return (
    <div
      style={{
        margin: '10px 0px'
      }}
    >
      <Grid>
        <Grid.Row>
          <Grid.Column width={2}>
            <p></p>
          </Grid.Column>
          <Grid.Column width={10}>
            <p></p>
          </Grid.Column>
        </Grid.Row>
      </Grid>
    </div>
  );
};

export default PostElement;
