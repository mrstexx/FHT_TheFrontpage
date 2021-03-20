const dev = {
  graphql: {
    url: 'http://localhost:8080/graphql'
  },
  rest: {
    url: 'http://localhost:8080/api/'
  }
};

const prod = {
  graphql: {
    url: 'http://localhost:8080/graphql'
  },
  rest: {
    url: 'http://localhost:8080/api/'
  }
};

const config = process.env.REACT_APP_STAGE === 'production' ? prod : dev;

export default config;
