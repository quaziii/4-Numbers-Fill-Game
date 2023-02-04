import java.util.ArrayList;
import java.util.List;

public class Move {
    int row;
    int column;
    int value;
    List<Move> adjacentMoves = new ArrayList<Move>();
    List<Move> diagonalMoves = new ArrayList<Move>();

    public Move(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public void setAdjacentMoves(List<Move> adjacentMoves) {
        this.adjacentMoves = adjacentMoves;
    }

    public void setDiagonalMoves(List<Move> diagonalMoves) {
        this.diagonalMoves = diagonalMoves;
    }
}
