conn = new Mongo();

db = conn.getDB("booksonline");

db.auth("root", "root");

db.categories.drop();

db.categories.insert(
{
    "_id" : ObjectId("59fd6b39acc04f10a07d1340"),
    "_class" : "com.dub.spring.domain.DocumentCategory",
    "slug" : "books",
    "name" : "Books",
    "description" : "All books",
    "children" : [
        ObjectId("59fd6b39acc04f10a07d1341"),
        ObjectId("59fd6b39acc04f10a07d1342"),
        ObjectId("59fd6b39acc04f10a07d1344")
    ],
    "ancestors" : [ ]
});


db.createUser(
{
    user: "spring",
    pwd: "password1234",
    roles: 
    [ 
	{ role: "readWrite", db: "booksonline" }
    ]
}
{
    w: "majority",
    wtimeout: 5000
}

);
