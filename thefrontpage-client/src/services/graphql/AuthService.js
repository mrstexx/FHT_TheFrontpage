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
  startAutoLogoutTimer();
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

const logout = (isAuto) => {
  localStorage.removeItem('auth');
  if (!isAuto) {
    clearTimeout(window.autoLogoutTimer);
  }
  if (isAuto) {
    alert('You have been automatically logged out!');
  }
};

const getCurrentUsername = () => {
  const authUser = localStorage.getItem('auth');
  if (authUser) {
    const authObj = JSON.parse(authUser);
    const expiresAt = new Date(authObj.expiresAt);
    if (expiresAt - Date.now() > 0) {
      return authObj.username;
    }
  }
  return null;
};

const isUserLoggedIn = () => {
  const authUser = localStorage.getItem('auth');
  if (authUser) {
    const expiresAt = new Date(JSON.parse(authUser).expiresAt);
    if (expiresAt - Date.now() > 0) {
      return true;
    }
  }
  return false;
};

const startAutoLogoutTimer = () => {
  const authUser = localStorage.getItem('auth');
  if (authUser) {
    const expiresAt = new Date(JSON.parse(authUser).expiresAt);
    clearTimeout(window.autoLogoutTimer);
    window.autoLogoutTimer = setTimeout(() => {
      logout(true);
    }, new Date(expiresAt) - Date.now());
  }
};

export default {
  login,
  register,
  logout,
  getCurrentUsername,
  isUserLoggedIn,
  startAutoLogoutTimer
};
