# rate limit modak by nescool101@gmail.com

PREREQUISITES
java 17
docker
    running container for redis

Step 1.
We need to run redis first, here are the redis commands
docker pull redis
docker run --name my-redis -p 6379:6379 -d redis
docker run --name my-redis -p 6379:6379 -d redis redis-server --requirepass nescaoPassword123

Step 2.
send grid credentials for smtp service
add your credentials to send your mail, also change the sender mail

also to see the swagger>
http://localhost:8080/swagger-ui/index.html


