# gutenberg-anaconda
I present here a microservice-oriented application that uses some basic Docker features including docker-compose. It consists of a collection of separate servers all running in Docker containers. MongoDB is used for persistence and also runs in a container.

Here are the prerequisites for running the complete application:

A recent Docker version installed (I used 19.03.11-ce)
A recent Apache Maven version installed (I used 3.6.0)

In addition I used Spring Tool Suite for developing this demo but it is not required for running the application.

Here is the list of all 12 containers:

Server            | Image                     | Port         | Function             | Database connection
---------------   | ------------------------- | ------------ | -------------------- | -------------------
config-server     | gutenberg/config-server   | 8888         | Configuration server | None
eureka-server     | gutenberg/eureka-server   | 8761         | Discovery server     | None
books-mongodb     | mongo                     | 27017        | Schemaless database  |
book-service      | gutenberg/book-server     | 8081         | Book requests        | booksonline
review-service    | gutenberg/review-server   | 8082         | Review requests      | booksonline
order-service     | gutenberg/order-server    | 8083         | Order requests       | booksonline
user-service      | gutenberg/user-server     | 8084         | User requests        | booksonline
gateway-service   | gutenberg/gateway-server  | 5555         | Gateway              | None
frontend-service  | gutenberg/frontend-server | 8080         | frontend             | None

A named volume is used for persistence.

Volume external name  | Server          | Volume type | Source     | Target
--------------------- | --------------- | ----------- | ---------- | -----------------
gutenberg-books-db    | books-mongodb   | bind        | booksdb    | /data/db
 

A gateway is used to hide some Spring servers. Here is the list:

Server           | Port | Gateway URI
---------------- | ---- | -------------------------
book-service     | 8081 | gateway-service:5555/books
review-service   | 8082 | gateway-service:5555/reviews
order-service    | 8083 | gateway-service:5555/orders
user-service     | 8084 | gateway-service:5555/users


Here are the steps to run the application:

# 1. Images creation

Here we use the docker support of spring-boot.

In folder dockerbuild/booksonline run the shell script booksBuild. It will create a local mongo image that contains a Javascript file gutenberg.js.

In folder dockerbuild run the shell script springBuild. It will build all Spring images. 
Note that a test is included in the build process and that the actual build happens only if the test is successful.

```
#!/bin/bash
# file name springBuild
for server in 'config-server' 'book-server' 'review-server' 'order-server' 'user-server' 'gateway-server' 'eureka-server' 'frontend-server';
do 
    echo ${server}
    pwd
    cd booksonline
    pwd
    echo "./bookRestore"
    ./booksRestore
    cd ../../$server
    pwd
    echo "./build"
    ./build
    cd ../dockerbuild

    echo $?
    if [ "$?" -ne 0 ]
    then 
      echo "Build failed for $server"
      exit "$?"
    fi
done;
```

Note that a volume named gutenberg-books-db is created by this script.

Finally in the folder dockerbuild/booksonline run the script `booksKill`. It kill the container but leaves the volume prepopulated.

# 3. Running the application

To start the application go to docker subdirectory and run the command:

```
sudo docker-compose up
```

```
# filename docker-compose.yml
version: '3.4'

services:

  # books-mongodb 
  mongodb-books:
    image: mongo
    # for testing only
    #image: gutenberg/books-mongodb
    volumes:
      - type: volume
        source: booksdb
        target: /data/db 
    # Note mongod not mongo because we start a server not a client
    command: mongod --auth
    ports:
      # Note the syntax host:container
      # Here the container port is mapped onto the host port 28017 to avoid a conflict with a local MongoDB service.
      # If no local MongoDB service is running this trick is not needed. Anyway the mapping is used for debugging only.
      - 28017:27017
    restart: always

 
  # gutenberg-config 
  config-server:
    image:
      gutenberg/config-server
    volumes:
      # edit to match your own filesystem
      - type: bind
        source: /home/dominique/Documents/workspace-anaconda-clean/config-repo
        target: /tmp/config-repo
    ports:
      - 8888:8888
    environment:
      - EUREKASERVER_URI=http://eureka-server:8761/eureka/
      - EUREKASERVER_HOST=eureka-server
      - EUREKASERVER_PORT=8761

  # gutenberg-eureka 
  eureka-server:
    image:
      gutenberg/eureka-server
    ports:
      # host:container
      - 8761:8761

  # book-service
  book-service:
    image: gutenberg/book-server
    depends_on:
      - config-server
      - mongodb-books
    ports:
      - 8081:8081

    environment:
      - CONFIGSERVER_HOST=config-server
      - CONFIGSERVER_PORT=8888
      - GUTENBERG_CONFIG_URI=http://config-server:8888
      - EUREKASERVER_URI=http://eureka-server:8761/eureka/
      - PROFILE=dev

  # review-service
  review-service:
    image: gutenberg/review-server
    depends_on:
      - config-server
      - mongodb-books
    ports:
      # host:container
      - 8082:8082

    environment:
      - CONFIGSERVER_HOST=config-server
      - CONFIGSERVER_PORT=8888
      - GUTENBERG\_CONFIG\_URI=http://config-server:8888
      - BASE\_REVIEWS\_URL=http://gateway-service:5555/reviews
      - EUREKASERVER_URI=http://eureka-server:8761/eureka/
      - PROFILE=dev

  # order-service
  order-service:
    image: gutenberg/order-server
    depends_on:
      - config-server
      - mongodb-books
      - book-service
    ports:
      # host:container
      - 8083:8083

    environment:
      - CONFIGSERVER_HOST=config-server
      - CONFIGSERVER_PORT=8888
      - GUTENBERG\_CONFIG\_URI=http://config-server:8888
      - EUREKASERVER_URI=http://eureka-server:8761/eureka/
      - PROFILE=dev

  # user-service
  user-service:
    image: gutenberg/user-server
    depends_on:
      - config-server
      - mongodb-books
    ports:
      # host:container
      - 8084:8084

    environment:
      - CONFIGSERVER_HOST=config-server
      - CONFIGSERVER_PORT=8888
      - GUTENBERG\_CONFIG_URI=http://config-server:8888
      - BASE\_USERS_URL=http://gateway-service:5555/users
      - EUREKASERVER_URI=http://eureka-server:8761/eureka/
      - PROFILE=dev

  # gateway-service
  gateway-service:
    image: gutenberg/gateway-server
    depends_on:
      - config-server
      - mongodb-books
    ports:
      # host:container
      - 5555:5555

    environment:
      - BOOKSERVER_URI=http://book-service:8081
      - BOOKSERVER_HOST=book-service
      - BOOKSERVER_PORT=8081
      - REVIEWSERVER_URI=http://review-service:8082
      - REVIEWSERVER_HOST=review-service
      - REVIEWSERVER_PORT=8082
      - ORDERSERVER_URI=http://order-service:8083
      - ORDERSERVER_HOST=order-service
      - ORDERSERVER_PORT=8083
      - USERSERVER_URI=http://user-service:8084
      - USERSERVER_HOST=user-service
      - USERSERVER_PORT=8084
      - EUREKASERVER_URI=http://eureka-server:8761/eureka/
      - CONFIGSERVER_HOST=config-server
      - CONFIGSERVER_PORT=8888
      - GUTENBERG\_CONFIG_URI=http://config-server:8888
      - PROFILE=dev

  # frontend-service
  frontend-service:
    image: gutenberg/frontend-server
    depends_on:
      - config-server
      - book-service
      - review-service
      - order-service
      - user-service
      - gateway-service
    ports:
      # host:container
      - 8080:8080
    environment:
      - CONFIGSERVER_HOST=config-server
      - CONFIGSERVER_PORT=8888
      - GATEWAYSERVER_HOST=gateway-service
      - GATEWAYSERVER_PORT=5555
      - GUTENBERG\_CONFIG_URI=http://config-server:8888
      - BASE\_BOOKS_URL=http://gateway-service:5555/books
      - BASE\_REVIEWS_URL=http://gateway-service:5555/reviews
      - BASE\_ORDERS_URL=http://gateway-service:5555/orders
      - BASE\_USERS_URL=http://gateway-service:5555/users
      - EUREKASERVER_URI=http://eureka-server:8761/eureka/
      - PROFILE=dev

volumes:
  booksdb:
    external: 
      name: gutenberg-books-db

```

All running Spring containers can be seen on Eureka port 8761.

The frontend itself is accessed on URL localhost:8080/gutenberg. A username and password are required. Here are the prepopulated users:

Username | Password
-------- | --------- 
Carol    | s1a2t3o4r 
Albert   | a5r6e7p8o
Werner   | t4e3n2e1t
Alice    | o8p7e6r5a
Richard  | r1o2t3a4s
Sator    | sator1234 
Arepo    | arepo1234
Tenet    | tenet1234
Opera    | opera1234
Rotas    | rotas1234


To stop the application run the command in docker subdirectory:

```
sudo docker-compose down
```


# 5. Accessing MongoDB container
To access the MongoDB container run the command:

```
sudo docker exec -it docker\_mongodb-books_1 /bin/bash
```
Then in container shell run the command:

```
mongo -u spring -p password1234 --authenticationDatabase booksonline
```

and then for example to display orders collection:

```
use booksonline
db.orders.find().pretty()
```

Cachan, September 19 2020
 
Dominique Ubersfeld
