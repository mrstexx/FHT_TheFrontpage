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
  let { getAllCommunities } = res.data.data;
  return getAllCommunities;
};

export default {
  getAllCommunities
};
