import java.util.ArrayList;

public class GiftWrapping {
    ArrayList<Point> pointList;
    ArrayList<Point> convexHull = new ArrayList<>();
    Point pLowest, currentPoint, pHeighest;
    boolean topFlag = false;


    public GiftWrapping(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    public void getConvexHull() {

        getLowestPoint();
        getHeightestPoint();

        convexHull.add(pLowest);
        currentPoint = pLowest;
        do {


            if (currentPoint == pHeighest) {
                getNextPoint();
                topFlag = true;
            } else {
                getNextPoint();
            }
        } while (currentPoint != pLowest);

        System.out.println("Convex Hull; "+convexHull);
    }

    private void getNextPoint() {

        double minAngle = 400;
        int index = 0;

        CartesianToPolar cartesianToPolar = new CartesianToPolar(pointList, currentPoint.x, currentPoint.y);
        cartesianToPolar.convertToPolar();
        ArrayList<PolarCoordinates> polarList = cartesianToPolar.pCoordinates;
        for (int i = 0; i < polarList.size(); i++) {

            if (!topFlag) {
                if (polarList.get(i).theta < minAngle && polarList.get(i).r > 0) {
                    minAngle = polarList.get(i).theta;
                    index = i;
                }
            } else {
                if (polarList.get(i).theta > 180 && polarList.get(i).theta < minAngle && polarList.get(i).r > 0) {
                    minAngle = polarList.get(i).theta;
                    index = i;
                }
            }
        }

       // System.out.println("min Angle: " + minAngle + "\n" + pointList.get(index));
        currentPoint = pointList.get(index);
        if(currentPoint != pLowest) {
            convexHull.add(currentPoint);
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

    private void getHeightestPoint() {
        //find lowest y
        pHeighest = pointList.get(0);
        for (int i = 1; i < pointList.size(); i++) {
            if (pHeighest.y < pointList.get(i).y) {
                pHeighest = pointList.get(i);
            }
        }
    }
}
