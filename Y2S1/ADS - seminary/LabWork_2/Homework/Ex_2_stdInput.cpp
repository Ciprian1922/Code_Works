#include <iostream>
#include <vector>
#include <map>
#include <queue>
#include <string>
#include <fstream>

using namespace std;

const int MAX_PATTERNS = 100;
const int ALPHABET_SIZE = 256;

struct TrieNode {
    map<char, TrieNode*> children;
    TrieNode* fail;
    vector<int> patterns;

    TrieNode() : fail(nullptr) {}
};

class AhoCorasick {
public:
    AhoCorasick() {
        root = new TrieNode();
    }

    void addPattern(const string& pattern, int patternIndex) {
        TrieNode* node = root;

        for (char c : pattern) {
            if (!node->children.count(c)) {
                node->children[c] = new TrieNode();
            }
            node = node->children[c];
        }

        node->patterns.push_back(patternIndex);
    }

    void buildFailureLinks() {
        queue<TrieNode*> q;
        for (auto& pair : root->children) {
            TrieNode* child = pair.second;
            q.push(child);
            child->fail = root;
        }

        while (!q.empty()) {
            TrieNode* node = q.front();
            q.pop();

            for (auto& pair : node->children) {
                char c = pair.first;
                TrieNode* child = pair.second;
                q.push(child);

                TrieNode* failNode = node->fail;
                while (failNode != nullptr && !failNode->children.count(c)) {
                    failNode = failNode->fail;
                }

                if (failNode != nullptr) {
                    child->fail = failNode->children[c];
                } else {
                    child->fail = root;
                }
            }
        }
    }

    void searchPatterns(const string& text) {
        TrieNode* node = root;

        for (int i = 0; i < text.size(); i++) {
            char c = text[i];

            while (node != root && !node->children.count(c)) {
                node = node->fail;
            }

            if (node->children.count(c)) {
                node = node->children[c];
            }

            if (!node->patterns.empty()) {
                for (int patternIndex : node->patterns) {
                    patternOccurrences[patternIndex].push_back(i - patterns[patternIndex].size() + 1);
                }
            }
        }
    }

    void printPatternOccurrences() {
        for (int i = 0; i < patternCount; i++) {
            cout << "Pattern " << i + 1 << " occurs at positions: ";
            for (int pos : patternOccurrences[i]) {
                cout << pos << " ";
            }
            cout << endl;
        }
    }

private:
    TrieNode* root;
    vector<string> patterns;
    vector<vector<int>> patternOccurrences;
    int patternCount;
};

int main() {
    AhoCorasick aho;
    int patternCount;

    cout << "Enter the number of patterns: ";
    cin >> patternCount;

    cin.ignore(); // Consume the newline character

    cout << "Enter patterns:" << endl;

    for (int i = 0; i < patternCount; i++) {
        string pattern;
        cout << "Pattern " << i + 1 << ": ";
        getline(cin, pattern);
        aho.addPattern(pattern, i);
    }

    aho.buildFailureLinks();

    string text;

    cout << "Enter the text: ";
    getline(cin, text);

    aho.searchPatterns(text);
    aho.printPatternOccurrences();

    return 0;
}
