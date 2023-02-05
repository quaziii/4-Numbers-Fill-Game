import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        String[] position = fillGamePosition.split("\\*");
        int boardRowSize = position.length;
        int boardColumnSize = position[0].length();

        Fillgame fillgame = new Fillgame(boardRowSize, boardColumnSize);    // create an empty board
        initializeBoardFromInput(fillgame, position);

        System.out.println("Board: ");
        System.out.println("-------");
        fillgame.printBoard();
        System.out.println();

        Node root = new Node(null, fillgame);

        long startTime = System.currentTimeMillis();
        PNS.runPNS(root);
        System.out.println("PN = " + root.proof);
        System.out.println("DN = " + root.disproof);
        long stopTime = System.currentTimeMillis();
        System.out.println((stopTime - startTime) / 1000F);
    }
}
