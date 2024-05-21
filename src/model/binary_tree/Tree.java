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
        return getBestCoupRecursive(root);
    }

    private String getBestCoupRecursive(Node node) {
        if (node.getRight() == null) {
            return node.getCoup();
        }
        return getBestCoupRecursive(node.getRight());
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
