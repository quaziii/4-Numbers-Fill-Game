import java.util.ArrayList;
import java.util.List;

public class Move {
    int row;
    int column;
    int value;
    List<Move> adjacentMoves = new ArrayList<Move>();

    public Move(int row, int column, int value, List<Move> adjacentMoves) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.adjacentMoves = adjacentMoves;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Move> getAdjacentMoves() {
        return adjacentMoves;
    }

    public void setAdjacentMoves(List<Move> adjacentMoves) {
        this.adjacentMoves = adjacentMoves;
    }
}
