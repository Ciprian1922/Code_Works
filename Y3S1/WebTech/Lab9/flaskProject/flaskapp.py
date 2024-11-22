from flask import Flask, render_template
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///site.db'
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
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))
    posted_by = db.relationship('User')
    posted = db.Column(db.DateTime, nullable=False)
    title = db.Column(db.String(120), nullable=False)
    body = db.Column(db.String(500), nullable=False)
    reads = db.Column(db.Integer)

@app.route('/')
def display_content(content=None):
    return "Salutare"

@app.route('/user/profile/<int:user_id>')
def display_user_profile(user_id):
    user = User.query.filter_by(id=user_id).first()
    return render_template('user_profile.html', user=user)

@app.route('/users')
def display_all_users():
    users = User.query.all()
    return render_template('users.html', users=users)

@app.route('/posts')
def display_all_posts():
    posts = Post.query.all()
    return render_template('posts.html', posts=posts)

@app.route('/post/<int:post_id>')
def display_post(post_id):
    found_post = Post.query.filter_by(id=post_id).first()
    return render_template('post.html', post=found_post)

if __name__ == '__main__':
    app.run(debug=True)

#http://127.0.0.1:5000/
#http://127.0.0.1:5000/users
#http://127.0.0.1:5000/posts
#http://127.0.0.1:5000/user/profile/1
#http://127.0.0.1:5000/post/1