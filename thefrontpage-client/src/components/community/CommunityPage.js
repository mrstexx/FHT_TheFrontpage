import { Link } from '@reach/router';
import React, { useEffect, useState } from 'react';
import { Divider, Grid, Icon, Label } from 'semantic-ui-react';
import DataManager from '../../data/DataManager';
import CreatePost from '../post/CreatePost';
import PostElement from '../post/PostElement';

import './community.css';

const initState = {
  createdBy: {},
  members: []
};

const CommunityPage = (props) => {
  const { communityName } = props;
  const [community, setCommunity] = useState(initState);
  useEffect(() => {
    const fetchData = async () => {
      const data = await DataManager.getCommunityByName(communityName);
      setCommunity(data);
    };
    fetchData();
  }, [communityName]);
  return (
    <div>
      <h2>@{community.name}</h2>
      <p>{community.description}</p>
      <span>
        Active Members{' '}
        {community.members && (
          <Label>
            <Icon name="users" />
            {community.members.length}
          </Label>
        )}
         - created by <Link to="/">{community.createdBy.username}</Link>
      </span>
      <Divider />
      <Grid>
        <Grid.Row>
          <CreatePost />
        </Grid.Row>
        {community.posts &&
          community.posts.map((post) => (
            <PostElement
              key={post.id}
              {...{
                ...post,
                communityName
              }}
            />
          ))}
      </Grid>
    </div>
  );
};

export default CommunityPage;
