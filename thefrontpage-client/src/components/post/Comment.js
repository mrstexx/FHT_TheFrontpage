import { Link } from '@reach/router';
import React from 'react';
import { Icon } from 'semantic-ui-react';

import './comment.css';

const Comment = () => {
  return (
    <div className="comment-container">
      <div className="comment-header">
        <Icon name="comment" />
        <Link to="/user">admin</Link> at
        <b>14:34</b>
      </div>
      <div className="comment-body">This is a comment lorem ipsum...</div>
    </div>
  );
};

export default Comment;
