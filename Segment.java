public class Segment {

    Point p;
    Point q;

    public Segment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }

    @Override
    public String toString() {
        return "\nSegment{" +
                "p=" + p +
                ", q=" + q +
                '}';
    }
}
