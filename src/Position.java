public class Position {

    public int x, y;

    public Position(int i, int j) {
        x = i;
        y = j;
    }

    @Override
    public String toString() {
        return "("
                + x
                + ","
                + y
                + ')';
    }
}
