import { Link } from '@reach/router';
import React, { useEffect, useState } from 'react';
import { Button, Divider, Grid, Icon, Label } from 'semantic-ui-react';
import {
  CommunityService,
  PostService,
  AuthService
} from '../../services/DataService';
import CreatePost from '../post/CreatePost';
import PostElement from '../post/PostElement';

import './community.css';

const isCurrentUserMember = (members) => {
  if (!members) {
    return false;
  }
  const user = members.find(
    (member) => AuthService.getCurrentUsername() === member.username
  );
  if (user) {
    return true;
  }
  return false;
};

const initState = {
  createdBy: {},
  members: []
};

const CommunityPage = (props) => {
  const { communityName } = props;
  const [community, setCommunity] = useState(initState);
  useEffect(() => {
    const fetchData = async () => {
      const data = await CommunityService.getCommunityByName(communityName);
      setCommunity(data);
    };
    fetchData();
  }, [communityName]);

  const onCreateNewPost = async (reqData) => {
    if (!reqData.title) {
      alert('Field "title" is required to create a new community');
      return;
    }
    if (!AuthService.isUserLoggedIn()) {
      alert('You must be logged in to create a community');
      return;
    }
    const res = await PostService.createPost({
      ...reqData,
      communityName
    });
    const { posts } = community;
    if (posts) {
      posts.push(res);
      setCommunity({
        ...community
      });
    }
  };

  const handleFollow = async (isFollow) => {
    if (!AuthService.isUserLoggedIn()) {
      alert('You must be logged in to create a community');
      return;
    }
    const currentUsername = AuthService.getCurrentUsername();
    if (isFollow) {
      let res = await CommunityService.follow(community.name);
      if (res) {
        setCommunity({
          ...community,
          members: [...community.members, { username: currentUsername }]
        });
      }
    } else {
      let res = await CommunityService.unfollow(community.name);
      if (res) {
        const { members } = community;
        const newMembers = members.filter(
          (member) => member.username !== currentUsername
        );
        setCommunity({
          ...community,
          members: newMembers
        });
      }
    }
  };

  const isMember = isCurrentUserMember(community.members);
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
        <Button
          icon
          size="mini"
          color={isMember ? 'grey' : 'green'}
          onClick={() => {
            handleFollow(!isMember);
          }}
        >
          <Icon name={isMember ? 'remove' : 'add'} />{' '}
          {isMember ? 'Leave' : 'Join'}
        </Button>
        - created by{' '}
        <Link to={`/user/${community.createdBy.username}`}>
          {community.createdBy.username}
        </Link>{' '}
        at {new Date(community.createdAt).toLocaleString()}
      </span>
      <Divider />
      <Grid>
        {AuthService.isUserLoggedIn() === true && (
          <Grid.Row>
            <CreatePost
              {...{
                onCreateNewPost: onCreateNewPost
              }}
            />
          </Grid.Row>
        )}
        {community.posts &&
          community.posts
            .sort(
              (first, second) =>
                new Date(second.createdAt) - new Date(first.createdAt)
            )
            .map((post) => (
              <PostElement
                key={post.id}
                {...{
                  ...post,
                  communityName,
                  onCreateNewPost
                }}
              />
            ))}
      </Grid>
    </div>
  );
};

export default CommunityPage;
