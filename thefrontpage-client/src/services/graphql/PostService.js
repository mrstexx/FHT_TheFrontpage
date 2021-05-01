import axios from 'axios';
import config from '../../config';

const authItem = localStorage.getItem('auth');
const token = authItem ? JSON.parse(authItem).authToken : '';
const reqConfig = {
  headers: {
    Authorization: `Bearer ${token}`
  }
};

const getAllPosts = async () => {
  let res = await axios.post(config.graphql.url, {
    query: `
    query {
      getAllPosts {
        id
        title
        url
        createdAt
        user {
          username
        }
        voteCount
        comments {
          id
        }
        community {
          name
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
  let { getAllPosts } = resData.data;
  return getAllPosts;
};

const getPostAndCommentsById = async (postId) => {
  let res = await axios.post(config.graphql.url, {
    query: `
    query {
      getPostById(id: ${postId}) {
        id
        title
        body
        url
        createdAt
        user {
          username
        }
        voteCount
        comments {
          id
          body
          createdAt
          user {
            username
          }
        }
        community {
          name
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
  let { getPostById } = resData.data;
  return getPostById;
};

const createPost = async (req) => {
  let res = await axios.post(
    config.graphql.url,
    {
      query: `
      mutation {
        createPost(input: {
          title: "${req.title}"
          body: "${req.body}"
          url: "${req.url}"
          communityName: "${req.communityName}"
        }) {
          id
          title
          url
          createdAt
          user {
            username
          }
          voteCount
          comments {
            id
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
    return {};
  }
  let { createPost } = resData.data;
  return createPost;
};

export default {
  getAllPosts,
  getPostAndCommentsById,
  createPost
};
