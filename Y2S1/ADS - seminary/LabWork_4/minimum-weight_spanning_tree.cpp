#include <iostream>
#include <vector>
#include <algorithm>

struct Edge {
    int src, dest, weight;
};

bool compareEdges(const Edge &a, const Edge &b) {
    return a.weight < b.weight;
}

int findParent(const std::vector<int> &parent, int i) {
    if (parent[i] == -1)
        return i;
    return findParent(parent, parent[i]);
}

void unionSets(std::vector<int> &parent, int x, int y) {
    int rootX = findParent(parent, x);
    int rootY = findParent(parent, y);
    parent[rootX] = rootY;
}

void kruskalMST(const std::vector<Edge> &edges, int numVertices) {
    std::vector<Edge> result;
    std::vector<int> parent(numVertices, -1);

    //sort edges by weight in ascending order
    std::vector<Edge> sortedEdges = edges;
    std::sort(sortedEdges.begin(), sortedEdges.end(), compareEdges);

    for (const auto &edge : sortedEdges) {
        int x = findParent(parent, edge.src);
        int y = findParent(parent, edge.dest);

        if (x != y) {
            result.push_back(edge);
            unionSets(parent, x, y);
        }
    }

    //check if the resulting tree is a spanning tree
    if (result.size() != numVertices - 1) {
        std::cout << "The graph is not connected. No minimum spanning tree exists." << std::endl;
        return;
    }

    //printing the result
    int totalWeight = 0;
    for (const auto &edge : result) {
        std::cout << edge.src << " --> " << edge.dest << " : " << edge.weight << std::endl;
        totalWeight += edge.weight;
    }

    std::cout << "Total weight of the minimum spanning tree: " << totalWeight << std::endl;
}

int main() {
    //example of usage
    std::vector<Edge> edges = {
            {0, 1, 14},
            {1, 3, 3},
            {1, 2, 10},
            {3, 5, 2},
            {5, 4, 9},
    };

    int numVertices = 6;

    kruskalMST(edges, numVertices);

    return 0;
}
