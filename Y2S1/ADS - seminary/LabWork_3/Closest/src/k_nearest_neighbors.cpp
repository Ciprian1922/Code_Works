import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Point {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class KNN {
    static class DistanceComparator implements Comparator<Point> {
        private final double qx, qy;

        DistanceComparator(double qx, double qy) {
            this.qx = qx;
            this.qy = qy;
        }

        @Override
        public int compare(Point p1, Point p2) {
            double dist1 = distance(p1.x, p1.y, qx, qy);
            double dist2 = distance(p2.x, p2.y, qx, qy);

            return Double.compare(dist1, dist2);
        }

        private double distance(double x1, double y1, double x2, double y2) {
            return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }
    }

public static void main(String[] args) {
        try {
            File inputFile = new File("input.txt");
            Scanner scanner = new Scanner(inputFile);

            int n = scanner.nextInt();
            int k = scanner.nextInt();

            List<Point> points = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                points.add(new Point(x, y));
            }

            double qx = scanner.nextDouble();
            double qy = scanner.nextDouble();
            Point queryPoint = new Point(qx, qy);

            List<Point> nearestNeighbors = findNearestNeighbors(points, queryPoint, k);

            for (Point p : nearestNeighbors) {
                System.out.println(p.x + " " + p.y);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

private static List<Point> findNearestNeighbors(List<Point> points, Point queryPoint, int k) {
        PriorityQueue<Point> nearestNeighbors = new PriorityQueue<>(new DistanceComparator(queryPoint.x, queryPoint.y));

        for (Point point : points) {
            nearestNeighbors.offer(point);
            if (nearestNeighbors.size() > k) {
                nearestNeighbors.poll();
            }
        }

        List<Point> result = new ArrayList<>();
        while (!nearestNeighbors.isEmpty()) {
            result.add(nearestNeighbors.poll());
        }

        return result;
    }
}
