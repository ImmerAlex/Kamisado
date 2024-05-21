package model.binary_tree;

public class Tree {
    private Node root;

    public Tree() {
        root = null;
    }

    public void add(int value) {
        if (root == null) {
            root = new Node(value);
        } else {
            root.addRecursive(value);
        }
    }
}
