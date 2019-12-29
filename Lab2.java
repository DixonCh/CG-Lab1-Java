import java.util.ArrayList;
import java.util.Scanner;

public class Lab2 {
    public static void main(String args[]) {

        //testPLC();  Lab 1
        while(true) {
            checkForIntersection();
            System.out.println("");
            System.out.println("===================================");
            System.out.println("===================================");
            System.out.println("");
        }
    }

    private static void testPLC() {

        int x1, y1, x2, y2, x, y;
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter first point of the line segment:");
        System.out.print("x = ");
        x1 = scan.nextInt();
        System.out.print("y = ");
        y1 = scan.nextInt();

        System.out.println("Enter second point of the line segment:");
        System.out.print("x = ");
        x2 = scan.nextInt();
        System.out.print("y = ");
        y2 = scan.nextInt();

        System.out.println("Enter any point to test:");
        System.out.print("x = ");
        x = scan.nextInt();
        System.out.print("y = ");
        y = scan.nextInt();

        Point a = new Point(x1, y1);
        Point b = new Point(x2, y2);
        Point c = new Point(x, y);


        if (isCollinear(a, b, c)) {
            isCollinearInsideSegment(a, b, c);
        } else {
            checkLeftRight(a, b, c);
        }

    }

    private static void checkLeftRight(Point a, Point b, Point c) {

        Double areaOfTriangle = getAreaofTriangle(a, b, c);

        if (areaOfTriangle < 0) {
            System.out.println("The point " + c + " lies on the left side of the line segment");
        } else if (areaOfTriangle > 0) {
            System.out.println("The point " + c + " lies on the right side of the line segment");
        }
    }

    private static void checkForIntersection() {

        ArrayList<Segment> segmentList = getInputSegments();

        if (checkCollinearInsideSegments(segmentList)) {
            System.out.println("The two segments are intersected with each other");
        } else if (hasIntersection(segmentList)) {
            System.out.println("The two segments are intersected with each other");
        } else {
            System.out.println();
            System.out.println("There is no intersection between the segments");
        }
    }

    private static ArrayList<Segment> getInputSegments() {
        int x1, y1, x2, y2;
        Scanner scan = new Scanner(System.in);
        ArrayList<Segment> segmentList = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            System.out.println();
            System.out.println("Enter points for line segment s" + i);
            System.out.println("Enter the start point for s" + i);
            System.out.print("Enter x = ");
            x1 = scan.nextInt();
            System.out.print("Enter y = ");
            y1 = scan.nextInt();
            System.out.println("Enter the end point for s" + i);
            System.out.print("Enter x = ");
            x2 = scan.nextInt();
            System.out.print("Enter y = ");
            y2 = scan.nextInt();

            segmentList.add(new Segment(
                    new Point(x1, y1),
                    new Point(x2, y2)
            ));
        }

        return segmentList;
    }

    private static boolean checkCollinearInsideSegments(ArrayList<Segment> sList) {

        Point a = sList.get(0).p;
        Point b = sList.get(0).q;
        Point c = sList.get(1).p;
        Point d = sList.get(1).q;

        if (isCollinear(a, b, c)) { //if collinear, check for intersection
            if (isCollinearInsideSegment(a, b, c)) { //if true there is intersection
                return true;
            }
        }
        if (isCollinear(a, b, d)) {
            if (isCollinearInsideSegment(a, b, d)) { //if true there is intersection
                return true;
            }
        }

        return false;
    }

    private static Boolean isCollinear(Point a, Point b, Point c) {

        if (getAreaofTriangle(a, b, c) == 0) {
         //   System.out.println("The points are collinear");
            return true;
        }
        return false;
    }

    private static Boolean isCollinearInsideSegment(Point a, Point b, Point c) {
        if (c.x == a.x && c.y == a.y) {       // lies at start
           // System.out.println("The point " + c + " lies at start");
            return true;
        }
        if (c.x == b.x && c.y == b.y) {     //lies at end
           // System.out.println("The point " + c + " lies at terminus");
            return true;
        }
        if ((float) getDistance(a, b) == (float) (getDistance(a, c) + getDistance(b, c))) { //lies between start and end
            //System.out.println("The point " + c + " lies between " + a + " and " + b);
            return true;
        }

        if (getDistance(a, c) < getDistance(b, c)) {  // lies behind the line segment
            //System.out.println("The point " + c + " lies behind the line segment");
            return false;
        }

        if (getDistance(a, c) > getDistance(b, c)) { //lies beyond the line segment
           // System.out.println("The point " + c + " lies beyond the line segment");
            return false;
        }


        return false;
    }

    private static boolean hasIntersection(ArrayList<Segment> sList) {

        Point a = sList.get(0).p;
        Point b = sList.get(0).q;
        Point c = sList.get(1).p;
        Point d = sList.get(1).q;

        int abcArea, abdArea, cdaArea, cdbArea = 0;

        if (getAreaofTriangle(a, b, c) < 0) {
            abcArea = -1;
        } else {
            abcArea = 1;
        }

        if (getAreaofTriangle(a, b, d) < 0) {
            abdArea = -1;
        } else {
            abdArea = 1;
        }

        if (getAreaofTriangle(c, d, a) < 0) {
            cdaArea = -1;
        } else {
            cdaArea = 1;
        }
        if (getAreaofTriangle(c, d, b) < 0) {
            cdbArea = -1;
        } else {
            cdbArea = 1;
        }

        if (abcArea != abdArea && cdaArea != cdbArea) {
            return true;
        }
        return false;
    }

    private static double getAreaofTriangle(Point a, Point b, Point c) {
        return 0.5 * (a.x * (c.y - b.y) + c.x * (b.y - a.y) + b.x * (a.y - c.y));
    }

    private static double getDistance(Point a, Point b) {
        return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
    }
}
