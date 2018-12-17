import java.util.*;

public class Game {

    public static void main(String... args) {

        int boardSize = Board.range(8, 16);
        new Game(boardSize, boardSize + 3).play();

    }


    private final int mines, size;
    private boolean inGame;
    private int turn;
    private Board board;

    private Game(int size, int mines) {
        this.size = size;
        this.mines = mines;
    }


    private void play() {
        inGame = true;
        turn = 0;
        board = new Board(size, mines);
        while (inGame) {
            System.out.println("Playing in turn " + turn);
            playTurn();
            drawBoard();
            turn++;
        }
    }

    private void playTurn() {
        if (turn == 0)
            playTurnOne();
        else
            playTurnN();
    }

    private void playTurnOne() {
        int x = Board.range(0, size - 1);
        int y = Board.range(0, size - 1);

        System.out.println("Click on " + x + ", " + y);
        if (!board.click(x, y))
            inGame = false;
    }

    private void playTurnN() {
        //List<Position> edges = board.getOpenEdges();

        List<Position> detectedMines = searchSureToBeMine();

        if (detectedMines.size() != 0) {
            // Know there are mines
            System.out.println("Detected a mine on " + detectedMines.get(0).x + ", " + detectedMines.get(0).y);
            board.flag(detectedMines.get(0).x, detectedMines.get(0).y);
        }
        /*else if(!edges.isEmpty()){
            System.out.println("Click on " + edges.get(0).x + ", " + edges.get(0).y);
            if (!board.click(edges.get(0).x, edges.get(0).y))
                win = inGame = false;
        }*/
        else {
            int x = Board.range(0, size - 1);
            int y = Board.range(0, size - 1);

            while (!board.tiles[x][y].equals(" ")) {
                x = Board.range(0, size - 1);
                y = Board.range(0, size - 1);
            }

            System.out.println("Click on " + x + ", " + y);
            if (!board.click(x, y))
                inGame = false;
        }
    }

    private List<Position> searchSureToBeMine() {
        List<Position> pos = new ArrayList<>();

        List<Position> numbers = board.getExposedNumbers();

        for (Position number : numbers) {
            int i = number.x,
                    j = number.y;

            Collection<? extends Position> aux = board.adjacentFreeTilesAndFlags(i, j);
            if (aux.size() == board.values[i][j])

                for (Position position : aux) {
                    if (board.tiles[position.x][position.y].equals(" "))
                        pos.add(position);
                }
        }
        return pos;

    }


    private void drawBoard() {
        System.out.println(board);
    }
}
