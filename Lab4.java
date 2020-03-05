import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.atan2;

public class Lab4 {

    static ArrayList<Point> points = new ArrayList<>();
    private static Scanner scan = new Scanner(System.in);


    public static void main(String[] args) {
        readPoints();
        displayMenu();

    }

    private static void displayMenu() {


        System.out.println("\nSelect an option");
        System.out.println("1. Extreme Point");
        System.out.println("2. Extreme Edge");
        System.out.println("3. Gift Wrapping");
        System.out.println("4. Graham Scan");
        int choice = scan.nextInt();

        System.out.println("Input Points: "+points);
        System.out.println();
        switch (choice) {
            case 1:
                System.out.println("Using Extreme point");
                ExtremePoint extremePoint = new ExtremePoint(points);
                extremePoint.getConvexHull();
                break;
            case 2:
                System.out.println("Using Extreme Edge");
                ExtremeEdge extremeEdge = new ExtremeEdge(points);
                extremeEdge.getConvexHull();
                break;
            case 3:
                System.out.println("Using Gift Warp");
                GiftWrapping giftWrap = new GiftWrapping(points);
                giftWrap.getConvexHull();
                break;
            case 4:
                System.out.println("Using Graham Scan");
                GrahamScan grahamScan = new GrahamScan(points);
                grahamScan.getConvexHull();
                break;
        }
    }


    private static void readPoints() {
        try {
            File file = new File("src\\convexHull.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] data = st.split(",");
                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                points.add(new Point(x, y));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static double getAngle(Point p1, Point p2) {
        double delta_x = p1.x - p2.x;
        double delta_y = p1.y - p2.y;
        double angle = atan2(delta_y, delta_x) * 180 / Math.PI;
        if (angle < 0) {
            angle = angle + 180;
        }
        return angle;

    }
}
