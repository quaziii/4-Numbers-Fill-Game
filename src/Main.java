import java.util.Arrays;

public class Main {
    public static void initializeBoardFromInput(Fillgame fillgame, String[] position) {
        for (int i = 0; i < fillgame.boardRowSize; i++) {
            for (int j = 0; j < fillgame.boardColumnSize; j++) {
                if (position[i].charAt(j) == '.') {
                    fillgame.board[i][j] = 0;
                } else {
                    fillgame.board[i][j] = Character.getNumericValue(position[i].charAt(j));
                }
            }
        }
    }
    public static void main(String[] args) {
        String fillGamePosition = args[0];
        String timeLimit = args[1];

        // Remove this block later
        String testRowIdx = args[2];
        String testColIdx = args[3];
        String testMoveVal = args[4];

        String[] position = fillGamePosition.split("\\*");
        int boardRowSize = position.length;
        int boardColumnSize = position[0].length();

        Fillgame fillgame = new Fillgame(boardRowSize, boardColumnSize);    //  create an empty board
        initializeBoardFromInput(fillgame, position);

        fillgame.printBoard();
    }
}
