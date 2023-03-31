package Snake.Model;

/**
 * This class is used to store x and y coordinates. It implements the Comparable interface, so that we can sort the coordinates.
 * It also implements an equals method so that we can compare coordinates.
 */
public class Coordinate implements Comparable<Coordinate> {

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

    /**
     * @return The accurate distance between the two coordinates
     */
    public static double distance(Coordinate coord1, Coordinate coord2) {
        return Math.sqrt(Math.pow(coord1.getX() - coord2.getX(), 2) + Math.pow(coord1.getY() - coord2.getY(), 2));
    }

    /**
     * Used when comparing distances which both uses this method, where the only thing that matters is if one is larger than the other. This does not give the actual distance, but gives a method for comparing distances.
     * @param coord1 The first coordinate
     * @param coord2 The second coordinate
     * @return A number which indicates the distance between the two coordinates (not the actual distance)
     */
    public static int distanceFast(Coordinate coord1, Coordinate coord2) {
        return Math.abs(coord1.getX() - coord2.getX()) + Math.abs(coord1.getY() - coord2.getY());
    }

    @Override
    public int compareTo(Coordinate arg0) {
        if (this.x == arg0.getX()) {
            return this.y - arg0.getY();
        }
        return this.x - arg0.getX();
    }

}
