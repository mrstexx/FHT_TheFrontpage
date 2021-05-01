import { Link } from '@reach/router';
import React from 'react';
import { Icon } from 'semantic-ui-react';

import './comment.css';

const Comment = (props) => {
  const { username, createdAt, body } = props;
  return (
    <div className="comment-container">
      <div className="comment-header">
        <Icon name="comment" />
        <Link to={`/user/${username}`}>{username}</Link> at{' '}
        <b>{new Date(createdAt).toLocaleString()}</b>
      </div>
      <div className="comment-body">{body}</div>
    </div>
  );
};

export default Comment;
