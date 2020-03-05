import java.util.ArrayList;
import java.util.Stack;

public class GrahamScan {

    ArrayList<Point> pointList;
    ArrayList<Point> sortedPointList = new ArrayList<>();
    Stack<Point> pointStack = new Stack<>();
    Point pLowest;

    public GrahamScan(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    public void getConvexHull() {


        getLowestPoint(); //smallest y
        angularSort();

        pointStack.push(pointList.get(0));
        pointStack.push(pointList.get(1));

        for (int i = 2; i < pointList.size(); i++) {
           if(isLeft(pointStack.get(pointStack.size()-2), pointList.get(i),pointStack.lastElement())){
                pointStack.push(pointList.get(i));
            }else{
               pointStack.pop();
               pointStack.push(pointList.get(i));
           }
        }
        System.out.println("Convex Hull:" +pointStack);
    }

    private void angularSort() {

        CartesianToPolar cartesianToPolar = new CartesianToPolar(pointList, pLowest.x, pLowest.y);
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

    private void getLowestPoint() {
        //find lowest y
        pLowest = pointList.get(0);
        for (int i = 1; i < pointList.size(); i++) {
            if (pLowest.y > pointList.get(i).y) {
                pLowest = pointList.get(i);
            }
        }
        //System.out.println(pLowest);
    }

    private static boolean isLeft(Point a, Point c, Point b) {
        Double areaofTriangle = getAreaOfTriangle(a, c, b);
        if (areaofTriangle <= 0) {
            return true;
        }
        return false;
    }

    private static double getAreaOfTriangle(Point a, Point b, Point c) {

        return 0.5 * (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));
    }


}
