import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class Point {
    double x, y, z;
    double distance;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double x, double y, double z, double distance) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = distance;
    }

    public double distanceTo(double qx, double qy, double qz) {
        return (x - qx) * (x - qx) + (y - qy) * (y - qy) + (z - qz) * (z - qz);
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
}

class Node {
    double x, y, z;
    Node left, right;

    public Node(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.left = null;
        this.right = null;
    }

    public double distanceTo(double qx, double qy, double qz) {
        return (x - qx) * (x - qx) + (y - qy) * (y - qy) + (z - qz) * (z - qz);
    }
}

class KDTree {
    private Node root;

    public KDTree(List<Point> points) {
        this.root = buildTree(points, 0);
    }

    private Node buildTree(List<Point> points, int depth) {
        if (points.isEmpty()) {
            return null;
        }

        int axis = depth % 3;
        points.sort((p1, p2) -> {
            if (axis == 0) return Double.compare(p1.x, p2.x);
            if (axis == 1) return Double.compare(p1.y, p2.y);
            return Double.compare(p1.z, p2.z);
        });

        int median = points.size() / 2;
        Node node = new Node(points.get(median).x, points.get(median).y, points.get(median).z);
        node.left = buildTree(points.subList(0, median), depth + 1);
        node.right = buildTree(points.subList(median + 1, points.size()), depth + 1);

        return node;
    }

    public Queue<Point> findNearestNeighbors(Point queryPoint, int k) {
        Queue<Point> nearestNeighbors = new PriorityQueue<>(k, (p1, p2) -> Double.compare(p2.distance, p1.distance));
        findNearestNeighbors(root, queryPoint, k, nearestNeighbors, 0);
        return nearestNeighbors;
    }

    private void findNearestNeighbors(Node node, Point queryPoint, int k, Queue<Point> nearestNeighbors, int depth) {
        if (node == null) {
            return;
        }

        double distance = node.distanceTo(queryPoint.x, queryPoint.y, queryPoint.z);
        Point point = new Point(node.x, node.y, node.z, distance);

        if (nearestNeighbors.size() < k || distance < nearestNeighbors.peek().distance) {
            nearestNeighbors.offer(point);
            if (nearestNeighbors.size() > k) {
                nearestNeighbors.poll();
            }

            int axis = depth % 3;
            if (axis == 0) {
                if (queryPoint.x < node.x) {
                    findNearestNeighbors(node.left, queryPoint, k, nearestNeighbors, depth + 1);
                } else {
                    findNearestNeighbors(node.right, queryPoint, k, nearestNeighbors, depth + 1);
                }
            } else if (axis == 1) {
                if (queryPoint.y < node.y) {
                    findNearestNeighbors(node.left, queryPoint, k, nearestNeighbors, depth + 1);
                } else {
                    findNearestNeighbors(node.right, queryPoint, k, nearestNeighbors, depth + 1);
                }
            } else {
                if (queryPoint.z < node.z) {
                    findNearestNeighbors(node.left, queryPoint, k, nearestNeighbors, depth + 1);
                } else {
                    findNearestNeighbors(node.right, queryPoint, k, nearestNeighbors, depth + 1);
                }
            }
        }
    }
}

public class KNearestNeighborsKDTree {
    public static void main(String[] args) throws IOException {
        File inputFile = new File("C:\\Users\\Ciprian\\Desktop\\GitHub_UVT\\Code_Works\\ADS - seminary\\LabWork_3\\Closest\\src\\input.txt");
        FileInputStream inputStream = new FileInputStream(inputFile);
        Scanner scanner = new Scanner(inputStream);

        int n = scanner.nextInt();
        int k = scanner.nextInt();
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            double z = scanner.nextDouble();
            points.add(new Point(x, y, z));
        }

        double xQuery = scanner.nextDouble();
        double yQuery = scanner.nextDouble();
        double zQuery = scanner.nextDouble();
        Point queryPoint = new Point(xQuery, yQuery, zQuery);

        KDTree kdTree = new KDTree(points);

        Queue<Point> nearestNeighbors = kdTree.findNearestNeighbors(queryPoint, k);

        while (!nearestNeighbors.isEmpty()) {
            System.out.println(nearestNeighbors.poll());
        }

        scanner.close();
        inputStream.close();
    }
}
