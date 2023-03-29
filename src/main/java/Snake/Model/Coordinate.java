package Snake.Model;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Coordinate)) {
            return false;
        }
        return this.x == ((Coordinate) arg0).getX() && this.y == ((Coordinate) arg0).getY();
    }

    public static double distance(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    // Used when comparing distances which both uses this method, where the only thing that matters is if one is larger than the other. This does not give the actual distance, but gives a method for comparing distances.
    public static int distanceFast(Coordinate a, Coordinate b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

}
