from app import db,User,Post,app,Comment
from datetime import datetime

with app.app_context():
    db.create_all()
    admin = User(username='admin',email='popaciprian2003@gmail.com', age=18)
    user = User(username='user',email='RoxanaPatricia@gmail.com', age=22)
    db.session.add(admin)
    db.session.add(user)
    post1 = Post(user_id=1, posted=datetime(2024,11,13), title="Learn web technologies",
                 body="You have here the link to all the labs required this semester: https://en.wikiversity.org/wiki/Web_Technologies/2021-2022?authuser=1 ",
                 image_url="images/post1.jpg")
    post2 = Post(user_id=2, posted=datetime(2025,9,2), title="Thesis Project",
                 body="My project implies remote control over some aspects of the house such as plant watering system and pet feeder.(implemented until now)",
                 image_url="images/post2.jpg")
    post3 = Post(user_id=3, posted=datetime(2025,9,2), title="Happy New 2025",
                 body="It is crazy how fast time flies, maybe we have to appreciate it more...",
                 image_url="images/post3.jpg")
    db.session.add(post1)
    db.session.add(post2)
    db.session.add(post3)
    db.session.commit()
