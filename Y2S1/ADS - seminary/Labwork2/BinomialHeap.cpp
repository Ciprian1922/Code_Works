#include <iostream>
#include <sstream>
#include <climits>
#include "BinomialHeap.h"
using namespace std;

/**
 * Node constructor
 */
Node::Node(int k, string s) // constructor
        : p(0), child(0), sibling(0), key(k), degree(0), data(s) {}

string Node::toString() {
    stringstream ss;
    if (data != "") {
        ss << key << " : " << data;
        return ss.str();
    }
    return "";
}

void Node::showTreeContent(int indent) {
    if (sibling)
        sibling->showTreeContent(indent);
    for (int i = 1; i <= indent; i++)
        cout << ' ';
    cout << toString() << endl;
    if (child)
        child->showTreeContent(indent + 1);
}

void binomialLink(Node* y, Node* z) {
    y->p = z;
    y->sibling = z->child;
    z->child = y;
    z->degree++;
}

/*
 * creates an empty binomial heap and returns a pointer to it.
 * This function is a friend of the class BinomialHeap.
 */
BinomialHeap* makeBinomialHeap() {
    return new BinomialHeap();
}

/*
 * Inserts node x in the binomial heap H.
 * The children and siblings of x are 'forgotten.'
 * This function is a friend of the classes Node and BinomialHeap.
 */
BinomialHeap* insert(BinomialHeap* H, Node* x) {
    BinomialHeap* H1 = makeBinomialHeap();
    BinomialHeap* h;
    x->p = x->child = x->sibling = 0;
    x->degree = 0;
    H1->head = x;
    h = binomialHeapUnion(H, H1);
    delete H;
    delete H1;
    return h;
}

/*
 * l1 and l2 are lists of nodes, sorted in ascending order of their degree,
 * when the 'sibling' pointers are followed.
 * nodeListMerge(l1, l2) merges l1 and l2 destructively, in ascending order of their degrees.
 *
 * This function is a friend of the class Node.
 */
Node* nodeListMerge(Node* l1, Node* l2) {
    if (l1 == 0)        // l1 is empty
        return l2;
    else if (l2 == 0)    // l2 is empty
        return l1;
    else if (l1->degree <= l2->degree) {
        l1->sibling = nodeListMerge(l1->sibling, l2);
        return l1;
    } else {
        l2->sibling = nodeListMerge(l1, l2->sibling);
        return l2;
    }
}

/**
 * Constructor of an empty binomial heap:
 *   head is instantiated with a null pointer.
 */
BinomialHeap::BinomialHeap() {
    head = 0;
}

void BinomialHeap::showContent() {
    if (head) {
        cout << "The content of the binomial heap is: " << endl;
        head->showTreeContent(0);
    } else
        cout << "The binomial heap is empty." << endl;
}

BinomialHeap::~BinomialHeap() {}

/**
 * binomialHeapUnion(H1, H2) returns a pointer to a binomial heap, which is the union of the binomial heaps
 * pointed to by H1 and H2. The heaps pointed to by H1 and H2 may be destroyed.
 *
 * This function is a friend of the class BinomialHeap.
 */
BinomialHeap* binomialHeapUnion(BinomialHeap* H1, BinomialHeap* H2) {
    BinomialHeap* H = makeBinomialHeap();
    H->head = binomialHeapMerge(H1, H2);
    if (H->head == 0)
        return H;
    Node* prev_x = 0;
    Node* x = H->head;
    Node* next_x = x->sibling;

    while (next_x) {
        if (x->degree != next_x->degree ||
            (next_x->sibling && next_x->sibling->degree == x->degree)) {
            prev_x = x;                   // Cases 1 and 2
            x = next_x;                   // Cases 1 and 2
        } else if (x->key <= next_x->key) {
            x->sibling = next_x->sibling; // Case 3
            binomialLink(next_x, x);      // Case 3
        } else {
            if (prev_x == 0)              // Case 4
                H->head = next_x;         // Case 4
            else
                prev_x->sibling = next_x; // Case 4
            binomialLink(x, next_x);      // Case 4
            x = next_x;                   // Case 4
        }
        next_x = x->sibling;
    }
    return H;
}

/**
 * This function is a friend of the class BinomialHeap.
 */
Node* binomialHeapMerge(BinomialHeap* H1, BinomialHeap* H2) {
    return nodeListMerge(H1->head, H2->head);
}

/*
 * auxiliary function: it reverses the list l whose nodes are linked by 'sibling'
 */
Node* reverseList(Node* l) {
    Node* prev = nullptr;
    Node* current = l;
    Node* next = nullptr;
    while (current != nullptr) {
        next = current->sibling;
        current->sibling = prev;
        prev = current;
        current = next;
    }
    return prev;
}

/**
 * this function returns a pointer to the node with the minimum key from
 * the linked list of nodes pointed by n.
 */
Node* findMinRoot(Node* n) {
    if (n == nullptr) {
        return nullptr;
    }
    Node* minNode = n;
    int minKey = n->key;
    while (n != nullptr) {
        if (n->key < minKey) {
            minKey = n->key;
            minNode = n;
        }
        n = n->sibling;
    }
    return minNode;
}

/* This function extracts the node with the minimum key from the binomial heap pointed to by H (see Lecture Notes).
 */
Node* binomialHeapExtractMin(BinomialHeap* H) {
    // find the root x with the minimum key from the root
    // list of H and remove it from the root list of H
    Node* x = findMinRoot(H->head);
    if (x == 0) { // H is empty
        cout << "This heap is empty" << endl;
        return 0;
    } else {
        Node* n = H->head;
        // drop x from the root list of H
        if (n == x)
            H->head = n->sibling;
        else {
            while (n->sibling != x) n = n->sibling;
            n->sibling = n->sibling->sibling;
        }
    }
    BinomialHeap* H1 = makeBinomialHeap();
    H1->head = reverseList(x->child);
    H->head = binomialHeapUnion(H, H1)->head;
    x->child = x->sibling = x->p = 0;
    return x;
}
