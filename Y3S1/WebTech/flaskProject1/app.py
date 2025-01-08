from flask import Flask, render_template, request, jsonify
from tree_logic import AVLTree  # Ensure AVLTree is correctly imported

app = Flask(__name__)

# Initialize the AVL tree
avl_tree = AVLTree()  # This should only be defined once

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
        avl_tree.insert(int(value))  # Ensure this calls the AVLTree instance
        return jsonify({"status": "success", "tree": avl_tree.get_tree()})
    return jsonify({"status": "error", "message": "Invalid input"})

@app.route('/delete_node', methods=['POST'])
def delete_node():
    data = request.get_json()
    value = data.get('value')
    if value is not None:
        avl_tree.delete(int(value))  # Ensure this calls the AVLTree instance
        return jsonify({"status": "success", "tree": avl_tree.get_tree()})
    return jsonify({"status": "error", "message": "Invalid input"})

if __name__ == '__main__':
    app.run(debug=True)
