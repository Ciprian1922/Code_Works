public class Segment {
    public Point3D A, B; //the coordinates of the endpoints of the segment

    public Segment(Point3D A, Point3D B) {
        this.A = A;
        this.B = B;
    }
    static class Point3D {
        public double x, y, z;

        public Point3D(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    //static method to determine the relative position of two segments
    public static int above(Segment s1, Segment s2) {
        //get the projections of segments s1 and s2 on the plane xoy
        Segment s1Prime = new Segment(new Point3D(s1.A.x, s1.A.y, 0), new Point3D(s1.B.x, s1.B.y, 0));
        Segment s2Prime = new Segment(new Point3D(s2.A.x, s2.A.y, 0), new Point3D(s2.B.x, s2.B.y, 0));

        //check if the projections s1' and s2' intersect
        Point3D intersectionPoint = intersect(s1Prime.A, s1Prime.B, s2Prime.A, s2Prime.B);
        if (intersectionPoint != null) {
            //the segments intersect
            return 0;
        }

        //calculate the z-coordinates of the intersection points r1 and r2
        double t1 = (s1.A.z + s1.B.z) / 2.0; //assuming the z-coordinate of s1 is the average of A and B
        double t2 = (s2.A.z + s2.B.z) / 2.0; //assuming the z-coordinate of s2 is the average of A and B

        //check if s1 is above s2 or vice versa
        if (t1 >= t2) {
            return 1; //s1 is above s2
        } else {
            return 2; //s2 is above s1
        }
    }

    //helper method to check if the line segments AB and CD intersect
    private static Point3D intersect(Point3D A, Point3D B, Point3D C, Point3D D) {
        //calculate the intersection point using vector cross product
        double det = (B.x - A.x) * (D.y - C.y) - (B.y - A.y) * (D.x - C.x);
        if (det == 0) {
            //segments are parallel or collinear
            return null;
        }

        double u = ((C.x - A.x) * (D.y - C.y) - (C.y - A.y) * (D.x - C.x)) / det;
        double v = ((C.x - A.x) * (B.y - A.y) - (C.y - A.y) * (B.x - A.x)) / det;

        if (u >= 0 && u <= 1 && v >= 0 && v <= 1) {
            //intersection point lies within both segments
            double x = A.x + u * (B.x - A.x);
            double y = A.y + u * (B.y - A.y);
            double z = A.z + u * (B.z - A.z);
            return new Point3D(x, y, z);
        } else {
            //segments do not intersect
            return null;
        }
    }

    public static void main(String[] args) {
        //example usage
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D q1 = new Point3D(4, 4, 4);
        Point3D p2 = new Point3D(2, 2, 2);
        Point3D q2 = new Point3D(5, 5, 5);

        Segment s1 = new Segment(p1, q1);
        Segment s2 = new Segment(p2, q2);

        int result = above(s1, s2);

        if (result == 0) {
            System.out.println("Segments intersect at " + intersect(s1.A, s1.B, s2.A, s2.B));
        } else if (result == 1) {
            System.out.println("s1 is above s2.");
        } else if (result == 2) {
            System.out.println("s2 is above s1.");
        } else {
            System.out.println("Segments do not intersect.");
        }
    }
}
