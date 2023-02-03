import java.util.ArrayList;
import java.util.List;

public class Node {
    Node parent;
    List<Node> children = new ArrayList<>();
    Fillgame gameState;
    int phi;
    int delta;

    public Node(Node parent, Fillgame gameState) {
        this.parent = parent;
        this.gameState = gameState;
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
