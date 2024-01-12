#include <vector>
#include <set>
#include <stack>
#include <iostream>
#include <algorithm>
#include <sstream>

using namespace std;

class digraph;

class Kosaraju {
    vector<bool> marked;
    vector<int> componentId;
    stack<int> order;  //declare 'order' stack here

    void dfs(digraph& G, int u);
    void reverseDFS(digraph& G, int u, int id);

public:
    explicit Kosaraju(digraph& G);

    int strongComponents() {
        return *max_element(componentId.begin(), componentId.end()) + 1;
    }

    set<int> SC(int i) {
        set<int> result;
        for (int v = 0; v < componentId.size(); v++)
            if (componentId[v] == i) result.insert(v);
        return result;
    }
};
class digraph {
protected:
    int nV; //number of nodes
    int nE; //number of edges
    vector<set<int>> vec; //representation with adjacency lists
public:

    explicit digraph(istream& src);
    int V() const { return nV; }
    set<int> adj(int u) { return vec[u]; }


};

//implement Kosaraju constructor outside the class
Kosaraju::Kosaraju(digraph& G) {
    int n = G.V();
    marked = vector<bool>(n, false);
    componentId = vector<int>(n, -1);

    //first DFS to compute reverse postorder
    for (int s = 0; s < n; s++)
        if (!marked[s]) dfs(G, s);

    //reset marked array
    fill(marked.begin(), marked.end(), false);

    //second DFS to compute strongly connected components
    int id = 0;
    while (!order.empty()) {
        int u = order.top();
        order.pop();
        if (!marked[u]) {
            reverseDFS(G, u, id);
            id++;
        }
    }
}
//implementing dfs and reverseDFS outside the class
void Kosaraju::dfs(digraph& G, int u) {
    marked[u] = true;
    for (int v : G.adj(u))
        if (!marked[v]) dfs(G, v);
    order.push(u);
}

void Kosaraju::reverseDFS(digraph& G, int u, int id) {
    marked[u] = true;
    componentId[u] = id;
    for (int v : G.adj(u))
        if (!marked[v]) reverseDFS(G, v, id);
}
digraph::digraph(istream& src) {
    nV = nE = 0;
    src >> nV >> nE;
    vec = vector<set<int>>(nV);
    int u, v;
    for (int e = 0; e < nE; e++) {
        src >> u >> v;
        vec[u].insert(v);
    }
}


int main() {
    //creating a directed graph from the example content in the lab
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

    digraph G(graphContent);
    Kosaraju kosaraju(G);    //creating an instance of Kosaraju to find strongly connected components
    int numComponents = kosaraju.strongComponents();    //get the number of strong components
    cout << "Number of Strong Components: " << numComponents << endl;
    //displaying the nodes in each strong component
    for (int i = 0; i < numComponents; ++i) {
        set<int> component = kosaraju.SC(i);
        cout << "Strong Component " << i << ": ";
        for (int node : component) {
            cout << node << " ";
        }
        cout << endl;
    }

    return 0;
}
