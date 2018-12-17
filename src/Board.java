import java.util.*;

public class Board {

    String[][] tiles;
    int[][] values;
    private boolean[][] mines;

    private static final boolean EXTRA_LOG_INFO = false;

    Board(int size, int numberOfMines) {
        if (size < 1)
            size = 8;
        if (numberOfMines <= 0)
            numberOfMines = 8;

        tiles = new String[size][size];
        mines = new boolean[size][size];
        values = new int[size][size];

        while (numberOfMines != 0) {
            int x = range(0, size - 1);
            int y = range(0, size - 1);

            // Don't repeat mine locations
            if (mines[x][y])
                continue;

            mines[x][y] = true;

            numberOfMines--;
        }

        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = " ";
                values[i][j] = -1;
            }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                sb.append("|");
                sb.append(tiles[i][j]);
            }
            if (EXTRA_LOG_INFO) {
                sb.append("\t\t");


                for (int j = 0; j < tiles[i].length; j++) {
                    sb.append("|");
                    sb.append(mines[i][j] ? "M" : " ");
                }

                sb.append("\t\t");

                for (int j = 0; j < tiles[i].length; j++) {
                    sb.append("|");
                    sb.append(values[i][j] == -1 ? " " : values[i][j]);
                }
            }
            sb.append("|\n");
        }

        return sb.toString();
    }

    // RANDOM

    private static Random r = new Random();

    static int range(int minValue, int maxValue) {
        return minValue + r.nextInt(maxValue - minValue - 1);
    }

    boolean click(int x, int y) {
        //if(initFlag == false);
        //init(x,y)


        if (mines[x][y]) {
            tiles[x][y] = "M";
            return false;
        }

        int ma = countMinesAround(x, y);
        values[x][y] = ma;
        tiles[x][y] = String.valueOf(ma);

        if (tiles[x][y].equals("0")) {
            for (int i = x - 1; i <= x + 1; i++) {
                if (i < 0 || i >= tiles.length)
                    continue;
                for (int j = y - 1; j <= y + 1; j++) {
                    if (j < 0 || j >= tiles[i].length)
                        continue;

                    if (tiles[i][j].equals(" "))
                        click(i, j);
                }
            }
        }


        return true;

    }

    private int countMinesAround(int x, int y) {
        int count = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            if (i < 0 || i >= tiles.length)
                continue;

            for (int j = y - 1; j <= y + 1; j++) {

                if (j < 0 || j >= tiles[i].length)
                    continue;

                if (mines[i][j])
                    count++;

            }
        }
        return count;
    }

    /*
    public List<Position> getOpenEdges() {
        Set<Position> positions = new HashSet<>();

        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles[i].length; j++)
                if (tiles[i][j].matches("[1-9]"))
                    positions.addAll(adjacentFreeTiles(i, j));

        return new ArrayList<>(positions);

    }
*/

    List<Position> getExposedNumbers() {
        Set<Position> positions = new HashSet<>();

        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles[i].length; j++)
                if (tiles[i][j].matches("[1-9]") && adjacentFreeTiles(i, j).size() > 0)
                    positions.add(new Position(i, j));

        return new ArrayList<>(positions);

    }

    private Collection<? extends Position> adjacentFreeTiles(int x, int y) {
        List<Position> pos = new ArrayList<>();
        for (int i = x - 1; i <= x + 1; i++) {
            if (i < 0 || i >= tiles.length)
                continue;
            for (int j = y - 1; j <= y + 1; j++) {
                if (j < 0 || j >= tiles[i].length)
                    continue;
                if (tiles[i][j].equals(" "))
                    pos.add(new Position(i, j));
            }
        }
        return pos;
    }

    Collection<? extends Position> adjacentFreeTilesAndFlags(int x, int y) {
        List<Position> pos = new ArrayList<>();
        for (int i = x - 1; i <= x + 1; i++) {
            if (i < 0 || i >= tiles.length)
                continue;
            for (int j = y - 1; j <= y + 1; j++) {
                if (j < 0 || j >= tiles[i].length)
                    continue;

                if (tiles[i][j].equals(" ") || tiles[i][j].equals("F"))
                    pos.add(new Position(i, j));
            }
        }
        return pos;
    }

    void flag(int x, int y) {
        tiles[x][y] = "F";
    }
}
