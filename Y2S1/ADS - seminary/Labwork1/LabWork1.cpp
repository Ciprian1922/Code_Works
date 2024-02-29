#include <iostream>
#include <sstream>

struct Node {
    int key;
    Node* sibling;
    Node(int k) : key(k), sibling(nullptr) {}
};

Node* reverseList(Node* head) {
    Node* prev = nullptr;
    Node* current = head;
    Node* next = nullptr;

    while (current != nullptr) {
        next = current->sibling;
        current->sibling = prev;
        prev = current;
        current = next;
    }

    return prev;
}

void displayReversedList(Node* head) {
    while (head != nullptr) {
        std::cout << head->key << " ";
        head = head->sibling;
    }
    std::cout << std::endl;
}

int main() {
    std::string input;
    std::getline(std::cin, input);

    std::istringstream iss(input);
    int key;
    Node* head = nullptr;
    Node* tail = nullptr;

    while (iss >> key) {
        Node* newNode = new Node(key);
        if (tail == nullptr) {
            head = newNode;
            tail = newNode;
        } else {
            tail->sibling = newNode;
            tail = newNode;
        }
    }

    head = reverseList(head);

    std::cout << "Reversed List: ";
    displayReversedList(head);

    return 0;
}
