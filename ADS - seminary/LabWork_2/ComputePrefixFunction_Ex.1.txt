#include <iostream>
#include <vector>

using namespace std;

vector<int> computePrefixFunction(const string& pattern) {
    int m = pattern.size();
    vector<int> pi(m, 0);  // Initialize the prefix function pi with zeros

    int k = 0;  // Length of the current prefix

    for (int i = 1; i < m; i++) {
        while (k > 0 && pattern[i] != pattern[k]) {
            k = pi[k - 1];
        }

        if (pattern[i] == pattern[k]) {
            k++;
        }

        pi[i] = k;
    }

    return pi;
}

int main() {
    string pattern;
    cin >> pattern;

    vector<int> pi = computePrefixFunction(pattern);

    for (int i = 0; i < pattern.size(); i++) {
        cout << "pi[" << i + 1 << "]=" << pi[i] << endl;
    }

    return 0;
}
