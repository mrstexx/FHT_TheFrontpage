import React from 'react';
import { Grid, Image } from 'semantic-ui-react';

class FrontPage extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Grid>
        <Grid.Column width={11}>
          <Image src="/images/wireframe/paragraph.png" />
        </Grid.Column>
        <Grid.Column width={3}>
          <Image src="/images/wireframe/paragraph.png" />
        </Grid.Column>
      </Grid>
    );
  }
}

export default FrontPage;
