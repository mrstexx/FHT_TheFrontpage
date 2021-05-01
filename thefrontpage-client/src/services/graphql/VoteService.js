import axios from 'axios';
import config from '../../config';

const authItem = localStorage.getItem('auth');
const token = authItem ? JSON.parse(authItem).authToken : '';
const reqConfig = {
  headers: {
    Authorization: `Bearer ${token}`
  }
};

const vote = async ({ isUpVote, postId }) => {
  let res = await axios.post(
    config.graphql.url,
    {
      query: `
      mutation {
        vote(input: {
          postId: ${postId}
          voteType: ${isUpVote ? 'UP_VOTE' : 'DOWN_VOTE'}
        })
      }
    `
    },
    reqConfig
  );
  const resData = res.data;
  if (resData.errors && resData.errors.length > 0) {
    console.error(resData.errors);
    return false;
  }
  let { vote } = resData.data;
  return vote;
};

export default { vote };
