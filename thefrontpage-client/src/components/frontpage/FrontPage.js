import React from 'react';
import { Grid } from 'semantic-ui-react';
import CreatePost from '../post/CreatePost';
import PostElement from '../post/PostElement';

class FrontPage extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Grid>
        <Grid.Row>
          <CreatePost />
        </Grid.Row>
        <PostElement />
        <PostElement />
      </Grid>
    );
  }
}

export default FrontPage;