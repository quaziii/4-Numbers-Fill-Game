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

        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardColumnSize; j++) {
                board[i][j] = 0;
            }
        }
    }

    public int getBoardRowSize() {
        return boardRowSize;
    }

    public void setBoardRowSize(int boardRowSize) {
        this.boardRowSize = boardRowSize;
    }

    public int getBoardColumnSize() {
        return boardColumnSize;
    }

    public void setBoardColumnSize(int boardColumnSize) {
        this.boardColumnSize = boardColumnSize;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getToPlay() {
        return toPlay;
    }

    public void setToPlay(int toPlay) {
        this.toPlay = toPlay;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public void printBoard() {
        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardColumnSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void addMoveToBoard(Move move) {
        moves.add(move);
    }
}
