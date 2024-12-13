from app import db, User, Post, app
from datetime import datetime

with app.app_context():
    db.create_all()

    # Create users
    admin = User(username='admin', email='admin@example.com', age=42)
    user = User(username='user', email='user@example.com', age=30)

    db.session.add(admin)
    db.session.add(user)

    # Create posts
    post1 = Post(user_id=1, posted=datetime(2024, 11, 13), title="Learn web technologies",
                 body="You have here the link to all the labs required this semester: https://en.wikiversity.org/wiki/Web_Technologies/2021-2022?authuser=1 ",
                 image_url="images/python.png")
    post2 = Post(user_id=2, posted=datetime(2025, 9, 2), title="Thesis Project",
                 body="My project implies remote control over some aspects of the house such as plant watering system and pet feeder.(implemented until now)",
                 image_url="images/python.png")
    post3 = Post(user_id=3, posted=datetime(2024, 12, 22), title="Why is this subject helpful",
                 body="It is part of the process, if we learn this skill we are going to become unstoppable, any company would want us.",
                 image_url="images/python.png")
    # Add more posts as needed...
    db.session.add(post1)
    db.session.add(post2)
    db.session.commit()