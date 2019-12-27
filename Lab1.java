import java.util.Scanner;

public class Lab1 {

    static Segment s;
    static int x1, x2, y1, y2;
    static int x, y;
    static Point p1, p2, p;

    public static void main(String args[]) {

        getData();
        while (true) {
            if (!checkCollinear()) {
                checkLeftRight();
            }
        }

    }

    private static void getData() {

        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter start point p1 of the line segment");
        System.out.print("x1 = ");
        x1 = scan.nextInt();
        System.out.print("y1 = ");
        y1 = scan.nextInt();
        p1 = new Point(x1, y1);

        System.out.println();
        System.out.println("Enter end point p2 of the line segment");
        System.out.print("x2 = ");
        x2 = scan.nextInt();
        System.out.print("y2 = ");
        y2 = scan.nextInt();
        p2 = new Point(x2, y2);

        s = new Segment(p1, p2);


    }

    private static boolean checkCollinear() {

        boolean isCollinear = true;

        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter a query point:");
        System.out.print("x = ");
        x = scan.nextInt();
        System.out.print("y = ");
        y = scan.nextInt();
        p = new Point(x, y);

        float slope = (float) (y2 - y1) / (x2 - x1);
        float newSlope = (float) (y - y1) / (x - x1);

        System.out.println();

        if (p.x == p1.x && p.y == p1.y) {
            System.out.println("The point p" + p + " is at the start");
        } else if (p.x == p2.x && p.y == p2.y) {
            System.out.println("The point p" + p + " is at the terminus");
        } else {
            if (slope == newSlope) {

                if ((float) getDistance(p1, p2) == (float) (getDistance(p1, p) + getDistance(p2, p))) {
                    System.out.println("The point p" + p + "lies between p1 and p2");
                } else if (getDistance(p1, p) < getDistance(p2, p)) {
                    System.out.println("The point p" + p + "lies behind the line segment");
                } else {
                    System.out.println("The point p" + p + "lies beyond the line segment");
                }
            } else {
                isCollinear = false;
            }
        }

        return isCollinear;

    }

    private static void checkLeftRight() {

        Double areaofTriangle = getAreaofTriangle(p1, p, p2);

        if (areaofTriangle < 0) {
            System.out.println("The point p" + p + " is on the left side of the line segment");
        } else if (areaofTriangle > 0) {
            System.out.println("The point p" + p + "is on the right side of the line segment");
        }
    }

    private static double getDistance(Point a, Point b) {
        return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
    }

    private static double getAreaofTriangle(Point a, Point b, Point c) {

        return 0.5 * (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));
    }

   /* private static void checkInsideLine(Point a, Point b, Point c) {


        if(a.x > c.x && a.y > c.y){
            System.out.println("behind");
        }
        if(b.x < c.x && b.y < c.y){
            System.out.println("beyond");
        }

        if(a.x == c.x && a.y == c.y){
            System.out.println("Start Point");
        }

        if(b.x == c.x && b.y == c.y){
            System.out.println("Terminus point");
        }


        if (a.x <= c.x && a.y <= c.y && b.x >= c.x && b.y >= c.y){
            System.out.println("Point is inside");
        }

    }
*/

}
