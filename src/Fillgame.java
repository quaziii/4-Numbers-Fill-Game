import java.util.ArrayList;
import java.util.List;

public class Fillgame {
    int boardRowSize;
    int boardColumnSize;
    int[][] board;
    int toPlay;
    List<Move> moves = new ArrayList<Move>();

    public Fillgame(int boardRowSize, int boardColumnSize) {
        this.boardRowSize = boardRowSize;
        this.boardColumnSize = boardColumnSize;
        this.board = new int[boardRowSize][boardColumnSize];

        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardColumnSize; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardColumnSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
