import java.util.ArrayList;
import java.util.List;

public class Fillgame {
    int boardRowSize;
    int boardColumnSize;
    int[][] board;
    int toPlay;
    List<Move> moves = new ArrayList<Move>();

    public static int[] adjRowDirections = {0, 1, 0, -1};   //directions of row to move left, right, top, bottom
    public static int[] adjColumnDirections = {-1, 0, 1, 0};   //directions of column to move left, right, top, bottom
    public  static int[] diagRowDirections = {-1, 1, -1, 1};
    public static int[] diagColumnDirections = {1, 1, -1, -1};

    public Fillgame(int boardRowSize, int boardColumnSize) {
        this.boardRowSize = boardRowSize;
        this.boardColumnSize = boardColumnSize;
        this.board = new int[boardRowSize][boardColumnSize];

        // toPlay might be needed

        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardColumnSize; j++) {
                board[i][j] = 0;
            }
        }
    }

    public Fillgame(Fillgame fillgame) {
        this.boardRowSize = fillgame.boardRowSize;
        this.boardColumnSize = fillgame.boardColumnSize;
        this.board = new int[boardRowSize][boardColumnSize];

        for (int i = 0; i < this.boardRowSize; i++) {
            System.arraycopy(fillgame.board[i], 0, this.board[i], 0, this.boardColumnSize);
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
        List<Move> adjacentMoves = new ArrayList<Move>();

        for (int i = 0; i < 4; i++) {
            int adj_x = move.row + adjRowDirections[i];
            int adj_y = move.column + adjColumnDirections[i];
            int adj_cell_value = 0;

            if (isValidBoardCell(adj_x, adj_y)) {
                adj_cell_value = board[adj_x][adj_y];
            }

            adjacentMoves.add(new Move(adj_x, adj_y, adj_cell_value));
        }

        move.setAdjacentMoves(adjacentMoves);
    }

    public void pushDiagonalMoves(Move move) {
        List<Move> diagonalMoves = new ArrayList<Move>();

        for (int i = 0; i < 4; i++) {
            int diag_x = move.row + diagRowDirections[i];
            int diag_y = move.column + adjColumnDirections[i];
            int diag_cell_value = 0;

            if (isValidBoardCell(diag_x, diag_y)) {
                diag_cell_value = board[diag_x][diag_y];
            }

            diagonalMoves.add(new Move(diag_x, diag_y, diag_cell_value));
        }

        move.setDiagonalMoves(diagonalMoves);
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

        boolean violateBlockRule = violateBlockRule(move, move.value, move.value, visited);

        return !violateBlockRule;
    }

    public List<Move> allLegalMoves() {
        List<Move> allPossibleMoves = new ArrayList<Move>();
        List<Move> allLegalMoves = new ArrayList<Move>();

        List<Integer> options = new ArrayList<Integer>();
        options.add(1);
        options.add(2);
        options.add(3);
        options.add(4);

        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardColumnSize; j++) {
                for (int option : options) {
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
