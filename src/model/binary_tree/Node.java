package model.binary_tree;

public class Node {
    private int value;
    private Node left;
    private Node right;

    public Node(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public int getValue() {
        return value;
    }

    public void addRecursive(int value) {
        if (value < this.value) {
            if (left == null) {
                left = new Node(value);
            } else {
                left.addRecursive(value);
            }
        } else {
            if (right == null) {
                right = new Node(value);
            } else {
                right.addRecursive(value);
            }
        }
    }

    public int getMax() {
        if (right == null) {
            return value;
        } else {
            return right.getMax();
        }
    }
}
