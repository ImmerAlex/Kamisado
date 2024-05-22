package model.binary_tree;

public class Tree {
    private Node root;

    public Tree() {
        root = null;
    }

    public void add(int value, String coup) {
        if (root == null) {
            root = new Node(value, coup);
        } else {
            root.addRecursive(value, coup);
        }
    }

    public String getBestCoup() {
        return getBestCoupRecursive(root, root.getCoup(), root.getPoint());
    }

    private String getBestCoupRecursive(Node node, String bestCoup, int bestPoint) {
        if (node == null) {
            return bestCoup;
        }
        if (node.getPoint() > bestPoint) {
            bestCoup = node.getCoup();
            bestPoint = node.getPoint();
        }
        bestCoup = getBestCoupRecursive(node.getLeft(), bestCoup, bestPoint);
        return getBestCoupRecursive(node.getRight(), bestCoup, bestPoint);
    }

    public void display () {
        displayRecursive(root);
    }

    private void displayRecursive(Node node) {
        if (node == null) {
            return;
        }
        displayRecursive(node.getLeft());
        System.out.println(node.getCoup() + " : " + node.getPoint());
        displayRecursive(node.getRight());
    }
}
