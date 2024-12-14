from flask import Flask, render_template, request, redirect, url_for, session
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime

app=Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI']='sqlite:///blog.db'
db=SQLAlchemy(app)

class User(db.Model):
    id=db.Column(db.Integer,primary_key=True)
    username=db.Column(db.String(80),unique=True,nullable=False)
    email=db.Column(db.String(120),unique=True,nullable=False)
    age=db.Column(db.Integer)

    def __repr__(self):
        return f"User: {self.username}\nEmail:{self.email}"

class Post(db.Model):
    id= db.Column(db.Integer,primary_key=True,autoincrement=True)
    user_id =db.Column(db.Integer,db.ForeignKey(User.id))
    posted_by= db.relationship('User')
    posted= db.Column(db.DateTime,nullable=False)
    title =db.Column(db.String(120),nullable=False)
    body =db.Column(db.String(500),nullable=False)
    reads =db.Column(db.Integer,default=0)
    image_url= db.Column(db.String(200))

class Comment(db.Model):
    id =db.Column(db.Integer,primary_key=True,autoincrement=True)
    post_id= db.Column(db.Integer,db.ForeignKey(Post.id),nullable=False)
    post= db.relationship('Post',backref=db.backref('comments',lazy=True))
    body= db.Column(db.String(300),nullable=False)
    likes= db.Column(db.Integer,default=0)
    created_at =db.Column(db.DateTime,default=datetime.utcnow)

@app.route('/')
def display_all_posts():
    posts= Post.query.all()
    return render_template('posts.html',posts=posts)


@app.route('/post/<int:post_id>',methods=['GET','POST'])
def display_post(post_id):
    post = Post.query.get_or_404(post_id)
    if request.method == 'POST':
        comment_body = request.form['body']
        if comment_body:
            new_comment =Comment(post_id=post_id, body=comment_body)
            db.session.add(new_comment)
            db.session.commit()
        #redirect after post to avoid resubmission and prevent reads increment
        return redirect(url_for('display_post',post_id=post_id,from_comment=True))


    if request.method == 'GET' and not request.args.get('from_comment') and not request.args.get('from_like'):
        post.reads += 1 #increment reads only if not redirected from a comment or like action
        db.session.commit()

    return render_template('post.html',post=post)

@app.route('/like_comment/<int:comment_id>')
def like_comment(comment_id):
    comment = Comment.query.get_or_404(comment_id)
    comment.likes += 1
    db.session.commit()
    return redirect(url_for('display_post',post_id=comment.post_id,from_like=True))


if __name__ == "__main__":
    app.run(debug=True)
