package model.binary_tree;

public class Node {
    private int point;
    private String coup;
    private Node left;
    private Node right;

    public Node(int point, String coup) {
        this.point = point;
        this.coup = coup;
        this.left = null;
        this.right = null;
    }

    public int getPoint() {
        return point;
    }

    public String getCoup() {
        return coup;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void addRecursive(int value, String coup) {
        // If coup already exists, we don't add it we just update the point
        if (this.coup.equals(coup)) {
            this.point = value;
        } else if (value > this.point) {
            if (right == null) {
                right = new Node(value, coup);
            } else {
                right.addRecursive(value, coup);
            }
        } else {
            if (left == null) {
                left = new Node(value, coup);
            } else {
                left.addRecursive(value, coup);
            }
        }
    }
}
