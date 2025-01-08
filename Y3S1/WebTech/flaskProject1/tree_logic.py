class Node:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
        self.height = 1

class AVLTree:
    def __init__(self):
        self.root = None

    def insert(self, value):
        self.root = self._insert(self.root, value)

    def _insert(self, node, value):
        if not node:
            return Node(value)

        #revent duplicates
        if value == node.value:
            return node  #ignore duplicate values

        if value < node.value:
            node.left = self._insert(node.left, value)
        else:
            node.right = self._insert(node.right, value)

        #pdate height and balance
        node.height = 1 + max(self._get_height(node.left), self._get_height(node.right))
        return self._balance(node)

    def delete(self, value):
        self.root = self._delete(self.root, value)

    def _delete(self, node, value):
        if not node:
            return node
        if value < node.value:
            node.left = self._delete(node.left, value)
        elif value > node.value:
            node.right = self._delete(node.right, value)
        else:
            if not node.left or not node.right:
                node = node.left or node.right
            else:
                successor = self._get_min(node.right)
                node.value = successor.value
                node.right = self._delete(node.right, successor.value)

        if not node:
            return node
        node.height = 1 + max(self._get_height(node.left), self._get_height(node.right))
        return self._balance(node)

    def _balance(self, node):
        balance = self._get_balance(node)
        if balance > 1:
            if self._get_balance(node.left) < 0:
                node.left = self._rotate_left(node.left)
            return self._rotate_right(node)
        if balance < -1:
            if self._get_balance(node.right) > 0:
                node.right = self._rotate_right(node.right)
            return self._rotate_left(node)
        return node

    def _rotate_left(self, z):
        y = z.right
        z.right = y.left
        y.left = z
        z.height = 1 + max(self._get_height(z.left), self._get_height(z.right))
        y.height = 1 + max(self._get_height(y.left), self._get_height(y.right))
        return y

    def _rotate_right(self, z):
        y = z.left
        z.left = y.right
        y.right = z
        z.height = 1 + max(self._get_height(z.left), self._get_height(z.right))
        y.height = 1 + max(self._get_height(y.left), self._get_height(y.right))
        return y

    def _get_height(self, node):
        return node.height if node else 0

    def _get_balance(self, node):
        return self._get_height(node.left) - self._get_height(node.right)

    def _get_min(self, node):
        while node.left:
            node = node.left
        return node

    def get_tree(self):
        return self._build_tree_json(self.root)

    def _build_tree_json(self, node):
        if not node:
            return None

        children = []
        if node.left:
            children.append(self._build_tree_json(node.left))
        if node.right:
            children.append(self._build_tree_json(node.right))

        return {
            "name": node.value,
            "children": children
        }

    def _traverse(self, node, result):
        if node:
            result.append({"value": node.value, "left": bool(node.left), "right": bool(node.right)})
            self._traverse(node.left, result)
            self._traverse(node.right, result)
