## FHT_TheFrontpage - Server-Side Implementation
Server-side implementation is done using Spring Boot library for REST API and GraphQL services.
The application configuration is in `application.yml` file. To start the appliaction in a web server, mailserver(i.e. https://github.com/maildev/maildev) and database server (supported database is H2 -> https://www.h2database.com/html/main.html) must run first.

#### GraphQL Schema
Path to file `./src/main/resources/graphql/schema.graphqls`

#### REST API Endpoints:
GET http://localhost:8080/api/auth/confirm  
POST http://localhost:8080/api/auth/login  
POST http://localhost:8080/api/auth/logout  
POST http://localhost:8080/api/auth/register  
PUT http://localhost:8080/api/comments/  
POST http://localhost:8080/api/comments/  
GET http://localhost:8080/api/comments/{{commentId}}  
DELETE http://localhost:8080/api/comments/{{commentId}}  
GET http://localhost:8080/api/comments/{{commentId}}/author  
GET http://localhost:8080/api/communities/  
PUT http://localhost:8080/api/communities/  
POST http://localhost:8080/api/communities/  
POST http://localhost:8080/api/communities/{{communityName}}/follow  
POST http://localhost:8080/api/communities/{{communityName}}/unfollow  
GET http://localhost:8080/api/communities/{{name}}  
DELETE http://localhost:8080/api/communities/{{name}}  
GET http://localhost:8080/api/communities/{{name}}/author  
GET http://localhost:8080/api/communities/{{name}}/posts  
GET http://localhost:8080/api/communities/{{name}}/users  
GET http://localhost:8080/api/posts/  
PUT http://localhost:8080/api/posts/  
POST http://localhost:8080/api/posts/  
GET http://localhost:8080/api/posts/{{id}}  
DELETE http://localhost:8080/api/posts/{{id}}  
GET http://localhost:8080/api/posts/{{postId}}/author  
GET http://localhost:8080/api/posts/{{postId}}/comments  
POST http://localhost:8080/api/votes/

