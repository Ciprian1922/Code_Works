from flask import Flask, render_template, request, jsonify
from tree_logic import AVLTree

app = Flask(__name__)

# Initialize the AVL tree
avl_tree = AVLTree()

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/add_node', methods=['POST'])
def add_node():
    data = request.get_json()
    value = data.get('value')
    if value is not None:
        avl_tree.insert(int(value))
        print("Updated Tree:", avl_tree.get_tree())  # Debugging line
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

if __name__ == '__main__':
    app.run(debug=True)
