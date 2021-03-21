import axios from 'axios';
import config from '../../config';

const URL = config.graphql.url;

const login = async (req) => {
  const res = await axios.post(URL, {
    query: `
        mutation{
          login(input: {
            username: "${req.username}"
            password: "${req.password}"
          }) {
            username
            authToken
            expiresAt
          }
        }
      `
  });
  const resData = res.data;
  if (resData.errors && resData.errors.length > 0) {
    console.error(resData.errors);
    return false;
  }
  let { login } = resData.data;
  localStorage.setItem('auth', JSON.stringify(login));
  return true;
};

const register = async (req) => {
  const res = await axios.post(URL, {
    query: `
        mutation{
          register(input: {
            username: "${req.username}"
            password: "${req.password}"
            email: "${req.email}"
          })
        }
      `
  });
  const resData = res.data;
  if (resData.errors && resData.errors.length > 0) {
    console.error(resData.errors);
    return false;
  }
  return true;
};

const logout = () => {
  localStorage.removeItem('auth');
};

const getCurrentUsername = () => {
  const authUser = localStorage.getItem('auth');
  if (authUser) {
    return JSON.parse(authUser).username;
  }
  return null;
};

const isUserLoggedIn = () => {
  const authUser = localStorage.getItem('auth');
  if (authUser) {
    return true;
  }
  return false;
};

export default {
  login,
  register,
  logout,
  getCurrentUsername,
  isUserLoggedIn
};
