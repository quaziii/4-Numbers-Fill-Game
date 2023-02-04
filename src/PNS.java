public class PNS {
    public void evaluate(Node node) {

    }

    public void setProofAndDisproofNumbers(Node node) {

    }

    public Node selectMostProvingNode(Node node) {


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
