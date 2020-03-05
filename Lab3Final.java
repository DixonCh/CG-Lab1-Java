import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Lab3Final {

    static ArrayList<Point> points = new ArrayList<>();
    static CartesianToPolar cartesianToPolar;
    static ArrayList<PolarCoordinates> polarCoordinates;
    static int vertexNo;
    private static Scanner scan = new Scanner(System.in);
    private static Point qPoint;
    private static Point rayEndPoint = new Point(500, 477);
    static float centerX, centerY;


    public static void main(String[] args) {
        readPoints();
        getCentroid();
        cartesianToPolar = new CartesianToPolar(points, centerX, centerY);
        cartesianToPolar.convertToPolar();
        polarCoordinates = cartesianToPolar.pCoordinates;
        //System.out.println(cartesianToPolar.toString());
        System.out.println("Input: " + points);
        sortPoints();
        System.out.println("Sorted Points: " + points);

        if (checkConvex()) {
            System.out.println("\nThe polygon is convex");
            useTurnTest();
        } else {
            System.out.println("\nThe polygon is not convex");
            useRayCasting();

        }
    }

    private static void sortPoints() {

        PolarCoordinates tempPolarCoordinate;
        Point tempPoint;
        for (int i = 0; i < points.size(); i++) {
            for (int j = 1; j < (points.size() - i); j++) {
                if (polarCoordinates.get(j - 1).theta > polarCoordinates.get(j).theta) {
                    tempPolarCoordinate = polarCoordinates.get(j - 1);
                    polarCoordinates.set(j - 1, polarCoordinates.get(j));
                    polarCoordinates.set(j, tempPolarCoordinate);

                    tempPoint = points.get(j - 1);
                    points.set(j - 1, points.get(j));
                    points.set(j, tempPoint);
                }
            }
        }

        //System.out.println(polarCoordinates);
        //System.out.println(points);

    }

    private static void readPoints() {
        try {
            File file = new File("src\\coordinates.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i = 0;
            String st;
            while ((st = br.readLine()) != null) {
                String[] data = st.split(",");
                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                points.add(new Point(x, y));
                i++;
            }
            vertexNo = i;
            System.out.println(vertexNo);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean checkConvex() {
        int b, c;

        for (int i = 0; i < vertexNo; i++) {
            b = (i + 1) % vertexNo;
            c = (i + 2) % vertexNo;

            if (getAreaOfTriangle(points.get(i), points.get(b), points.get(c)) > 0) {
                return false;
            }
        }

        return true;
    }

    private static double getAreaOfTriangle(Point a, Point b, Point c) {
        return 0.5 * (a.x * (c.y - b.y) + c.x * (b.y - a.y) + b.x * (a.y - c.y));
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

    private static void useTurnTest() {

        setQueryPoint();
        System.out.println("\nUsing Turn Test:");

        int b;
        boolean isInside = true;

        for (int i = 0; i < vertexNo; i++) {

            b = (i + 1) % vertexNo;
            if (getAreaOfTriangle(qPoint, points.get(i), points.get(b)) > 0) {
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

    private static void useRayCasting() {

        setQueryPoint();
        System.out.println("\nUsing Ray Casting:");

        Segment s1 = new Segment(qPoint, rayEndPoint);
        Segment s2;
        int b;
        int intersectCount = 0;
        for (int i = 0; i < vertexNo; i++) {

            b = (i + 1) % vertexNo;
            s2 = new Segment(points.get(i), points.get(b));
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

    private static void getCentroid() {
        float sumX=0, sumY=0;
        for(Point p: points){
            sumX = sumX + p.x;
            sumY = sumY + p.y;
        }
        centerX = sumX/points.size();
        centerY = sumY/points.size();

        // System.out.println("Centroid: ("+centerX+","+centerY+")");
    }

}
