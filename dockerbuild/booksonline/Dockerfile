FROM mongo

ENV MONGO_INITDB_ROOT_USERNAME root 
ENV MONGO_INITDB_ROOT_PASSWORD root 
ENV MONGO_INITDB_DATABASE booksonline 

# Create Database

COPY ["./gutenberg.js", "/docker-entrypoint-initdb.d/"] # push forward

