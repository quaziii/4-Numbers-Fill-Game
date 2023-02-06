import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static long startTime;
    public static long stopTime;
    public static long timeLimit;

    public static Move nextMove = null;
    public static int nodeCount = 0;
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

    public static boolean storeInTT(HashMap<Fillgame, Boolean> tt, Fillgame fillgame, boolean result) {
        tt.put(fillgame, result);
        return result;
    }

    public static boolean negamaxTT (Fillgame fillgame, HashMap<Fillgame, Boolean> tt) {
        boolean result = tt.containsKey(fillgame);

        if (result) {
            return tt.get(fillgame);
        }

        List<Move> allLegalMoves = fillgame.allLegalMoves();

        if (allLegalMoves.isEmpty()) {
            result = fillgame.toPlay == GameBasics.BLACK;
            return storeInTT(tt, fillgame, result);
        }

        for (Move m : allLegalMoves) {
            nodeCount++;
            fillgame.board[m.row][m.column] = m.value;

            if (fillgame.toPlay == GameBasics.WHITE) {
                fillgame.toPlay = GameBasics.BLACK;
            } else {
                fillgame.toPlay = GameBasics.WHITE;
            }

            boolean success = !negamaxTT(fillgame, tt);

            fillgame.board[m.row][m.column] = 0;
            if (fillgame.toPlay == GameBasics.WHITE) {
                fillgame.toPlay = GameBasics.BLACK;
            } else {
                fillgame.toPlay = GameBasics.WHITE;
            }

            if (success) {
                nextMove = m;
                return storeInTT(tt, fillgame, true);
            }
        }

        return storeInTT(tt, fillgame, false);
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
        fillgame.toPlay = GameBasics.BLACK;

        // negaMAX
        if (fillgame.allLegalMoves().isEmpty()) {
            System.out.println("L None " + ((stopTime-startTime) / 1000F) + " " + nodeCount);
        } else {
            HashMap<Fillgame, Boolean> tt = new HashMap<>();
            boolean negamaxResult;
            startTime = System.currentTimeMillis();
            nodeCount++;
            negamaxResult = negamaxTT(fillgame, tt);
            stopTime = System.currentTimeMillis();

//            System.out.println("Negamax with TT");
//            System.out.println("-----------------------");
            System.out.println();
            if (negamaxResult) {
                System.out.println("W " + nextMove.column + " " + nextMove.row + " " + nextMove.value + " " + ((stopTime-startTime) / 1000F) + " " + nodeCount);
            } else {
                System.out.println("L None " + ((stopTime-startTime) / 1000F) + " " + nodeCount);
            }
        }
        System.out.println();

        // PNS
//        Node root = new Node(null, fillgame);
//
//        startTime = System.currentTimeMillis();
//        PNS.nodeCount++;
//        PNS.runPNS(root);
//        PNS.setNextMove(root);
//        stopTime = System.currentTimeMillis();
//
//        System.out.println("PNS");
//        System.out.println("-----------------------");
//        System.out.println();
//        if (root.proof == 0) {
//            System.out.println("W " + PNS.nextMove.column + " " + PNS.nextMove.row + " " + PNS.nextMove.value + " " + ((stopTime - startTime) / 1000F) + " " + PNS.nodeCount);
//        }  else if (root.disproof == 0) {
//            System.out.println("L None " + ((stopTime - startTime) / 1000F) + " " + PNS.nodeCount);
//        } else {
//            System.out.println("? None " + ((stopTime - startTime) / 1000F) + " " + PNS.nodeCount);
//        }
//        System.out.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(10241024.0));
    }
}
