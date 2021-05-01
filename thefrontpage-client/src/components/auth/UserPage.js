import React from 'react';
import { Icon } from 'semantic-ui-react';

import PostElement from '../post/PostElement';

import './user.css';

const UserPage = (props) => {
  const { username } = props;
  return (
    <div>
      <div className="user-header">
        <Icon name="user circle" /> <b>{username}</b>&apos;s posts
      </div>
      <div className="user-posts">
        <PostElement />
        <PostElement />
      </div>
    </div>
  );
};

export default UserPage;
