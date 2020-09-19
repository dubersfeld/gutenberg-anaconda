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

db.categories.insert(
{
    "_id" : ObjectId("59fd6b39acc04f10a07d1341"),
    "_class" : "com.dub.spring.domain.DocumentCategory",
    "slug" : "textbooks",
    "name" : "Textbooks",
    "description" : "Textbooks for professionals",
    "parentId" : ObjectId("59fd6b39acc04f10a07d1340"),
    "children" : [
        ObjectId("59fd6b39acc04f10a07d1343")
    ],
    "ancestors" : [
        {
            "name" : "Books",
            "_id" : ObjectId("59fd6b39acc04f10a07d1340"),
            "slug" : "books"
        }
    ]
});

db.categories.insert(
{
    "_id" : ObjectId("59fd6b39acc04f10a07d1342"),
    "_class" : "com.dub.spring.domain.DocumentCategory",
    "slug" : "biographies",
    "name" : "Biographies",
    "description" : "All about the life of famous people",
    "parentId" : ObjectId("59fd6b39acc04f10a07d1340"),
    "children" : [ ],
    "ancestors" : [
        {
            "name" : "Books",
            "_id" : ObjectId("59fd6b39acc04f10a07d1340"),
            "slug" : "books"
        }
    ]
});

db.categories.insert(
{
    "_id" : ObjectId("59fd6b39acc04f10a07d1343"),
    "_class" : "com.dub.spring.domain.DocumentCategory",
    "slug" : "computer-science",
    "name" : "Computer science",
    "description" : "Latest trends in computer science",
    "parentId" : ObjectId("59fd6b39acc04f10a07d1341"),
    "children" : [ ],
    "ancestors" : [
        {
            "name" : "Textbooks",
            "_id" : ObjectId("59fd6b39acc04f10a07d1341"),
            "slug" : "textbooks"
        },
        {
            "name" : "Books",
            "_id" : ObjectId("59fd6b39acc04f10a07d1340"),
            "slug" : "books"
        }
    ]
});

db.categories.insert(
{
    "_id" : ObjectId("59fd6b39acc04f10a07d1344"),
    "_class" : "com.dub.spring.domain.DocumentCategory",
    "slug" : "fiction",
    "name" : "Fiction",
    "description" : "Most popular novels.",
    "parentId" : ObjectId("59fd6b39acc04f10a07d1340"),
    "children" : [ ],
    "ancestors" : [
        {
            "name" : "Books",
            "_id" : ObjectId("59fd6b39acc04f10a07d1340"),
            "slug" : "books"
        }
    ]
});





db.createUser(
{
    user: "spring",
    pwd: "password1234",
    roles: 
    [ 
	{ role: "readWrite", db: "booksonline" }
    ]
},
{
    w: "majority",
    wtimeout: 5000
});



