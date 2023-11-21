import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Represents a point in 3D space
class Point {
    double x, y, z;

    // Constructor to initialize the coordinates of the point
    Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Calculates the Euclidean distance between two points
    double distanceTo(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2) + Math.pow(this.z - other.z, 2));
    }

    // Comparator for sorting points based on their distance to a query point
    static class DistanceComparator implements Comparator<Point> {
        private final Point queryPoint;

        DistanceComparator(Point queryPoint) {
            this.queryPoint = queryPoint;
        }

        @Override
        public int compare(Point p1, Point p2) {
            double dist1 = p1.distanceTo(queryPoint);
            double dist2 = p2.distanceTo(queryPoint);

            return Double.compare(dist1, dist2);
        }
    }
}

// Represents a node in the KD tree
class KDTree {
    static class Node {
        double x, y, z;
        Node left, right;

        // Constructor to initialize the node with coordinates
        Node(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    // Constructor to build the KD tree from a list of points
    KDTree(List<Point> points) {
        root = buildTree(points, 0);
    }

    // Recursively builds the KD tree
    private Node buildTree(List<Point> points, int depth) {
        if (points.isEmpty()) {
            return null;
        }

        // Determine the axis to split based on depth in the tree
        int axis = depth % 3;
        if (axis == 0) {
            points.sort(Comparator.comparingDouble(p -> p.x));
        } else if (axis == 1) {
            points.sort(Comparator.comparingDouble(p -> p.y));
        } else {
            points.sort(Comparator.comparingDouble(p -> p.z));
        }

        // Find the median point along the current axis
        int median = points.size() / 2;
        Point medianPoint = points.get(median);

        // Create a node and recursively build left and right subtrees
        Node node = new Node(medianPoint.x, medianPoint.y, medianPoint.z);
        node.left = buildTree(new ArrayList<>(points.subList(0, median)), depth + 1);
        node.right = buildTree(new ArrayList<>(points.subList(median + 1, points.size())), depth + 1);

        return node;
    }

    // Finds k nearest neighbors to a query point
    PriorityQueue<Point> findNearestNeighbors(Point queryPoint, int k) {
        // Use a max heap to keep track of the k nearest neighbors
        PriorityQueue<Point> nearestNeighbors = new PriorityQueue<>(Comparator.comparingDouble(p -> -p.distanceTo(queryPoint)));

        // Call the recursive helper function
        findNearestNeighbors(root, queryPoint, k, nearestNeighbors, 0);

        return nearestNeighbors;
    }

    // Recursive helper function to find nearest neighbors
    private void findNearestNeighbors(Node node, Point queryPoint, int k, PriorityQueue<Point> nearestNeighbors, int depth) {
        if (node == null) {
            return;
        }

        // Create a point from the current node
        Point point = new Point(node.x, node.y, node.z);
        double distance = point.distanceTo(queryPoint);

        // Update the nearest neighbors if the current point is closer
        if (nearestNeighbors.size() < k || distance < nearestNeighbors.peek().distanceTo(queryPoint)) {
            nearestNeighbors.offer(point);
            if (nearestNeighbors.size() > k) {
                nearestNeighbors.poll();
            }

            // Determine the axis to traverse based on depth in the tree
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

// Main class to run the k-nearest neighbors algorithm
public class KNearestNeighbors {
    public static void main(String[] args) {
        try {
            // Read input from the file
            File inputFile = new File("src/input.txt");
            Scanner scanner = new Scanner(inputFile);

            // Read the total number of points (n) and the number of nearest neighbors to find (k)
            int n = scanner.nextInt();
            int k = scanner.nextInt();

            // Read the coordinates of n points in 3D space
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                double z = scanner.nextDouble();
                points.add(new Point(x, y, z));
            }

            // Read the coordinates of the query point
            double xQuery = scanner.nextDouble();
            double yQuery = scanner.nextDouble();
            double zQuery = scanner.nextDouble();
            Point queryPoint = new Point(xQuery, yQuery, zQuery);

            // Build the KD tree from the list of points
            KDTree kdTree = new KDTree(points);

            // Find the k nearest neighbors to the query point
            PriorityQueue<Point> nearestNeighbors = kdTree.findNearestNeighbors(queryPoint, k);

            // Print the coordinates of the k nearest neighbors
            while (!nearestNeighbors.isEmpty()) {
                Point p = nearestNeighbors.poll();
                System.out.println(p.x + " " + p.y + " " + p.z);
            }

            // Close the scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
