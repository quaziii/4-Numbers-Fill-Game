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

    public boolean isValidBoardCell(int rowIdx, int colIdx) {
        return rowIdx >= 0 && rowIdx < boardRowSize && colIdx >= 0 && colIdx < boardColumnSize;
    }

    public void pushAdjacentMoves(Move move) {
        int[] rowDirections = {0, 1, 0, -1};   //directions of row to move left, right, top, bottom
        int[] columnDirections = {-1, 0, 1, 0};   //directions of column to move left, right, top, bottom

        List<Move> adjacentMoves = new ArrayList<Move>();

        for (int i = 0; i < 4; i++) {
            int adj_x = move.row + rowDirections[i];
            int adj_y = move.column + columnDirections[i];
            int adj_cell_value = 0;

            if (isValidBoardCell(adj_x, adj_y)) {
                adj_cell_value = board[adj_x][adj_y];
            }

            adjacentMoves.add(new Move(adj_x, adj_y, adj_cell_value));
        }

        move.setAdjacentMoves(adjacentMoves);
    }

    public boolean violateBlockRule(Move move, int value, int limit, int[][] visited) {
        visited[move.row][move.column] = 1;
        int totalVisited = 0;

        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardColumnSize; j++) {
                totalVisited += visited[i][j];
            }
        }

        if (totalVisited - 1 == value) {   // 1 has been subtracted here because of considering the root node's value
            return true;
        }

        if (limit <= 0)
            return false;

        pushAdjacentMoves(move);

        for (Move m : move.adjacentMoves) {
            if (isValidBoardCell(m.row, m.column) && m.value == value && visited[m.row][m.column] == 0) {
                if (violateBlockRule(m, value, limit - 1, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isLegalMove(Move move) {
        int[][] visited = new int[boardRowSize][boardColumnSize];     // keeps track of the visited cells

        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardColumnSize; j++) {
                visited[i][j] = 0;
            }
        }

        return !violateBlockRule(move, move.value, move.value, visited);
    }

    public List<Move> allLegalMoves() {
        List<Move> allPossibleMoves = new ArrayList<Move>();
        List<Move> allLegalMoves = new ArrayList<Move>();

        int[] options = {1, 2, 3, 4};

        for (int option : options) {
            for (int i = 0; i < boardRowSize; i++) {
                for (int j = 0; j < boardColumnSize; j++) {
                    if (board[i][j] == 0) {
                        Move m = new Move(i, j, option);
                        allPossibleMoves.add(m);
                    }
                }
            }
        }

        for (Move m : allPossibleMoves) {
            if (isLegalMove(m)) {
                allLegalMoves.add(m);
            }
        }

        return allLegalMoves;
    }
}
