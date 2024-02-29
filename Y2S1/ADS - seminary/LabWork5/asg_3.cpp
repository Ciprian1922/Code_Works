#include <vector>
#include <set>
#include <iostream>
#include <sstream>

using namespace std;

class graph {
protected:
    int nV; //number of nodes
    int nE; //number of edges
    vector<set<int>> vec; //representation with adjacency lists
public:
    explicit graph(istream& src) {
        nV = nE = 0;
        src >> nV >> nE;
        vec = vector<set<int>>(nV);
        int u, v;
        for (int e = 0; e < nE; e++) {
            src >> u >> v;
            vec[u].insert(v);
            vec[v].insert(u);
        }
    }
    [[nodiscard]] int V() const { return nV; }
    set<int> adj(int u) { return vec[u]; }
};

class ElementaryPaths {
    graph& G;
    vector<vector<int>> allPaths;
    vector<bool> visited;
    void dfs(int s, int d, vector<int>& path) {
        visited[s] = true;
        path.push_back(s);

        if (path.size() == d + 1) {
            allPaths.push_back(path);
        } else {
            for (int v : G.adj(s)) {
                if (!visited[v]) {
                    dfs(v, d, path);
                }
            }
        }
        visited[s] = false;
        path.pop_back();
    }
public:
    explicit ElementaryPaths(graph& graph) : G(graph) {}
    vector<vector<int>> paths(int s, int d) {
        allPaths.clear();
        visited = vector<bool>(G.V(), false);
        vector<int> path;
        dfs(s, d, path);
        return allPaths;
    }
};

int main() {
    //use case
    stringstream graphContent;
    graphContent << "13 13\n"
                 << "0 1\n"
                 << "0 2\n"
                 << "0 5\n"
                 << "0 6\n"
                 << "3 4\n"
                 << "3 5\n"
                 << "4 5\n"
                 << "4 6\n"
                 << "7 8\n"
                 << "9 10\n"
                 << "9 11\n"
                 << "9 12\n"
                 << "11 12";

    graph G(graphContent);

    //create an instance of ElementaryPaths
    ElementaryPaths elementaryPaths(G);

    //getting all elementary paths of length 3 starting from node 0
    int sourceNode = 0;
    int pathLength = 3;
    vector<vector<int>> result = elementaryPaths.paths(sourceNode, pathLength);

    //showing the results
    cout << "Elementary Paths of length " << pathLength << " starting from node " << sourceNode << ":" << endl;
    for (const auto& path : result) {
        for (int node : path) {
            cout << node << " ";
        }
        cout << endl;
    }
    return 0;
}
