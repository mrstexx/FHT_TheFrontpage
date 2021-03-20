import axios from 'axios';
import config from '../../config';

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
  if (resData.error && resData.error > 0) {
    console.error(resData.error);
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
  if (resData.error && resData.error > 0) {
    console.error(resData.error);
    return {};
  }
  let { getCommunityByName } = resData.data;
  return getCommunityByName;
};

export default {
  getAllCommunities,
  getCommunityByName
};
