import java.util.ArrayList;
import java.util.List;

public class Node {
    Node parent;
    List<Node> children = new ArrayList<>();
    Fillgame gameState;
    int phi;
    int delta;
    int toPlay;

    public Node(Node parent, Fillgame gameState) {
        this.parent = parent;
        this.gameState = gameState;

        if (parent == null) {
            this.toPlay = GameBasics.BLACK;
        } else {
            this.toPlay = GameBasics.BLACK + GameBasics.WHITE - parent.toPlay;
        }
    }

    public void printBoardStatus() {
        for (int i = 0; i < gameState.boardRowSize; i++) {
            for (int j = 0; j < gameState.boardColumnSize; j++) {
                System.out.print(gameState.board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
