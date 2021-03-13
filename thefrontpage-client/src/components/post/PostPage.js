import { Link } from '@reach/router';
import React from 'react';
import { Button, Divider, Form, Icon, Image } from 'semantic-ui-react';
import Comment from './Comment';

import './post.css';

const PostPage = () => {
  return (
    <div className="post-page-container">
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
          <div className="post-body-description">
            <p>
              Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
              nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam
              erat, sed diam voluptua. At vero eos et accusam et justo duo
              dolores et ea rebum. Stet clita kasd gubergren, no sea takimata
              sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit
              amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor
              invidunt ut labore et dolore magna aliquyam erat, sed diam
              voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
              Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum
              dolor sit amet.
            </p>
          </div>
          <Divider horizontal>Comments</Divider>
          <Form>
            <Form.TextArea
              label="New comment"
              placeholder="Add new comment..."
            />
            <Button type="submit" circular compact color="blue">
              Comment
            </Button>
          </Form>
          <Comment />
          <Comment />
        </div>
      </div>
    </div>
  );
};

export default PostPage;
