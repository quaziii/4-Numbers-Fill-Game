import java.util.List;
import java.util.ArrayList;

public class PNS {
    public static Move nextMove = null;
    public static int nodeCount = 0;
    public static void evaluate(Node node) {
        if (node.gameState.allLegalMoves().isEmpty()) {
            if (node.toPlay == GameBasics.WHITE) {
                node.value = GameBasics.PROVEN;
            } else if (node.toPlay == GameBasics.BLACK) {
                node.value = GameBasics.DISPROVEN;
            }
        }
    }

    public static void setProofAndDisproofNumbers(Node node) {
        if (!node.children.isEmpty()) {     // internal node. has child(ren)
            if (node.toPlay == GameBasics.WHITE) {      // AND node
                node.proof = 0;
                node.disproof = GameBasics.INFINITY;

                for (Node child : node.children) {
                    node.proof = node.proof + child.proof;

                    if (node.proof > GameBasics.INFINITY) {
                        node.proof = GameBasics.INFINITY;
                    }

                    if (child.disproof < node.disproof) {
                        node.disproof = child.disproof;
                    }
                }
            } else {        // OR node
                node.proof = GameBasics.INFINITY;
                node.disproof = 0;

                for (Node child : node.children) {
                    node.disproof = node.disproof + child.disproof;

                    if (node.disproof > GameBasics.INFINITY) {
                        node.disproof = GameBasics.INFINITY;
                    }

                    if (child.proof < node.proof) {
                        node.proof = child.proof;
                    }
                }
            }
        } else {    // terminal or non-terminal leaf
            if (node.value == GameBasics.DISPROVEN) {
                node.proof = GameBasics.INFINITY;
                node.disproof = 0;
            } else if (node.value == GameBasics.PROVEN) {
                node.proof = 0;
                node.disproof = GameBasics.INFINITY;
            } else if (node.value == GameBasics.UNKNOWN) {
                node.proof = 1;
                node.disproof = 1;
            }
        }
    }

    public static Node selectMostProvingNode(Node node) {
        Node best = null;

        while (node.expanded && !node.gameState.allLegalMoves().isEmpty()) {    // expanded node and not the end of the game
            int value = GameBasics.INFINITY;

            if (node.toPlay == GameBasics.BLACK) {      // OR node
                for (Node child : node.children) {
                    if (value > child.proof) {
                        best = child;
                        value = child.proof;
                    }
                }
            } else {    // AND node
                for (Node child : node.children) {
                    if (value > child.disproof) {
                        best = child;
                        value = child.disproof;
                    }
                }
            }

            node = best;    //need to be checked later
        }

        return node;
    }

    public static void generateChildren(Node node) {
        node.generateChildren();
    }

    public static void expandNode(Node node) {
        generateChildren(node);

        for (Node child : node.children) {
            nodeCount++;
            evaluate(child);
            setProofAndDisproofNumbers(child);

            if ((node.toPlay == GameBasics.BLACK && child.proof == 0) || (node.toPlay == GameBasics.WHITE && child.disproof == 0))
                break;
        }

        node.expanded = true;
    }

    public static Node updateAncestors(Node node, Node root) {
        while (true) {
            int oldProof = node.proof;
            int oldDisproof = node.disproof;

            setProofAndDisproofNumbers(node);

            if (node.proof == oldProof && node.disproof == oldDisproof) {
                return node;
            }

            if (node == root) {  // recheck this
                return node;
            }

            node = node.parent;
        }
    }

    public static void runPNS(Node root) {
        evaluate(root);
        setProofAndDisproofNumbers(root);
        Node current = root;
        Node mostProving;

        while (root.proof != 0 && root.disproof != 0) {
            if ((System.currentTimeMillis() - Main.startTime) > Main.timeLimit)
                return;

            mostProving = selectMostProvingNode(current);
            expandNode(mostProving);
            current = updateAncestors(mostProving, root);
        }
    }

    public static void setNextMove(Node root) {
        List<Move> allLegalMoves = root.gameState.allLegalMoves();
        int minProof = GameBasics.INFINITY;

        for (Node child : root.children) {
            if (child.proof < minProof) {
                minProof = child.proof;
                int[][] board = child.gameState.board;

                for (int i = 0; i < child.gameState.boardRowSize; i++) {
                    for (int j = 0; j < child.gameState.boardColumnSize; j++) {
                        if (child.gameState.board[i][j] != root.gameState.board[i][j]) {
                            nextMove = new Move(i, j, child.gameState.board[i][j]);
                        }
                    }
                }
            }
        }
    }
}
