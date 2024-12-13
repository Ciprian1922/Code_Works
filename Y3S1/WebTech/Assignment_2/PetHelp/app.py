from flask import Flask, render_template, url_for
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///blog.db'
db = SQLAlchemy(app)

class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(80), unique=True, nullable=False)
    email = db.Column(db.String(120), unique=True, nullable=False)
    age = db.Column(db.Integer)

    def __repr__(self):
        return f"User: {self.username}\nEmail: {self.email}"

class Post(db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    user_id = db.Column(db.Integer, db.ForeignKey(User.id))
    posted_by = db.relationship('User')
    posted = db.Column(db.DateTime, nullable=False)
    title = db.Column(db.String(120), nullable=False)
    body = db.Column(db.String(500), nullable=False)
    reads = db.Column(db.Integer, default=0)
    image_url = db.Column(db.String(200))

@app.route('/')
def display_all_posts():
    posts = Post.query.all()
    return render_template('posts.html', posts=posts)

@app.route('/post/<int:post_id>')
def display_post(post_id):
    post = Post.query.filter_by(id=post_id).first()
    if post:
        post.reads += 1
        db.session.commit()
    return render_template('post.html', post=post)

if __name__ == "__main__":
    app.run(debug=True)
