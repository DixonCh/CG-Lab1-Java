import java.util.ArrayList;

import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;

public class CartesianToPolar {

    ArrayList<Point> points;
    ArrayList<PolarCoordinates> pCoordinates = new ArrayList<>();
    float centerX, centerY;

    public CartesianToPolar(ArrayList<Point> points, float centerX, float centerY) {
        this.points = points;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void convertToPolar() {

        //getCentroid();
        for (int i = 0; i < points.size(); i++) {
            pCoordinates.add(new PolarCoordinates(getDistance(points.get(i)), getTheta(points.get(i))));
        }
    }

    public double getDistance(Point p) {
        return sqrt((p.y - centerY) * (p.y - centerY) + (p.x - centerX) * (p.x - centerX));
    }

    public double getTheta(Point p) {
        double delta_x = p.x - centerX;
        double delta_y = p.y - centerY;
        return getPositiveTheta((atan2(delta_y, delta_x) * 180) / Math.PI);
    }

    public double getPositiveTheta(double theta) {
        if (theta >= 0) {
            return theta;
        } else {
            return 360 + theta;
        }
    }

    private void getCentroid() {
        float sumX = 0, sumY = 0;
        for (Point p : points) {
            sumX = sumX + p.x;
            sumY = sumY + p.y;
        }
        this.centerX = sumX / points.size();
        this.centerY = sumY / points.size();

        // System.out.println("Centroid: ("+centerX+","+centerY+")");
    }

    @Override
    public String toString() {
        return "CartesianToPolar{" +
                "\npoints=" + points +
                "\npCoordinates=" + pCoordinates +
                "\ncenterX=" + centerX +
                "\ncenterY=" + centerY +
                '}';
    }
}
