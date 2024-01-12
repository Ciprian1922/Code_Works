#include <vector>
#include <set>
#include <iostream>
#include <istream>
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
class DFS {
    vector<int> pre; //preorder
    vector<int> post; //postorder
    vector<int> revpost; //reversed postorder
    vector<bool> marked;
    vector<int> P; //representation with predecessor

    void dfs(graph& G, int u) {
        pre.push_back(u);
        marked[u] = true;
        for (int v : G.adj(u))
            if (!marked[v]) { P[v] = u; dfs(G, v); }
        post.push_back(u);
    }
public:
    DFS(graph& G, vector<int>& ord) {
        int n = G.V();
        pre = vector<int>(0);
        post = vector<int>(0);
        revpost = vector<int>(n);
        marked = vector<bool>(n, false);
        P = vector<int>(n, -1); //P[u] is -1 when u has no predecessor
        for (int s : ord)
            if (!marked[s]) dfs(G, s);
        for (int s = 0; s < n; s++)
            revpost[s] = post[n - 1 - s];
    }
    vector<int> preorder() { return pre; }
    vector<int> postorder() { return post; }
    vector<int> revpostorder() { return revpost; }
};
int main() {
    //usecase
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

    //creating the vector representing the order in which nodes should be traversed
    vector<int> order;
    order.reserve(G.V());
for (int i = 0; i < G.V(); i++) {
        order.push_back(i);
    }
    DFS dfsInstance(G, order);    //create an instance of DFS using the new constructor
    vector<int> preOrder = dfsInstance.preorder();    //accessing the pre, post, and revpost vectors
    vector<int> postOrder = dfsInstance.postorder();
    vector<int> revPostOrder = dfsInstance.revpostorder();

    //showing the results
    cout << "Preorder: ";
    for (int node : preOrder) {
        cout << node << " ";
    }
    cout << endl;

    cout << "Postorder: ";
    for (int node : postOrder) {
        cout << node << " ";
    }
    cout << endl;

    cout << "Reversed Postorder: ";
    for (int node : revPostOrder) {
        cout << node << " ";
    }
    cout << endl;

    return 0;
}
