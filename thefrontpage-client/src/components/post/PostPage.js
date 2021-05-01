import { Link } from '@reach/router';
import parse from 'html-react-parser';
import React, { useEffect, useState } from 'react';
import {
  Button,
  Divider,
  Form,
  Icon,
  Image,
  TextArea
} from 'semantic-ui-react';
import Comment from './Comment';
import {
  PostService,
  CommentService,
  AuthService
} from '../../services/DataService';

import './post.css';
import { isEmpty, isNumber } from 'lodash';

const PostPage = (props) => {
  const { postId } = props;
  const [post, setPost] = useState({});
  const [commentBody, setCommentBody] = useState('');
  useEffect(() => {
    const fetchData = async () => {
      const data = await PostService.getPostAndCommentsById(postId);
      setPost(data);
    };
    fetchData();
  }, [postId]);

  const handleCreateComment = async ({ postId, body }) => {
    if (isEmpty(body)) {
      alert('Comment field cannot be empty');
      return;
    }
    if (isEmpty(postId)) {
      alert('Something went wrong, unable to create a comment');
      return;
    }
    if (!AuthService.isUserLoggedIn()) {
      alert('You must be logged in to create a comment');
      return;
    }
    const res = await CommentService.createComment({
      postId,
      body
    });
    if (res !== null) {
      const { comments } = post;
      setPost({
        ...post,
        comments: [...comments, res]
      });
    }
  };

  const username = post.user ? post.user.username : 'undefined';
  const communityName = post.community ? post.community.name : 'undefined';
  const { comments = [] } = post;
  return (
    <div className="post-page-container">
      <div className="post-grid">
        <div className="post-vote">
          <Button basic icon>
            <Icon name="thumbs up outline" />
          </Button>
          <span className="post-vote-count">{post.voteCount || 0}</span>
          <Button basic icon>
            <Icon name="thumbs down outline" />
          </Button>
        </div>
        <div className="post-body">
          <div className="post-body-header">
            Posted in{' '}
            <Link to={`/community/${communityName}`}>c/{communityName}</Link> by{' '}
            <Link to={`/user/${username}`}>{username}</Link> at{' '}
            <b>{new Date(post.createdAt).toLocaleString()}</b>
          </div>
          <div className="post-body-title">{post.title}</div>
          {isEmpty(post.url) !== false && (
            <div className="post-body-image">
              <Image src={post.url} wrapped rounded />
            </div>
          )}
          <div className="post-body-description">
            {parse(String(post.body))}
          </div>
          <Divider horizontal>Comments</Divider>
          <Form>
            <Form.Group>
              <TextArea
                label="New comment"
                placeholder="Add new comment..."
                onChange={(_event, { value }) => {
                  setCommentBody(value);
                }}
              />
            </Form.Group>
            <Button
              type="submit"
              onClick={() => {
                handleCreateComment({ postId, body: commentBody });
              }}
              circular
              compact
              color="blue"
            >
              Comment
            </Button>
          </Form>
          {comments.map((comment) => (
            <Comment
              key={comment.id}
              {...{
                username: comment.user.username,
                createdAt: comment.createdAt,
                body: comment.body
              }}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default PostPage;
