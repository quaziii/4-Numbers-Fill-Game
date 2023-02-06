import java.util.ArrayList;
import java.util.Arrays;
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

        if (fillgame.toPlay == GameBasics.WHITE) {
            this.toPlay = GameBasics.BLACK;
        } else {
            this.toPlay = GameBasics.WHITE;
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

    public int[][] createEmptyBoard() {
        int[][] emptyBoard = new int[boardRowSize][boardColumnSize];

        for (int i = 0; i < boardRowSize; i++) {
            for (int j = 0; j < boardColumnSize; j++) {
                emptyBoard[i][j] = 0;
            }
        }

        return emptyBoard;
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
                adjacentMoves.add(new Move(adj_x, adj_y, adj_cell_value));
            }
        }

        move.setAdjacentMoves(adjacentMoves);
    }

    public void pushDiagonalMoves(Move move) {
        List<Move> diagonalMoves = new ArrayList<Move>();

        for (int i = 0; i < 4; i++) {
            int diag_x = move.row + diagRowDirections[i];
            int diag_y = move.column + diagColumnDirections[i];
            int diag_cell_value = 0;

            if (isValidBoardCell(diag_x, diag_y)) {
                diag_cell_value = board[diag_x][diag_y];
                diagonalMoves.add(new Move(diag_x, diag_y, diag_cell_value));
            }
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

        if (move.adjacentMoves.isEmpty())
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

    public boolean preserveRegionRule(Move move, int value, int limit, int[][] visited) {   // checks if the move kills other moves opportunity
        visited[move.row][move.column] = 1;

        if (move.value == 0) {
            return true;
        }
        
        if (limit < 0) {
            return false;
        }

        if (move.adjacentMoves.isEmpty())
            pushAdjacentMoves(move);

        for (Move m : move.adjacentMoves) {
            if (isValidBoardCell(m.row, m.column) && (m.value == value || m.value == 0) && visited[m.row][m.column] == 0) {
                if (preserveRegionRule(m, value, limit - 1, visited)) {
                    return true;
                }
            }
        }
        
        return false;
    }

    public boolean isLegalMove(Move move) {
        int[][] visitedForBlockRule = createEmptyBoard();

        boolean violateBlockRule = violateBlockRule(move, move.value, move.value, visitedForBlockRule);

        if (violateBlockRule)
            return false;

        boolean areNeighboursCompletedBlock = false;
        boolean preserveRegionRule = true;  // looks for empty cell in neighbours block
        boolean surroundedByOthers = true;

        for (Move neighbour : move.adjacentMoves) {
            if (neighbour.value == 0 || neighbour.value == move.value || move.value == 1) {
                surroundedByOthers = false;
                break;
            }
        }

        if (surroundedByOthers)
            return false;

        for (Move neighbour : move.adjacentMoves) {
            if (neighbour.value != 0 && neighbour.value != move.value) {
                int[][] visited = createEmptyBoard();
                visited [move.row][move.column] = 1;

                areNeighboursCompletedBlock = violateBlockRule(neighbour, neighbour.value, neighbour.value, visited);    // check if neighbour's block is completed

                if (!areNeighboursCompletedBlock) {  // if any one of them is false
                    break;
                }
            }
        }

//        System.out.println(areNeighboursCompletedBlock + " for " + move.value);

        for (Move neighbour : move.adjacentMoves) {
            if (neighbour.value != 0 && neighbour.value != move.value) {
                int[][] visitedForRegionRule = createEmptyBoard();
                visitedForRegionRule[move.row][move.column] = 1;
                preserveRegionRule = preserveRegionRule(neighbour, neighbour.value, neighbour.value - 1, visitedForRegionRule);

                if (!preserveRegionRule) {  // if any one of them is false
                    break;
                }
            }
        }

        return areNeighboursCompletedBlock || preserveRegionRule;
    }

    public List<Move> allLegalMoves() {
        List<Move> allPossibleMoves = new ArrayList<Move>();
        List<Move> allLegalMoves = new ArrayList<Move>();

        if (boardRowSize == 1 && boardColumnSize == 1 && board[0][0] == 0) {
            allLegalMoves.add(new Move(0, 0, 1));
            return allLegalMoves;
        }

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
