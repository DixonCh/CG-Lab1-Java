import java.util.ArrayList;
import java.util.Scanner;

public class Lab3 {

    private static ArrayList<Point> vertexList = new ArrayList<>();
    private static Scanner scan = new Scanner(System.in);
    private static int vertexNo, choice;
    private static Point qPoint;
    private static Point rayEndPoint = new Point(700, 800);

    public static void main(String args[]) {

        setPolygonData();

        if (checkConvex()) {
            System.out.println("\nThe polygon is convex");
            displayMenu();
        } else {
            System.out.println("\nThe polygon is not convex");
            useRayCasting();
        }

    }

    private static void setQueryPoint() {
        int x, y;
        System.out.println("\nEnter a query point: ");
        System.out.print("x = ");
        x = scan.nextInt();
        System.out.print("y = ");
        y = scan.nextInt();

        qPoint = new Point(x, y);

    }

    private static void displayMenu() {

        System.out.println("\nSelect an option");
        System.out.println("1. Perform Turn Test");
        System.out.println("2. Perform Ray Casting");
        choice = scan.nextInt();

        switch (choice) {
            case 1:
                useTurnTest();            //point inclusion using Turn test
                break;
            case 2:
                useRayCasting();          //point inclusion using ray casting
                break;
        }
    }

    private static void setPolygonData() {

        int x, y;
        System.out.print("Enter the number of vertices of a polygon: ");
        vertexNo = scan.nextInt();

        for (int i = 0; i < vertexNo; i++) {
            System.out.println("Enter point " + (i + 1) + ":");
            System.out.print("x = ");
            x = scan.nextInt();
            System.out.print("y = ");
            y = scan.nextInt();
            vertexList.add(new Point(x, y));
        }
    }

    private static boolean checkConvex() {
        int b, c;

        for (int i = 0; i < vertexNo; i++) {

            b = (i + 1) % vertexNo;
            c = (i + 2) % vertexNo;

            if (getAreaOfTriangle(vertexList.get(i), vertexList.get(b), vertexList.get(c)) > 0) {
                return false;
            }
        }

        return true;
    }

    private static void useRayCasting() {

        setQueryPoint();
        System.out.println("\nUsing Ray Casting:");

        Segment s1 = new Segment(qPoint, rayEndPoint);
        Segment s2;
        int b;
        int intersectCount = 0;
        for (int i = 0; i < vertexNo; i++) {

            b = (i + 1) % vertexNo;
            s2 = new Segment(vertexList.get(i), vertexList.get(b));
            if (hasIntersection(s1, s2)) {
                intersectCount++;
            }
        }

        if(intersectCount%2 == 0){
            System.out.println("The point " + qPoint + " lies outside the polygon");
        }else{
            System.out.println("The point " + qPoint + " lies inside the polygon");
        }

        useRayCasting();
    }

    private static void useTurnTest() {

        setQueryPoint();
        System.out.println("\nUsing Turn Test:");

        int b;
        boolean isInside = true;

        for (int i = 0; i < vertexNo; i++) {

            b = (i + 1) % vertexNo;
            if (getAreaOfTriangle(qPoint, vertexList.get(i), vertexList.get(b)) > 0) {
                isInside = false;
                break;
            }
        }
        if (isInside) {
            System.out.println("The point " + qPoint + " lies inside the polygon");
        } else {
            System.out.println("The point " + qPoint + " lies outside the polygon");
        }

        useTurnTest();

    }

    private static boolean hasIntersection(Segment s1, Segment s2) {  //if intersection, returns true

        Point a = s1.p;
        Point b = s1.q;
        Point c = s2.p;
        Point d = s2.q;

        int abcArea, abdArea, cdaArea, cdbArea = 0;

        if (getAreaOfTriangle(a, b, c) < 0) {
            abcArea = -1;
        } else {
            abcArea = 1;
        }

        if (getAreaOfTriangle(a, b, d) < 0) {
            abdArea = -1;
        } else {
            abdArea = 1;
        }

        if (getAreaOfTriangle(c, d, a) < 0) {
            cdaArea = -1;
        } else {
            cdaArea = 1;
        }
        if (getAreaOfTriangle(c, d, b) < 0) {
            cdbArea = -1;
        } else {
            cdbArea = 1;
        }

        if (abcArea != abdArea && cdaArea != cdbArea) {
            return true;
        }
        return false;
    }

    private static double getAreaOfTriangle(Point a, Point b, Point c) {
        return 0.5 * (a.x * (c.y - b.y) + c.x * (b.y - a.y) + b.x * (a.y - c.y));
    }

}
