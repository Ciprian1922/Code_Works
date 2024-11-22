from datetime import datetime
from flaskapp import app, db, User, Post

with app.app_context():
    db.create_all()

    admin = User(username='admin', email='admin@something.com', age=42)
    regular_user = User(username='user', email='user@something.com', age=20)

    post1 = Post(user_id=1,
                 posted=datetime.strptime("5 November 2021", "%d %B %Y"),
                 title="BOOM",
                 body="Something for the first post of the block")

    post2 = Post(user_id=1,
                 posted=datetime.strptime("17 November 2021", "%d %B %Y"),
                 title="Post 2",
                 body="Yeye second post")

    post3 = Post(user_id=2,
                 posted=datetime.utcnow(),
                 title="Post 3",
                 body="Third one hehe")

    db.session.add(admin)
    db.session.add(regular_user)
    db.session.add(post1)
    db.session.add(post2)
    db.session.add(post3)

    db.session.commit()
