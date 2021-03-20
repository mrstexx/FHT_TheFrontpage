import axios from 'axios';
import config from '../../config';

const token = '';
const reqConfig = {
  headers: {
    Authorization: `Bearer ${token}`
  }
};

const getAllCommunities = async () => {
  let res = await axios.post(
    config.graphql.url,
    {
      query: `
      query {
        getAllCommunities {
          id
          name
          description
        }
      }
    `
    },
    reqConfig
  );
  const resData = res.data;
  if (resData.errors && resData.errors.length > 0) {
    console.error(resData.errors);
    return [];
  }
  let { getAllCommunities } = resData.data;
  return getAllCommunities;
};

const getCommunityByName = async (name) => {
  let res = await axios.post(
    config.graphql.url,
    {
      query: `
      query {
        getCommunityByName(name: "${name}") {
          id
          name
          description
          createdAt
          createdBy {
            username
          }
          members {
            username
          }
          posts {
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
  let { getCommunityByName } = resData.data;
  return getCommunityByName;
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
  getAllCommunities,
  getCommunityByName,
  createPost
};
