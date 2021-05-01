import { Link } from '@reach/router';
import { isEmpty } from 'lodash';
import React from 'react';
import { Button, Grid, Icon, Image } from 'semantic-ui-react';

import './post.css';

const PostElement = (props) => {
  const {
    id,
    title,
    url,
    createdAt,
    voteCount,
    user,
    comments,
    communityName
  } = props;
  const usrName = user ? user.username : 'undefined';
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
            <span className="post-vote-count">{voteCount || 0}</span>
            <Button basic icon>
              <Icon name="thumbs down outline" />
            </Button>
          </div>
          <div className="post-body">
            <div className="post-body-header">
              Posted in{' '}
              <Link to={`/community/${communityName}`}>c/{communityName}</Link>{' '}
              by <Link to={`/user/${usrName}`}>{usrName}</Link> at{' '}
              <b>{new Date(createdAt).toLocaleString()}</b>
            </div>
            <div className="post-body-title">{title}</div>
            <div className="post-body-image">
              {isEmpty(url) !== false && <Image src={url} wrapped rounded />}
            </div>
            <div className="post-body-footer">
              <Link to={`/post/${id}`}>
                <Icon name="comments outline" />{' '}
                {comments ? comments.length : 0} Comments
              </Link>
            </div>
          </div>
        </div>
      </div>
    </Grid.Row>
  );
};

export default PostElement;
