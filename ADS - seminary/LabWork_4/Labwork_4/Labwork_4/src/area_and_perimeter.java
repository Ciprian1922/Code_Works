import java.util.Vector;

class Points {
    public double x, y;

    public Points(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //static method to compute the area of a triangle given three points
    public static double area(Intersection_Point A, Intersection_Point B, Intersection_Point C) {
        return 0.5 * Math.abs(A.x * (B.y - C.y) + B.x * (C.y - A.y) + C.x * (A.y - B.y));
    }

    //static method to compute the area of a convex polygon given its vertices in clockwise order
    public static double area(Vector<Intersection_Point> P) {
        int n = P.size();
        double totalArea = 0;

        //iterate through each triangle formed by consecutive vertices
        for (int i = 1; i < n - 1; i++) {
            totalArea += area(P.get(0), P.get(i), P.get(i + 1));
        }

        return totalArea;
    }

    //static method to compute the perimeter of a convex polygon given its vertices in clockwise order
    public static double perimeter(Vector<Intersection_Point> P) {
        int n = P.size();
        double totalPerimeter = 0;

        //iterate through each edge of the polygon
        for (int i = 0; i < n - 1; i++) {
            totalPerimeter += distance(P.get(i), P.get(i + 1));
        }

        //add the distance from the last vertex to the first vertex
        totalPerimeter += distance(P.get(n - 1), P.get(0));

        return totalPerimeter;
    }

    //helper method to compute the Euclidean distance between two points
    private static double distance(Intersection_Point A, Intersection_Point B) {
        return Math.sqrt(Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2));
    }

    public static void main(String[] args) {
        //example usage
        Vector<Intersection_Point> polygon = new Vector<>();
        polygon.add(new Intersection_Point(0, 0));
        polygon.add(new Intersection_Point(4, 0));
        polygon.add(new Intersection_Point(4, 3));
        polygon.add(new Intersection_Point(0, 3));

        double polygonArea = area(polygon);
        double polygonPerimeter = perimeter(polygon);

        System.out.println("Polygon Area: " + polygonArea);
        System.out.println("Polygon Perimeter: " + polygonPerimeter);
    }
}
