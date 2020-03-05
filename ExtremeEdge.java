import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExtremeEdge {

    ArrayList<Point> pointList;
    Boolean isExtreme = true;
    List<Point> convexHull = new ArrayList<>();
    ArrayList<Segment> segmentList = new ArrayList<>();
    float centroidX, centroidY;

    public ExtremeEdge(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    public void getConvexHull() {

        for (int i = 0; i < pointList.size(); i++) {
            for (int j = 0; j < pointList.size(); j++) {
                if (j != i) {
                    isExtreme = true;
                    for (int k = 0; k < pointList.size(); k++) {
                        if (k != i && k != j) {
                            if (!isLeft(pointList.get(i), pointList.get(j), pointList.get(k))) {
                                isExtreme = false;
                                break;
                            }
                        }
                    }

                    if (isExtreme) {
                        segmentList.add(new Segment(pointList.get(i), pointList.get(j)));
                    }
                }
            }
        }

        System.out.println(segmentList);
        rearrangeEdges();
        //convexHull = convexHull.stream().distinct().collect(Collectors.toList());
        System.out.println();
        System.out.println("Convex Hull" + convexHull);
    }

   /* private void angularSort() {

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

    }*/

    private void getCentroid() {
        centroidX = (pointList.get(0).x + pointList.get(1).x + pointList.get(2).x) / 3;
        centroidY = (pointList.get(0).y + pointList.get(1).y + pointList.get(2).y) / 3;
    }


    private boolean isLeft(Point p1, Point p2, Point p3) {
        if (getAreaofTriangle(p1, p3, p2) <= 0) {
            return true;
        }
        return false;
    }

    private void rearrangeEdges() {

        for(Segment s : segmentList){
            convexHull.add(s.p);
            convexHull.add(s.q);
        }

        convexHull = convexHull.stream().distinct().collect(Collectors.toList());


        /*System.out.println(segmentList);
        convexHull.add(segmentList.get(0).p);
        convexHull.add(segmentList.get(0).q);

        for (int i = 0; i < convexHull.size(); i++) {
            for (int j = 0; j < segmentList.size(); j++) {
                if (convexHull.get(convexHull.size() - 1) == segmentList.get(j).p) {
                    if (convexHull.get(0) == segmentList.get(j).q) {
                        break;
                    }
                    convexHull.add(segmentList.get(j).q);
                }
            }
        }*/
    }

    private static double getAreaofTriangle(Point a, Point b, Point c) {
        return 0.5 * (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));
    }
}
