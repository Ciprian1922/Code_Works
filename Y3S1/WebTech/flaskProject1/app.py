from flask import Flask, render_template, request, jsonify
import sqlite3
from tree_logic import AVLTree  # Ensure AVLTree is implemented correctly

app = Flask(__name__)

# Initialize AVL Tree and Binary Tree
avl_tree = AVLTree()

class BinaryTree:
    def __init__(self):
        self.root = None

    class Node:
        def __init__(self, value):
            self.value = value
            self.left = None
            self.right = None

    def insert(self, value):
        if self.root is None:
            self.root = self.Node(value)
        else:
            self._insert(self.root, value)

    def _insert(self, node, value):
        if value < node.value:
            if node.left is None:
                node.left = self.Node(value)
            else:
                self._insert(node.left, value)
        else:
            if node.right is None:
                node.right = self.Node(value)
            else:
                self._insert(node.right, value)

    def delete(self, value):
        self.root = self._delete(self.root, value)

    def _delete(self, node, value):
        if node is None:
            return node
        if value < node.value:
            node.left = self._delete(node.left, value)
        elif value > node.value:
            node.right = self._delete(node.right, value)
        else:
            if node.left is None:
                return node.right
            elif node.right is None:
                return node.left
            min_larger_node = self._get_min(node.right)
            node.value = min_larger_node.value
            node.right = self._delete(node.right, min_larger_node.value)
        return node

    def _get_min(self, node):
        current = node
        while current.left is not None:
            current = current.left
        return current

    def get_tree(self):
        # Returns a simple tree structure
        return self._inorder(self.root)

    def _inorder(self, node):
        if node is None:
            return []
        return self._inorder(node.left) + [node.value] + self._inorder(node.right)

    def clear(self):
        self.root = None

# Initialize Binary Tree
binary_tree = BinaryTree()

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

@app.route('/binary_tree')
def binary_tree_page():
    return render_template('binary_tree_visualization.html')

# AVL Tree Routes
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

# Binary Tree Routes

@app.route('/binary_tree/add_node', methods=['POST'])
def binary_tree_add_node():
    data = request.get_json()
    value = data.get('value')
    if value is not None:
        binary_tree.insert(int(value))
        return jsonify({"status": "success", "tree": binary_tree.get_tree()})
    return jsonify({"status": "error", "message": "Invalid input"})

@app.route('/binary_tree/delete_node', methods=['POST'])
def binary_tree_delete_node():
    data = request.get_json()
    value = data.get('value')
    if value is not None:
        binary_tree.delete(int(value))
        return jsonify({"status": "success", "tree": binary_tree.get_tree()})
    return jsonify({"status": "error", "message": "Invalid input"})

@app.route('/binary_tree/save_tree', methods=['POST'])
def binary_tree_save_tree():
    data = request.get_json()
    tree_name = data.get('name')
    if tree_name:
        # Get the tree data from the frontend (replace this with your actual tree data logic)
        tree_data = str(binary_tree.get_tree())  # Example, you may want to adjust this

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

@app.route('/binary_tree/load_trees', methods=['GET'])
def binary_tree_load_trees():
    conn = sqlite3.connect('trees.db')
    cursor = conn.cursor()
    cursor.execute('SELECT name FROM trees')
    trees = cursor.fetchall()
    conn.close()
    return jsonify({"trees": [tree[0] for tree in trees]})

@app.route('/binary_tree/load_tree', methods=['POST'])
def binary_tree_load_tree():
    data = request.get_json()
    tree_name = data.get('name')
    if tree_name:
        conn = sqlite3.connect('trees.db')
        cursor = conn.cursor()
        cursor.execute('SELECT tree_data FROM trees WHERE name = ?', (tree_name,))
        result = cursor.fetchone()
        conn.close()
        if result:
            binary_tree_data = eval(result[0])
            binary_tree.clear()  # Clear the current tree
            for value in binary_tree_data:
                binary_tree.insert(value)  # Rebuild the tree
            return jsonify({"status": "success", "tree": binary_tree.get_tree()})
    return jsonify({"status": "error", "message": "Tree not found"})

@app.route('/binary_tree/reset_tree', methods=['POST'])
def binary_tree_reset_tree():
    binary_tree.clear()  # Clear the current binary tree
    return jsonify(status="success")

if __name__ == '__main__':
    app.run(debug=True)
