package Snake.Model;

public class Coordinate implements CoordinateInterface {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
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

}
