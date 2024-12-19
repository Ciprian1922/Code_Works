class Node {
  constructor(key) {
    this.key = key;
    this.left = null;
    this.right = null;
    this.height = 1;
  }
}

class AVLTree {
  constructor() {
    this.root = null;
  }

  // Utility to get the height of a node
  height(node) {
    return node ? node.height : 0;
  }

  // Utility to calculate balance factor
  getBalanceFactor(node) {
    return node ? this.height(node.left) - this.height(node.right) : 0;
  }

  // Right rotate
  rotateRight(y) {
    const x = y.left;
    const T = x.right;

    x.right = y;
    y.left = T;

    y.height = 1 + Math.max(this.height(y.left), this.height(y.right));
    x.height = 1 + Math.max(this.height(x.left), this.height(x.right));

    return x;
  }

  // Left rotate
  rotateLeft(x) {
    const y = x.right;
    const T = y.left;

    y.left = x;
    x.right = T;

    x.height = 1 + Math.max(this.height(x.left), this.height(x.right));
    y.height = 1 + Math.max(this.height(y.left), this.height(y.right));

    return y;
  }

  // Insert operation
  insert(node, key) {
    if (!node) return new Node(key);

    if (key < node.key) {
      node.left = this.insert(node.left, key);
    } else if (key > node.key) {
      node.right = this.insert(node.right, key);
    } else {
      return node; // Duplicate keys not allowed
    }

    // Update height of this node
    node.height = 1 + Math.max(this.height(node.left), this.height(node.right));

    // Get balance factor
    const balance = this.getBalanceFactor(node);

    // Perform rotations if unbalanced
    if (balance > 1 && key < node.left.key) return this.rotateRight(node);
    if (balance < -1 && key > node.right.key) return this.rotateLeft(node);
    if (balance > 1 && key > node.left.key) {
      node.left = this.rotateLeft(node.left);
      return this.rotateRight(node);
    }
    if (balance < -1 && key < node.right.key) {
      node.right = this.rotateRight(node.right);
      return this.rotateLeft(node);
    }

    return node;
  }

  // Public insert method
  add(key) {
    this.root = this.insert(this.root, key);
  }

  // Pre-order traversal
  preOrder(node = this.root) {
    if (node) {
      console.log(node.key);
      this.preOrder(node.left);
      this.preOrder(node.right);
    }
  }
}

export default AVLTree;
