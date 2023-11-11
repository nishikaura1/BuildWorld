package byow.Core;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position add(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    @Override
    public boolean equals(Object o) {
        return ((Position) o).x == x && ((Position) o).y == y;
    }
}
