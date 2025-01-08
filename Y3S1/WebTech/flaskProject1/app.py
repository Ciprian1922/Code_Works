from flask import Flask, render_template, request, jsonify
import sqlite3
from tree_logic import AVLTree  # Ensure AVLTree is implemented correctly

app = Flask(__name__)
avl_tree = AVLTree()

# Database initialization
def init_db():
    conn = sqlite3.connect('trees.db')
    cursor = conn.cursor()
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS trees (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT UNIQUE NOT NULL,
            tree_data TEXT NOT NULL
        )
    ''')
    conn.commit()
    conn.close()

init_db()

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/avl_tree')
def avl_tree_page():
    return render_template('avl_tree.html')

@app.route('/add_node', methods=['POST'])
def add_node():
    data = request.get_json()
    value = data.get('value')
    if value is not None:
        avl_tree.insert(int(value))
        return jsonify({"status": "success", "tree": avl_tree.get_tree()})
    return jsonify({"status": "error", "message": "Invalid input"})

@app.route('/delete_node', methods=['POST'])
def delete_node():
    data = request.get_json()
    value = data.get('value')
    if value is not None:
        avl_tree.delete(int(value))
        return jsonify({"status": "success", "tree": avl_tree.get_tree()})
    return jsonify({"status": "error", "message": "Invalid input"})


@app.route('/save_tree', methods=['POST'])
def save_tree():
    data = request.get_json()
    tree_name = data.get('name')
    if tree_name:
        # Get the tree data from the frontend (replace this with your actual tree data logic)
        tree_data = str(avl_tree.get_tree())  # Example, you may want to adjust this

        conn = sqlite3.connect('trees.db')
        cursor = conn.cursor()

        # Check if the tree already exists
        cursor.execute('SELECT 1 FROM trees WHERE name = ?', (tree_name,))
        result = cursor.fetchone()

        if result:
            # If the tree exists, update it
            cursor.execute('UPDATE trees SET tree_data = ? WHERE name = ?', (tree_data, tree_name))
        else:
            # Otherwise, insert it as a new tree
            cursor.execute('INSERT INTO trees (name, tree_data) VALUES (?, ?)', (tree_name, tree_data))

        conn.commit()
        conn.close()
        return jsonify({"status": "success"})
    return jsonify({"status": "error", "message": "Tree name is missing"})


@app.route('/load_trees', methods=['GET'])
def load_trees():
    conn = sqlite3.connect('trees.db')
    cursor = conn.cursor()
    cursor.execute('SELECT name FROM trees')
    trees = cursor.fetchall()
    conn.close()
    return jsonify({"trees": [tree[0] for tree in trees]})

@app.route('/load_tree', methods=['POST'])
def load_tree():
    data = request.get_json()
    tree_name = data.get('name')
    if tree_name:
        conn = sqlite3.connect('trees.db')
        cursor = conn.cursor()
        cursor.execute('SELECT tree_data FROM trees WHERE name = ?', (tree_name,))
        result = cursor.fetchone()
        conn.close()
        if result:
            avl_tree.root = avl_tree._build_tree_from_data(eval(result[0]))
            return jsonify({"status": "success", "tree": avl_tree.get_tree()})
    return jsonify({"status": "error", "message": "Tree not found"})

@app.route('/check_tree_exists', methods=['POST'])
def check_tree_exists():
    data = request.get_json()
    tree_name = data.get('name')
    if tree_name:
        conn = sqlite3.connect('trees.db')
        cursor = conn.cursor()
        cursor.execute('SELECT 1 FROM trees WHERE name = ?', (tree_name,))
        result = cursor.fetchone()
        conn.close()
        return jsonify({"exists": bool(result)})
    return jsonify({"exists": False})


@app.route('/reset_tree', methods=['POST'])
def reset_tree():
    global avl_tree
    avl_tree = AVLTree()  # Reset to a new AVL tree instance
    return jsonify(status="success")


if __name__ == '__main__':
    app.run(debug=True)
