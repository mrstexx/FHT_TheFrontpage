import React from 'react';
import { Grid } from 'semantic-ui-react';
import PostElement from '../post/PostElement';

class FrontPage extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <h2>Latest posts</h2>
        <Grid>
        </Grid>
      </div>
    );
  }
}

export default FrontPage;
