package model.binary_tree;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getAll0Point() {
        List<String> zeroPointMoves = new ArrayList<>();
        getAll0PointRecursive(root, zeroPointMoves);
        return zeroPointMoves;
    }

    private void getAll0PointRecursive(Node node, List<String> zeroPointMoves) {
        if (node == null) {
            return;
        }
        if (node.getPoint() == 0) {
            zeroPointMoves.add(node.getCoup());
        }
        getAll0PointRecursive(node.getLeft(), zeroPointMoves);
        getAll0PointRecursive(node.getRight(), zeroPointMoves);
    }

    public Node getBestCoup() {
        return getBestCoupRecursive(root);
    }

    private Node getBestCoupRecursive(Node node) {
        if (node == null) {
            return null;
        }

        Node leftBest = getBestCoupRecursive(node.getLeft());
        Node rightBest = getBestCoupRecursive(node.getRight());

        Node best = node;

        if (leftBest != null && leftBest.getPoint() > best.getPoint()) {
            best = leftBest;
        }

        if (rightBest != null && rightBest.getPoint() > best.getPoint()) {
            best = rightBest;
        }

        return best;
    }

    public void display() {
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

    public List<Node> getAll10Point() {
        return getAll10PointRecursive(root);
    }

    private List<Node> getAll10PointRecursive(Node node) {
        List<Node> tenPointMoves = new ArrayList<>();

        if (node == null) {
            return tenPointMoves;
        }
        if (node.getPoint() == 10) {
            tenPointMoves.add(node);
        }

        tenPointMoves.addAll(getAll10PointRecursive(node.getLeft()));
        tenPointMoves.addAll(getAll10PointRecursive(node.getRight()));
        return tenPointMoves;
    }

}
