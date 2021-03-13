import { Link } from '@reach/router';
import React from 'react';
import { Button, Grid, Icon, Image } from 'semantic-ui-react';

import './post.css';

const PostElement = () => {
  return (
    <Grid.Row className="post-base">
      <div
        style={{
          margin: '10px 0px',
          width: '100%'
        }}
      >
        <div className="post-grid">
          <div className="post-vote">
            <Button basic icon>
              <Icon name="thumbs up outline" />
            </Button>
            <span className="post-vote-count">10k</span>
            <Button basic icon>
              <Icon name="thumbs down outline" />
            </Button>
          </div>
          <div className="post-body">
            <div className="post-body-header">
              Posted in <Link to="/">c/programming</Link> by{' '}
              <Link to="/">mrstexx</Link> at <b>14:03</b>
            </div>
            <div className="post-body-title">
              What is the best programming language in 2021?
            </div>
            <div className="post-body-image">
              <Image
                src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/Test-Logo.svg/783px-Test-Logo.svg.png"
                wrapped
                rounded
              />
            </div>
            <div className="post-body-footer">
              <Link to="/">
                <Icon name="comments outline" /> 5.4k Comments
              </Link>
            </div>
          </div>
        </div>
      </div>
    </Grid.Row>
  );
};

export default PostElement;
