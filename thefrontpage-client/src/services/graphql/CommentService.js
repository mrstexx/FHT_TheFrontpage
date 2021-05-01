import axios from 'axios';
import config from '../../config';

const authItem = localStorage.getItem('auth');
const token = authItem ? JSON.parse(authItem).authToken : '';
const reqConfig = {
  headers: {
    Authorization: `Bearer ${token}`
  }
};

const getCommentsByPostId = async (postId) => {
  let res = await axios.post(config.graphql.url, {
    query: `
      query {
        getCommentsByPostId(postId: ${postId}) {
          id
          body
          createdAt
          user {
            username
          }
        }
      }
    `
  });
  const resData = res.data;
  if (resData.errors && resData.errors.length > 0) {
    console.error(resData.errors);
    return [];
  }
  let { getCommentsByPostId } = resData.data;
  return getCommentsByPostId;
};

const createComment = async ({ postId, body }) => {
  let res = await axios.post(
    config.graphql.url,
    {
      query: `
    mutation {
      createComment(input: {
        postId: "${postId}",
        body: "${body}",
      }) {
        id
        body
        createdAt
        user {
          username
        }
      }
    }
    `
    },
    reqConfig
  );
  const resData = res.data;
  if (resData.errors && resData.errors.length > 0) {
    console.error(resData.errors);
    return null;
  }
  let { createComment } = resData.data;
  return createComment;
};

export default { getCommentsByPostId, createComment };
