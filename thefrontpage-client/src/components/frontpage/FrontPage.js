import React, { useEffect, useState } from 'react';
import { Grid } from 'semantic-ui-react';
import { PostService } from '../../services/DataService';
import PostElement from '../post/PostElement';

const FrontPage = () => {
  const [posts, setPosts] = useState([]);
  useEffect(() => {
    const fetchData = async () => {
      const data = await PostService.getAllPosts();
      setPosts(data);
    };
    fetchData();
  }, []);
  return (
    <div>
      <h2>Latest posts</h2>
      <Grid>
        {posts &&
          posts
            .sort(
              (first, second) =>
                new Date(second.createdAt) - new Date(first.createdAt)
            )
            .map((post) => (
              <PostElement
                key={post.id}
                {...{
                  ...post,
                  communityName: post.community.name
                }}
              />
            ))}
      </Grid>
    </div>
  );
};

export default FrontPage;
