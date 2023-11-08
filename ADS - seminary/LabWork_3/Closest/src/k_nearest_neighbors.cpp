#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <algorithm>

struct Point {
    double x, y, z;
    double distance;

    Point(double x, double y, double z) : x(x), y(y), z(z), distance(0) {}

    Point(double x, y, z, dist) : x(x), y(y), z(z), distance(dist) {}

    double distanceTo(double qx, double qy, double qz) const {
        return (x - qx) * (x - qx) + (y - qy) * (y - qy) + (z - qz) * (z - qz);
    }

    bool operator<(const Point& other) const {
        return distance < other.distance;
    }
};

struct Node {
    double x, y, z;
    Node* left;
    Node* right;

    Node(double x, double y, double z) : x(x), y(y), z(z), left(nullptr), right(nullptr) {}
};

class KDTree {
public:
    KDTree(const std::vector<Point>& points) {
        root = buildTree(points, 0);
    }

    std::priority_queue<Point> findNearestNeighbors(const Point& queryPoint, int k) {
        std::priority_queue<Point> nearestNeighbors;

        findNearestNeighbors(root, queryPoint, k, nearestNeighbors, 0);

        return nearestNeighbors;
    }

private:
    Node* root;

    Node* buildTree(const std::vector<Point>& points, int depth) {
        if (points.empty()) {
            return nullptr;
        }

        int axis = depth % 3;
        if (axis == 0) {
            std::sort(points.begin(), points.end(), [](const Point& a, const Point& b) {
                return a.x < b.x;
            });
        } else if (axis == 1) {
            std::sort(points.begin(), points.end(), [](const Point& a, const Point& b) {
                return a.y < b.y;
            });
        } else {
            std::sort(points.begin(), points.end(), [](const Point& a, const Point& b) {
                return a.z < b.z;
            });
        }

        int median = points.size() / 2;
        Node* node = new Node(points[median].x, points[median].y, points[median].z);
        node->left = buildTree(std::vector<Point>(points.begin(), points.begin() + median), depth + 1);
        node->right = buildTree(std::vector<Point>(points.begin() + median + 1, points.end()), depth + 1);

        return node;
    }

    void findNearestNeighbors(Node* node, const Point& queryPoint, int k, std::priority_queue<Point>& nearestNeighbors, int depth) {
        if (!node) {
            return;
        }

        double distance = node->distanceTo(queryPoint.x, queryPoint.y, queryPoint.z);
        Point point(node->x, node->y, node->z, distance);

        if (nearestNeighbors.size() < k || distance < nearestNeighbors.top().distance) {
            nearestNeighbors.push(point);
            if (nearestNeighbors.size() > k) {
                nearestNeighbors.pop();
            }

            int axis = depth % 3;
            if (axis == 0) {
                if (queryPoint.x < node->x) {
                    findNearestNeighbors(node->left, queryPoint, k, nearestNeighbors, depth + 1);
                } else {
                    findNearestNeighbors(node->right, queryPoint, k, nearestNeighbors, depth + 1);
                }
            } else if (axis == 1) {
                if (queryPoint.y < node->y) {
                    findNearestNeighbors(node->left, queryPoint, k, nearestNeighbors, depth + 1);
                } else {
                    findNearestNeighbors(node->right, queryPoint, k, nearestNeighbors, depth + 1);
                }
            } else {
                if (queryPoint.z < node->z) {
                    findNearestNeighbors(node->left, queryPoint, k, nearestNeighbors, depth + 1);
                } else {
                    findNearestNeighbors(node->right, queryPoint, k, nearestNeighbors, depth + 1);
                }
            }
        }
    }
};

int main() {
    std::ifstream inputFile("input.txt");
    if (!inputFile) {
        std::cerr << "Failed to open input.txt" << std::endl;
        return 1;
    }

    int n, k;
    inputFile >> n >> k;
    std::vector<Point> points;

    for (int i = 0; i < n; i++) {
        double x, y, z;
        inputFile >> x >> y >> z;
        points.emplace_back(x, y, z);
    }

    double xQuery, yQuery, zQuery;
    inputFile >> xQuery >> yQuery >> zQuery;
    Point queryPoint(xQuery, yQuery, zQuery);

    KDTree kdTree(points);
    std::priority_queue<Point> nearestNeighbors = kdTree.findNearestNeighbors(queryPoint, k);

    while (!nearestNeighbors.empty()) {
        Point p = nearestNeighbors.top();
        std::cout << p.x << " " << p.y << " " << p.z << std::endl;
        nearestNeighbors.pop();
    }

    inputFile.close();

    return 0;
}
