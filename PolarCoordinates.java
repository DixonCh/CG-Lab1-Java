public class PolarCoordinates {

    double r;
    double theta;

    public PolarCoordinates(){
    }

    public PolarCoordinates(double r, double theta) {
        this.r = r;
        this.theta = theta;
    }

    @Override
    public String toString() {
        return "\nPolarCoordinates{" +
                "r=" + r +
                ", theta=" + theta +
                '}';
    }
}
