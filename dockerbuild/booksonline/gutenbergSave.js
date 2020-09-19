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



db.books.drop();

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e97409f"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "mess-harefaq-1542",
    "title" : "Messaging with HareFAQ",
    "publisher" : "Gutenberg",
    "authors" : [
        "Paul Bunyan"
    ],
    "description" : "A new enterprise messaging implementation.",
    "price" : 2339,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1343"),
    "tags" : [
        "java",
        "spring",
        "messaging"
    ],
    "totalReviews" : 1,
    "ratings" : [
        5
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740a0"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "malware-begin-666",
    "title" : "Malware for Beginners",
    "publisher" : "O'Rourke",
    "authors" : [
        "Marc Dutroux",
        "George Besse"
    ],
    "description" : "How to crash your entreprise servers.",
    "price" : 3339,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1343"),
    "tags" : [
        "malware",
        "system",
        "blackhat"
    ],
    "totalReviews" : 1,
    "ratings" : [
        4
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740a1"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "emerald-ultimate-421",
    "title" : "The Ultimate Emerald Reference",
    "publisher" : "O'Rourke",
    "authors" : [
        "Nivü Nikkonü"
    ],
    "description" : "Much easier to master and more efficient than Ruby.",
    "price" : 3539,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1343"),
    "tags" : [
        "software",
        "web",
        "database"
    ],
    "totalReviews" : 1,
    "ratings" : [
        5
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740a2"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "donkey-action-1234",
    "title" : "Donkey in Action",
    "publisher" : "Chebyshev",
    "authors" : [
        "Grace Deveneux"
    ],
    "description" : "An introduction to the newest microservice framework.",
    "price" : 3839,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1343"),
    "tags" : [
        "microservice",
        "web",
        "restful"
    ],
    "totalReviews" : 1,
    "ratings" : [
        4
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740a3"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "tangodb-depth-5678",
    "title" : "TangoDB in Depth",
    "publisher" : "Notch",
    "authors" : [
        "Norman Bates"
    ],
    "description" : "A comprehensive introduction to the latest schemaless database.",
    "price" : 4339,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1343"),
    "tags" : [
        "database",
        "schemaless",
        "nosql"
    ],
    "totalReviews" : 1,
    "ratings" : [
        5
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740a4"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "jvonneumann-1945",
    "title" : "The Incredible Life of John von Neumann",
    "publisher" : "Grouble",
    "authors" : [
        "Albert Schweitzer"
    ],
    "description" : "A founding father of computer science.",
    "price" : 4439,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1342"),
    "tags" : [
        "computer",
        "system",
        "mathematics"
    ],
    "totalReviews" : 1,
    "ratings" : [
        3
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740a5"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "heisenberg-1923",
    "title" : "Heisenberg, a Life in Uncertainty",
    "publisher" : "Grouble",
    "authors" : [
        "Isabel Spengler"
    ],
    "description" : "A founding father of quantum physics. His entire life he had to cope with uncertainty and most probably was not awarded the Nobel prize.",
    "price" : 4539,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1342"),
    "tags" : [
        "biography",
        "science",
        "history"
    ],
    "totalReviews" : 3,
    "ratings" : [
        5,
        1,
        2
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740a6"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "jsmouche-1900",
    "title" : "Jean-Sebastien Mouche, from Paris with Love",
    "publisher" : "Grouble",
    "authors" : [
        "André Malraux"
    ],
    "description" : "He created the popular Bateaux-Mouche where visitors from around the world can enjoy a romantic dinner on the river Seine.",
    "price" : 2939,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1342"),
    "tags" : [
        "biography",
        "science",
        "history"
    ],
    "totalReviews" : 1,
    "ratings" : [
        5
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740a7"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "marbront-1902",
    "title" : "Eleanor Brontë and the Blank Page Challenge",
    "publisher" : "Spivakov",
    "authors" : [
        "Hu Xiao-Mei"
    ],
    "description" : "The only Brontë sister who never wrote anything.",
    "price" : 2739,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1342"),
    "tags" : [
        "biography",
        "literature",
        "women"
    ],
    "totalReviews" : 1,
    "ratings" : [
        2
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740a8"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "nostradamus-42",
    "title" : "Nostradamus",
    "publisher" : "Springfield",
    "authors" : [
        "Helmut von Staubsauger"
    ],
    "description" : "Everybody has heard of him, now it's time to read about his true story.",
    "price" : 4439,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1342"),
    "tags" : [
        "biography",
        "literature",
        "medicine"
    ],
    "totalReviews" : 1,
    "ratings" : [
        4
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740a9"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "bourne-shell-1542",
    "title" : "The Bourne Shell Legacy",
    "publisher" : "MacNamara",
    "authors" : [
        "Robert Bedlam"
    ],
    "description" : "A nail-biting thriller featuring JSON Bourne.",
    "price" : 4539,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1344"),
    "tags" : [
        "thriller",
        "crime",
        "spying"
    ],
    "totalReviews" : 1,
    "ratings" : [
        5
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740aa"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "raiders-pattern-3190",
    "title" : "Raiders of the Lost Pattern",
    "publisher" : "Atkinson-Wembley",
    "authors" : [
        "Evert Edepamuur"
    ],
    "description" : "Two geeks on the track of an elusive pattern that escaped the attention of the Gang of Four.",
    "price" : 3639,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1344"),
    "tags" : [
        "thriller",
        "crime",
        "software"
    ],
    "totalReviews" : 1,
    "ratings" : [
        5
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740ab"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "dining-philosophers-1542",
    "title" : "The Dining Philosophers",
    "publisher" : "Dyson",
    "authors" : [
        "Paul Enclume"
    ],
    "description" : "Five philosophers decide to have a dinner together. They have to cope with a lack of forks and knives.",
    "price" : 3839,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1344"),
    "tags" : [
        "home",
        "life",
        "food"
    ],
    "totalReviews" : 1,
    "ratings" : [
        4
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740ac"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "walking-planck-3141",
    "title" : "Walking the Planck Constant",
    "publisher" : "Hanning",
    "authors" : [
        "Laetitia Haddad"
    ],
    "description" : "A Caribbean pirate captain falls into a quantum entanglement. Only the Schroedinger Cat can rescue him. Is he dead or alive?",
    "price" : 5339,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1344"),
    "tags" : [
        "piracy",
        "science-fiction",
        "gold"
    ],
    "totalReviews" : 1,
    "ratings" : [
        5
    ]
});

db.books.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740ad"),
    "_class" : "com.dub.spring.domain.BookDocument",
    "slug" : "apes-wrath-4153",
    "title" : "Apes of Wrath",
    "publisher" : "Butterworth",
    "authors" : [
        "Boris Cyrulnik"
    ],
    "description" : "A gorilla keeper in San Diego Zoo struggles to keep his job during the Great Depression.",
    "price" : 6839,
    "categoryId" : ObjectId("59fd6b39acc04f10a07d1344"),
    "tags" : [
        "apes",
        "life",
        "depression"
    ],
    "totalReviews" : 3,
    "ratings" : [
        4,
        4,
        4
    ]
});




db.reviews.drop()

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740b8"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e97409f"),
    "text" : "The most comprehensive source for everything HareFAQ.",
    "rating" : 5,
    "userId" : ObjectId("5a28f306acc04f7f2e9740b3"),
    "helpfulVotes" : 10,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740b9"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a0"),
    "text" : "Malware for Beginners is a great primer on malware and what you can do to protect yourself and your organisation from it.",
    "rating" : 4,
    "userId" : ObjectId("5a28f32dacc04f7f2e9740b4"),
    "helpfulVotes" : 9,
    "voterIds" : [  ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740ba"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a1"),
    "text" : "Nivü Nikkonü has assembled a comprehensive reference manual for Emerald. It is a treasure map that everyone will want to use.",
    "rating" : 5,
    "userId" : ObjectId("5a28f351acc04f7f2e9740b5"),
    "helpfulVotes" : 8,
    "voterIds" : [ "5a28f306acc04f7f2e9740b3" ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740bb"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a2"),
    "text" : "Donkey is fast becoming THE framework for microservices--This book shows you why and how.",
    "rating" : 4,
    "userId" : ObjectId("5a28f35cacc04f7f2e9740b6"),
    "helpfulVotes" : 7,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740bc"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a3"),
    "text" : "A thorough manual for learning, practicing, and implementing TangoDB.",
    "rating" : 5,
    "userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
    "helpfulVotes" : 6,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740bd"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a4"),
    "text" : "I recommend this book to all history of science majors.",
    "rating" : 3,
    "userId" : ObjectId("5a28f306acc04f7f2e9740b3"),
    "helpfulVotes" : 10,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740be"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a7"),
    "text" : "Thanks to this book I don't need sleeping pills anymore. It is so boring that I fall asleep after reading two pages.",
    "rating" : 2,
    "userId" : ObjectId("5a28f32dacc04f7f2e9740b4"),
    "helpfulVotes" : 9,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740bf"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a8"),
    "text" : "I was surprised to read that Nostradamus was a doctor. Moreover this book gives many interesting details about everyday life in France in XVIth century.",
    "rating" : 4,
    "userId" : ObjectId("5a28f351acc04f7f2e9740b5"),
    "helpfulVotes" : 8,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740c0"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a5"),
    "text" : "This is the long awaited biography of the most controversial scientist of XXth century.",
    "rating" : 5,
    "userId" : ObjectId("5a28f35cacc04f7f2e9740b6"),
    "helpfulVotes" : 7,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740c1"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a5"),
    "text" : "This book left me deeply frustrated. After thoroughly reading it twice, I still don't know which cat breed the Schroedinger Cat was!",
    "rating" : 1,
    "userId" : ObjectId("5a28f351acc04f7f2e9740b5"),
    "helpfulVotes" : 6,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740c2"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a5"),
    "text" : "I was really surprised that Heisenberg chose such a desolate place as Helgoland for his spring break.",
    "rating" : 2,
    "userId" : ObjectId("5a28f32dacc04f7f2e9740b4"),
    "helpfulVotes" : 10,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740c3"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a6"),
    "text" : "I had a dinner in a Bateau-Mouche during my honeymoon in Paris a long time ago. Reading this book made me feel young again!",
    "rating" : 5,
    "userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
    "helpfulVotes" : 9,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740c4"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740a9"),
    "text" : "Bedlam at his best. This is really a page-turner.",
    "rating" : 5,
    "userId" : ObjectId("5a28f306acc04f7f2e9740b3"),
    "helpfulVotes" : 8,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740c5"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740aa"),
    "text" : "An incredible exploration of the effect of virtual reality on human behavior.",
    "rating" : 5,
    "userId" : ObjectId("5a28f32dacc04f7f2e9740b4"),
    "helpfulVotes" : 7,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740c6"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740ab"),
    "text" : "This book shows that philosophy is not an abstract concept. It is the very foundation of social interactions.",
    "rating" : 4,
    "userId" : ObjectId("5a28f351acc04f7f2e9740b5"),
    "helpfulVotes" : 6,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740c7"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740ac"),
    "text" : "Really entertaining book. Now I hope that one of Hollywood Eight will adapt it into a movie!",
    "rating" : 5,
    "userId" : ObjectId("5a28f35cacc04f7f2e9740b6"),
    "helpfulVotes" : 10,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740c8"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740ad"),
    "text" : "Reading this book made me think of some novels by John Steinbeck. Here the gorillas are more human than some human characters!",
    "rating" : 4,
    "userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
    "helpfulVotes" : 9,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740c9"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740ad"),
    "text" : "After reading this book I feel like a privileged citizen!",
    "rating" : 4,
    "userId" : ObjectId("5a28f306acc04f7f2e9740b3"),
    "helpfulVotes" : 9,
    "voterIds" : [ ]
});

db.reviews.insert(
{
    "_id" : ObjectId("5a28f366acc04f7f2e9740ca"),
    "_class" : "com.dub.spring.domain.ReviewDocument",
    "bookId" : ObjectId("5a28f2b0acc04f7f2e9740ad"),
    "text" : "After reading this book I prefer gorillas to my neighbors!",
    "rating" : 4,
    "userId" : ObjectId("5a28f32dacc04f7f2e9740b4"),
    "helpfulVotes" : 9,
    "voterIds" : [ ]
});



db.users.drop();

db.users.insert(
{
    "_id" : ObjectId("5a28f2b0acc04f7f2e9740ae"),
    "_class" : "com.dub.spring.domain.MyUser",
    "username" : "Carol",
    "hashedPassword" : "{bcrypt}$2a$10$uj9mmgShGneS5gXG77TA6eITexGHdF/fOQk/85EFhlcslGzqyyVGm",

    "accountNonExpired" : true,
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "enabled" : true,
    "authorities" : [
		{
			"authority" : "ROLE_USER"
		}
	],
    "addresses" : [
        {
            "street" : "Main Street 2233",
            "city" : "Dallas",
            "zip" : "75215",
            "state" : "TX",
            "country" : "USA"
        }
    ],
    "paymentMethods" : [
        {
            "cardNumber" : "1234567813572468",
            "name" : "Carol Baker"
        }
    ],
    "mainPayMeth" : 0,
    "mainShippingAddress" : 0
});

db.users.insert(
{
    "_id" : ObjectId("5a28f2b2acc04f7f2e9740af"),
    "_class" : "com.dub.spring.domain.MyUser",
    "username" : "Albert",
    "hashedPassword" : "{bcrypt}$2a$10$2amyAvl.aoVidnbd/8AUWOl7LDqkcWHZF/z29WFd6jQ6ZR78sffCi",

    "accountNonExpired" : true,
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "enabled" : true,
    "authorities" : [{"authority"  :  "ROLE_USER"}],
    "addresses" : [
        {
            "street" : "Plumber Street 22",
            "city" : "London",
            "zip" : "WC2N 5DU",
            "state" : "",
            "country" : "UK"
        }
    ],
    "paymentMethods" : [
        {
            "cardNumber" : "1357246813572468",
            "name" : "Albert Degroot"
        }
    ],
    "mainPayMeth" : 0,
    "mainShippingAddress" : 0
});


db.users.insert(
{
    "_id" : ObjectId("5a28f2b2acc04f7f2e9740b0"),
    "_class" : "com.dub.spring.domain.MyUser",
    "username" : "Werner",
    "hashedPassword" : "{bcrypt}$2a$10$4D/68H.9Yesn32FldAQs2OVpF2AFsjosjxIiNx0hDX./3FOvYmZGa",

    "accountNonExpired" : true,
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "enabled" : true,
    "authorities" : [{"authority"  :  "ROLE_USER"}],
    "addresses" : [
        {
            "street" : "Hannoverstr. 22",
            "city" : "Berlin",
            "zip" : "10315",
            "state" : "",
            "country" : "DE"
        }
    ],
    "paymentMethods" : [
        {
            "cardNumber" : "4321987643219876",
            "name" : "Werner Stolz"
        }
    ],
    "mainPayMeth" : 0,
    "mainShippingAddress" : 0
});

db.users.insert(
{
    "_id" : ObjectId("5a28f2b9acc04f7f2e9740b1"),
    "_class" : "com.dub.spring.domain.MyUser",
    "username" : "Alice",
    "hashedPassword" : "{bcrypt}$2a$10$Ip8KBSorI9R39m.KQBk3nu/WhjekgPSmfmpnmnf5yCL3aL9y.ITVW",

    "accountNonExpired" : true,
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "enabled" : true,
    "authorities" : [
        {"authority"  :  "ROLE_USER"}],
    "addresses" : [
        {
            "street" : "42 rue Amélie Poulain",
            "city" : "Paris",
            "zip" : "75018",
            "state" : "",
            "country" : "FR"
        },
        {
            "street" : "42 rue Amélie Nothomb",
            "city" : "Paris",
            "zip" : "75018",
            "state" : "",
            "country" : "FR"
        }
    ],
    "paymentMethods" : [
        {
            "cardNumber" : "6789432167894321",
            "name" : "Alice Carrol"
        },
        {
            "cardNumber" : "6789432167891234",
            "name" : "Alice Carrol"
        }
    ],
    "mainPayMeth" : 0,
    "mainShippingAddress" : 0
});


db.users.insert(
{
    "_id" : ObjectId("5a28f2ddacc04f7f2e9740b2"),
    "_class" : "com.dub.spring.domain.MyUser",
    "username" : "Richard",
    "hashedPassword" : "{bcrypt}$2a$10$LBMYa2KlGqKyjUg6UPSx8.SqV99/mWTryFwl1sY4.x0UKKqnab9ru",

    "accountNonExpired" : true,
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "enabled" : true,
    "authorities" : [{"authority"  :  "ROLE_USER"}],
    "addresses" : [
        {
            "street" : "Avenue de la Gare 55",
            "city" : "Lausanne",
            "zip" : "1022",
            "state" : "",
            "country" : "CH"
        }
    ],
    "paymentMethods" : [
        {
            "cardNumber" : "9876123498761234",
            "name" : "Richard Brunner"
        },
        {
            "cardNumber" : "9876123498761235",
            "name" : "Richard Brenner"
        }
    ],
    "mainPayMeth" : 0,
    "mainShippingAddress" : 0
});


db.users.insert(
{
    "_id" : ObjectId("5a28f306acc04f7f2e9740b3"),
    "_class" : "com.dub.spring.domain.MyUser",
    "username" : "Sator",
    "hashedPassword" : "{bcrypt}$2a$10$bMiqHtOacryfa90U9ddokelGe3xlEmVVuZ1UDre3ArINmspjjsIGC",

    "accountNonExpired" : true,
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "enabled" : true,
    "authorities" : [{"authority"  :  "ROLE_USER"}],
    "mainPayMeth" : 0,
    "mainShippingAddress" : 0
});

db.users.insert(
{
    "_id" : ObjectId("5a28f32dacc04f7f2e9740b4"),
    "_class" : "com.dub.spring.domain.MyUser",
    "username" : "Arepo",
    "hashedPassword" : "{bcrypt}$2a$10$5YYBC/bq85.z8.cUbDzi5euUotdU1kHgqsuDGAiDNByiXMG3zEoMi",

    "accountNonExpired" : true,
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "enabled" : true,
    "authorities" : [{"authority"  :  "ROLE_USER"}],
    "mainPayMeth" : 0,
    "mainShippingAddress" : 0
});

db.users.insert(
{
    "_id" : ObjectId("5a28f351acc04f7f2e9740b5"),
    "_class" : "com.dub.spring.domain.MyUser",
    "username" : "Tenet",
    "hashedPassword" : "{bcrypt}$2a$10$Dr0/hk5Zy8xilHh.fdoc.OTsT/sDMraT1i4IMVpW39pzbu2w6ZVMC",

    "accountNonExpired" : true,
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "enabled" : true,
    "authorities" : [{"authority"  :  "ROLE_USER"}],
    "mainPayMeth" : 0,
    "mainShippingAddress" : 0
});

db.users.insert(
{
    "_id" : ObjectId("5a28f35cacc04f7f2e9740b6"),
    "_class" : "com.dub.spring.domain.MyUser",
    "username" : "Opera",
    "hashedPassword" : "{bcrypt}$2a$10$VwntQlZvYt4g7e3M7QadG.91SkLd/MW1vrhab2Qj.0VkTdGcjVrnm",

    "accountNonExpired" : true,
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "enabled" : true,
    "authorities" : [{"authority"  :  "ROLE_USER"}],
    "mainPayMeth" : 0,
    "mainShippingAddress" : 0
});

db.users.insert(
{
    "_id" : ObjectId("5a28f364acc04f7f2e9740b7"),
    "_class" : "com.dub.spring.domain.MyUser",
    "username" : "Rotas",
    "hashedPassword" : "{bcrypt}$2a$10$zICb16aMAVQyJ6180HUpDuvQAbZX.yGPmjUC7FSD7otSRSoBdbnjG",

    "accountNonExpired" : true,
    "accountNonLocked" : true,
    "credentialsNonExpired" : true,
    "enabled" : true,
    "authorities" : [{"authority"  :  "ROLE_USER"}],
    "mainPayMeth" : 0,
    "mainShippingAddress" : 0
});



db.orders.drop();


db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740cc"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f32dacc04f7f2e9740b4"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740a2",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-01-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740ce"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f351acc04f7f2e9740b5"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e97409f",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-02-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740cf"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f35cacc04f7f2e9740b6"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740a0",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-03-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740d0"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740a1",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-04-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740d1"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f306acc04f7f2e9740b3"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740a3",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-05-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740d2"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f32dacc04f7f2e9740b4"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ad",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-06-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740d3"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f351acc04f7f2e9740b5"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ab",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-07-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740d4"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f35cacc04f7f2e9740b6"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740a7",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-08-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740d5"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740aa",
			"quantity" : 1
		},
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ad",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-09-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740d6"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f306acc04f7f2e9740b3"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740aa",
			"quantity" : 1
		},
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ad",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-10-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740d7"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740aa",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-11-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740d8"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ac",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-12-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740d9"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f2b9acc04f7f2e9740b1"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ac",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-08-14T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740cb"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f306acc04f7f2e9740b3"),
	"state" : "SHIPPED",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ac",
			"quantity" : 1
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-07-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740cd"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
	"state" : "CART",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ac",
			"quantity" : 3
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-07-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740da"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
	"state" : "CART",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ac",
			"quantity" : 3
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-07-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740db"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
	"state" : "CART",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ac",
			"quantity" : 3
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-07-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740dc"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
	"state" : "CART",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ac",
			"quantity" : 3
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-07-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740dd"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
	"state" : "CART",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ac",
			"quantity" : 3
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-07-24T00:00:00Z")
});

db.orders.insert(
{
	"_id" : ObjectId("5a28f366acc04f7f2e9740de"),
	"_class" : "com.dub.spring.domain.OrderDocument",
	"userId" : ObjectId("5a28f364acc04f7f2e9740b7"),
	"state" : "CART",
	"lineItems" : [
		{
			"bookId" : "5a28f2b0acc04f7f2e9740ac",
			"quantity" : 3
		}
	],
	"shippingAddress" : {
		"street" : "",
		"city" : "",
		"zip" : "",
		"state" : "",
		"country" : ""
	},
	"paymentMethod" : {
		
	},
	"subtotal" : 0,
	"date" : ISODate("2017-07-24T00:00:00Z")
});




/* Finally create a Spring user */

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


