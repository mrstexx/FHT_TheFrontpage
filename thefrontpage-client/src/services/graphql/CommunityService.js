import axios from 'axios';
import config from '../../config';

const authItem = localStorage.getItem('auth');
const token = authItem ? JSON.parse(authItem).authToken : '';
const reqConfig = {
  headers: {
    Authorization: `Bearer ${token}`
  }
};

const getAllCommunities = async () => {
  let res = await axios.post(config.graphql.url, {
    query: `
      query {
        getAllCommunities {
          id
          name
          description
        }
      }
    `
  });
  const resData = res.data;
  if (resData.errors && resData.errors.length > 0) {
    console.error(resData.errors);
    return [];
  }
  let { getAllCommunities } = resData.data;
  return getAllCommunities;
};

const getCommunityByName = async (name) => {
  let res = await axios.post(config.graphql.url, {
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
  });
  const resData = res.data;
  if (resData.errors && resData.errors.length > 0) {
    console.error(resData.errors);
    return {};
  }
  let { getCommunityByName } = resData.data;
  return getCommunityByName;
};

const createCommunity = async ({ name, description }) => {
  let res = await axios.post(
    config.graphql.url,
    {
      query: `
    mutation {
      createCommunity(input: {
          name: "${name}",
          description: "${description}",
      }) {
          id,
          name,
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
    return null;
  }
  let { createCommunity } = resData.data;
  return createCommunity;
};

export default {
  getAllCommunities,
  getCommunityByName,
  createCommunity
};
