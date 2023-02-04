public class PNS {
    public void evaluate(Node node) {

    }

    public void setProofAndDisproofNumbers(Node node) {
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

    public Node selectMostProvingNode(Node node) {
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

    public void expandNode(Node node) {

    }

    public Node updateAncestors(Node node, Node root) {


        return node;
    }

    public void runPNS(Node root) {
        evaluate(root);
        setProofAndDisproofNumbers(root);
        Node current = root;
        Node mostProving = null;

        while (root.proof != 0 && root.disproof != 0) {
            mostProving = selectMostProvingNode(current);
            expandNode(mostProving);
            current = updateAncestors(mostProving, root);
        }
    }
}
