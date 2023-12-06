public class Intersection_Point {
    public double x, y;

    public Intersection_Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //static method to check if the line segments AB and CD intersect
    public static Intersection_Point intersect(Intersection_Point A, Intersection_Point B, Intersection_Point C, Intersection_Point D) {
        //calculate the slopes of the line segments
        double slopeAB = (B.y - A.y) / (B.x - A.x);
        double slopeCD = (D.y - C.y) / (D.x - C.x);

        //check if the line segments are parallel
        if (slopeAB == slopeCD) {
            //line segments are either parallel or collinear
            if (isOnSegment(A, B, C) || isOnSegment(A, B, D) || isOnSegment(C, D, A) || isOnSegment(C, D, B)) {
                //line segments are collinear and overlapping
                return A; //return any point on the intersection
            } else {
                //line segments are parallel and non-intersecting
                return null;
            }
        }

        //calculate the intersection point
        double x = ((C.y - A.y) + slopeAB * (A.x * slopeAB - C.x * slopeAB + C.y - A.y)) / (slopeAB - slopeCD);
        double y = A.y + slopeAB * (x - A.x);

        //check if the intersection point lies on both line segments
        Intersection_Point intersectionIntersectionPoint = new Intersection_Point(x, y);
        if (isOnSegment(A, B, intersectionIntersectionPoint) && isOnSegment(C, D, intersectionIntersectionPoint)) {
            return intersectionIntersectionPoint;
        } else {
            return null;
        }
    }

    //method to check if a point P lies on a line segment AB
    private static boolean isOnSegment(Intersection_Point A, Intersection_Point B, Intersection_Point P) {
        return (P.x >= Math.min(A.x, B.x) && P.x <= Math.max(A.x, B.x) &&
                P.y >= Math.min(A.y, B.y) && P.y <= Math.max(A.y, B.y));
    }

    public static void main(String[] args) {
        //example usage
        Intersection_Point A = new Intersection_Point(1, 1);
        Intersection_Point B = new Intersection_Point(4, 4);
        Intersection_Point C = new Intersection_Point(1, 4);
        Intersection_Point D = new Intersection_Point(4, 1);

        Intersection_Point intersectionIntersectionPoint = intersect(A, B, C, D);

        if (intersectionIntersectionPoint != null) {
            System.out.println("Intersection Point: (" + intersectionIntersectionPoint.x + ", " + intersectionIntersectionPoint.y + ")");
        } else {
            System.out.println("Segments do not intersect.");
        }
    }
}
