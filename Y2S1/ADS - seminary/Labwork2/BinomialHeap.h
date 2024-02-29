/*
 * BinomialHeap.h
 *
 */

#ifndef BINOMIALHEAP_H_
#define BINOMIALHEAP_H_

#include <string>

using namespace std;

struct BinomialHeap;

class Node {
    Node *p;         		// pointer to parent
    Node *child;     		// pointer to first child
    Node *sibling;   		// pointer to right sibling
    int key;         		// key
    int degree;      		// number of children
    string data;     		// satellite data: can be any other kind of data
public:
    Node(int, string s=""); // constructor
    string toString();
    void showTreeContent(int);	// displays the content of the tree with this node as root

    /* friend methods */
    friend BinomialHeap* binomialHeapUnion(BinomialHeap*, BinomialHeap*);
    friend void binomialLink(Node*, Node*);
    friend BinomialHeap* insert(BinomialHeap*, Node*);
    friend Node* nodeListMerge(Node*, Node*);
    friend Node* findMinRoot(Node*);
    friend Node* binomialHeapExtractMin(BinomialHeap*);
    friend Node* reverseList(Node*);
};


struct BinomialHeap {
    Node* head;
    BinomialHeap();
    virtual ~BinomialHeap();
    void showContent();

    /* friend methods */
    friend BinomialHeap* makeBinomialHeap();
    friend BinomialHeap* insert(BinomialHeap*, Node*);
    friend BinomialHeap* binomialHeapUnion(BinomialHeap*, BinomialHeap*);
    friend Node* binomialHeapMerge(BinomialHeap*, BinomialHeap*);

};

#endif /* BINOMIALHEAP_H_ */
