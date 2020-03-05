import java.util.ArrayList;

public class ExtremePoint {

    ArrayList<Point> pointList;
    float centroidX, centroidY;

    public ExtremePoint(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    public void getConvexHull() {


        for (int i = 0; i < pointList.size(); i++) {
            for (int j = 0; j < pointList.size(); j++) {
                if (j != i) {
                    for (int k = 0; k < pointList.size(); k++) {
                        if (k != i && k != j) {
                            for (int l = 0; l < pointList.size(); l++) {
                                if (l != i && l != j && l != k) {
                                    if (i < pointList.size() && j < pointList.size() && k < pointList.size())
                                        checkInsideTriangle(pointList.get(i), pointList.get(j), pointList.get(k), pointList.get(l));
                                }
                            }
                        }
                    }
                }
            }
        }

        angularSort();
        System.out.println("Convex Hull: " + pointList);

    }

    private void angularSort() {

        getCentroid();

        CartesianToPolar cartesianToPolar = new CartesianToPolar(pointList, centroidX, centroidY);
        cartesianToPolar.convertToPolar();
        ArrayList<PolarCoordinates> polarCoordinatesList = cartesianToPolar.pCoordinates;

        PolarCoordinates tempPolarCoordinate;
        Point tempPoint;

        for (int i = 0; i < pointList.size(); i++) {
            for (int j = 1; j < (pointList.size() - i); j++) {
                if (polarCoordinatesList.get(j - 1).theta > polarCoordinatesList.get(j).theta) {
                    tempPolarCoordinate = polarCoordinatesList.get(j - 1);
                    polarCoordinatesList.set(j - 1, polarCoordinatesList.get(j));
                    polarCoordinatesList.set(j, tempPolarCoordinate);

                    tempPoint = pointList.get(j - 1);
                    pointList.set(j - 1, pointList.get(j));
                    pointList.set(j, tempPoint);
                }
            }
        }

    }

    private void getCentroid() {
        centroidX = (pointList.get(0).x + pointList.get(1).x + pointList.get(2).x) / 3;
        centroidY = (pointList.get(0).y + pointList.get(1).y + pointList.get(2).y) / 3;
    }

    private void checkInsideTriangle(Point p1, Point p2, Point p3, Point p4) {

        if (isLeft(p1, p2, p4) && isLeft(p2, p3, p4) && isLeft(p3, p1, p4)) {
            pointList.remove(p4);
        }
    }

    private boolean isLeft(Point p1, Point p2, Point p3) {
        if (getAreaofTriangle(p1, p3, p2) < 0) {
            return true;
        }
        return false;
    }

    private static double getAreaofTriangle(Point a, Point b, Point c) {
        return 0.5 * (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));
    }
}
