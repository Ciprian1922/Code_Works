/*
 * Main.cpp
 */

#include "BinomialHeap.h"
#include <iostream>
#include <vector>

using namespace std;

BinomialHeap* makeBinomialHeap();
Node* binomialHeapExtractMin(BinomialHeap*);
unsigned int current_heap;

int chooseOption(int max) {
	int option;
	while (true) {
		if (!(cin >> option)) {
			cout << "Integer between 1 and "<< max << " expected. Try again: ";
			cin.clear();
			cin.ignore(10000, '\n');
		} else break;
	}
	cin.clear();
	cin.ignore(10000, '\n');
	return option;
}

void showMenu() {
	cout << "Current heap is " << current_heap << endl
		 << "Select operations to perform:" << endl
		 << "\t1 Show number of available heaps" << endl
		 << "\t2 Select current heap" << endl
		 << "\t3 Show content of current heap" << endl
		 << "\t4 Create a new binomial heap" << endl
		 << "\t5 Insert node in current heap" << endl
		 << "\t6 Merge binomial heaps" << endl
		 << "\t7 Extract the node with minimum key from the current heap" << endl
		 << "\t8 ..." << endl
		 << "\t9 Stop " << endl << endl
		 << "Choose (1-9): ";
}

int main() {
	current_heap = 0;
	int n, k;
	string s;
	vector<BinomialHeap*> heaps(0);
	BinomialHeap* h;
	Node* x;
	unsigned int idx1, idx2, tmp;

	while (true) {
		showMenu();
		switch (chooseOption(9)) {
		case 1:    // Show number of available heaps
			cout << "There are " << heaps.size() << " heaps" << endl;
			break;
		case 2:    // Select current heap
			if (heaps.size() == 0)
				cout << "No heaps available" << endl;
			else {
				cout << "Choose a number between 1 and " << heaps.size() << ": ";
				cin >> tmp;
				if (1 << tmp && tmp <= heaps.size())
					current_heap = tmp;
			}
			break;
		case 3:    // Show content of current heap
			if (current_heap > 0) {
				h = heaps[current_heap - 1];
				h->showContent();
			}
			break;
		case 4:    // Create a new binomial heap
			heaps.push_back(makeBinomialHeap());
			break;
		case 5:    // Insert node in current heap
			if (current_heap > 0) {
				cout << "Input key and data (a string) separated by whitespace: ";
				cin >> k >> s;
				heaps[current_heap - 1] = insert(heaps[current_heap - 1], new Node(k,s));
			} else {
				cout << "Select current heap" << endl;
			}
			break;
		case 6:    // Merge heaps
			cout << "Type the indexes of the heaps to merge, separated by space(s): ";
			while (true) {
				// make sure that we read two integers
				if (!(cin >> idx1 >> idx2)) {
					cout << "Invalid indexes. Try again: ";
					cin.clear();
					cin.ignore(10000, '\n');
				}
				if (idx1 > idx2) swap(idx1, idx2);
				// make sure that 1 <= idx1 < idx2 <= heaps.size()
				if ((idx1 < 1) || (idx1 == idx2) || idx2 > heaps.size())
					cout << "Invalid indexes. Try again: ";
				else
					break;
			}
			// The user assumes heap indexes are between 1 and heaps.size().
			// In C++ heap indexes should be between 0 and heaps.size() - 1
			// Therefore, we decrement the the index values introduced by the user.
			idx1--;
			idx2--;
			h = binomialHeapUnion(heaps[idx1], heaps[idx2]);
			delete heaps[idx1];
			heaps[idx1] = h;
			// Erase the heap heaps[idx2] from heaps.
			// This operation will also resize the vector of heaps accordingly.
			heaps.erase(heaps.begin() + (idx2 - 1));
			if (current_heap - 1 >= idx2)
				current_heap--;
			break;
		case 7:    // Extract the minimum element from current heap
			if (current_heap > 0) {
				x = binomialHeapExtractMin(heaps[current_heap - 1]);
				if (x)
					cout << x->toString() << " was removed" << endl;
			}
			break;
		case 8:    // ...
			break;
		case 9:    // Stop
			n = heaps.size();
			for (int i = 0; i < n; i++) {
				if (heaps[i]->head)
					delete heaps[i]->head;
				delete heaps[i];
			}
			heaps.resize(0);
			return 0;
		}
	}
}


