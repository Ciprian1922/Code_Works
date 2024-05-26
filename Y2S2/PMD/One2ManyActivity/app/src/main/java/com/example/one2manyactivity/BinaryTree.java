package com.example.one2manyactivity;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    static class Node {
        int value;
        Node left, right;

        Node(int value) {
            this.value = value;
            left = right = null;
        }
    }

    private Node root;
    private final List<Integer> values;

    public BinaryTree() {
        this.root = null;
        this.values = new ArrayList<>();
    }

    public Node getRoot() {
        return root;
    }

    public void add(int value) {
        values.add(value);
        root = addRecursive(root, value);
    }

    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        }

        return current;
    }

    public boolean removeLastAdded() {
        if (values.isEmpty()) {
            return false;
        }
        int value = values.remove(values.size() - 1);
        root = removeRecursive(root, value);
        return true;
    }

    private Node removeRecursive(Node current, int value) {
        if (current == null) {
            return null;
        }

        if (value == current.value) {
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.right == null) {
                return current.left;
            }
            if (current.left == null) {
                return current.right;
            }
            int smallestValue = findSmallestValue(current.right);
            current.value = smallestValue;
            current.right = removeRecursive(current.right, smallestValue);
            return current;
        }

        if (value < current.value) {
            current.left = removeRecursive(current.left, value);
            return current;
        }

        current.right = removeRecursive(current.right, value);
        return current;
    }

    private int findSmallestValue(Node root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }
}
