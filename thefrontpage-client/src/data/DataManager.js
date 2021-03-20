import GraphQL from './graphql/GraphQLManager';
import RestAPI from './api/RestAPIManager';

const isGraphQL = process.env.REACT_APP_API === 'graphql';
let dataSource = null;
if (isGraphQL) {
  dataSource = GraphQL;
} else {
  dataSource = RestAPI;
}

export default dataSource;
