import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static long startTime;
    public static long stopTime;
    public static long timeLimit;
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
        String limit = args[1];
        timeLimit = Integer.valueOf(limit);
        timeLimit *= 1000F;

        String[] position = fillGamePosition.split("\\*");
        int boardRowSize = position.length;
        int boardColumnSize = position[0].length();

        Fillgame fillgame = new Fillgame(boardRowSize, boardColumnSize);    // create an empty board
        initializeBoardFromInput(fillgame, position);

        Node root = new Node(null, fillgame);

        startTime = System.currentTimeMillis();
        PNS.runPNS(root);
        PNS.setNextMove(root);
        stopTime = System.currentTimeMillis();

        if (root.proof == 0) {
            System.out.println("W " + PNS.nextMove.row + " " + PNS.nextMove.column + " " + PNS.nextMove.value + " " + ((stopTime - startTime) / 1000F) + " " + PNS.nodesExpanded);
        }  else if (root.disproof == 0) {
            System.out.println("L None " + ((stopTime - startTime) / 1000F) + " " + PNS.nodesExpanded);
        } else {
            System.out.println("? None " + ((stopTime - startTime) / 1000F) + " " + PNS.nodesExpanded);
        }
        System.out.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(1024.0 * 1024.0));
    }
}
