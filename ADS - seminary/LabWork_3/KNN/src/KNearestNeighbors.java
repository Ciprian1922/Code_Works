import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Point {
    double x, y, z;

    Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    double distanceTo(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2) + Math.pow(this.z - other.z, 2));
    }

    // This comparator is used to compare points based on their distance to a query point
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

class KDTree {
    static class Node {
        double x, y, z;
        Node left, right;

        Node(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    KDTree(List<Point> points) {
        root = buildTree(points, 0);
    }

    private Node buildTree(List<Point> points, int depth) {
        if (points.isEmpty()) {
            return null;
        }

        int axis = depth % 3;
        if (axis == 0) {
            points.sort(Comparator.comparingDouble(p -> p.x));
        } else if (axis == 1) {
            points.sort(Comparator.comparingDouble(p -> p.y));
        } else {
            points.sort(Comparator.comparingDouble(p -> p.z));
        }

        int median = points.size() / 2;
        Point medianPoint = points.get(median);

        Node node = new Node(medianPoint.x, medianPoint.y, medianPoint.z);
        node.left = buildTree(new ArrayList<>(points.subList(0, median)), depth + 1);
        node.right = buildTree(new ArrayList<>(points.subList(median + 1, points.size())), depth + 1);

        return node;
    }

    PriorityQueue<Point> findNearestNeighbors(Point queryPoint, int k) {
        PriorityQueue<Point> nearestNeighbors = new PriorityQueue<>(Comparator.comparingDouble(p -> -p.distanceTo(queryPoint)));

        findNearestNeighbors(root, queryPoint, k, nearestNeighbors, 0);

        return nearestNeighbors;
    }

    private void findNearestNeighbors(Node node, Point queryPoint, int k, PriorityQueue<Point> nearestNeighbors, int depth) {
        if (node == null) {
            return;
        }

        Point point = new Point(node.x, node.y, node.z);
        double distance = point.distanceTo(queryPoint);

        if (nearestNeighbors.size() < k || distance < nearestNeighbors.peek().distanceTo(queryPoint)) {
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

public class KNearestNeighbors {
    public static void main(String[] args) {
        try {
            File inputFile = new File("src/input.txt");
            Scanner scanner = new Scanner(inputFile);

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
            PriorityQueue<Point> nearestNeighbors = kdTree.findNearestNeighbors(queryPoint, k);

            while (!nearestNeighbors.isEmpty()) {
                Point p = nearestNeighbors.poll();
                System.out.println(p.x + " " + p.y + " " + p.z);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
