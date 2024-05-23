package model.binary_tree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTestNode {
    @Test
    void testGetPoint() {
        Node node = new Node(5, "coup1");
        assertEquals(5, node.getPoint());
    }

    @Test
    void testGetCoup() {
        Node node = new Node(5, "coup1");
        assertEquals("coup1", node.getCoup());
    }

    @Test
    void testGetLeft() {
        Node node = new Node(5, "coup1");
        assertNull(node.getLeft());
    }

    @Test
    void testGetRight() {
        Node node = new Node(5, "coup1");
        assertNull(node.getRight());
    }

    @Test
    void testAddRecursive() {
        Node node = new Node(5, "coup1");
        node.addRecursive(3, "coup2");
        node.addRecursive(7, "coup3");

        assertEquals(3, node.getLeft().getPoint());
        assertEquals("coup2", node.getLeft().getCoup());

        assertEquals(7, node.getRight().getPoint());
        assertEquals("coup3", node.getRight().getCoup());

        node.addRecursive(6, "coup3");
        assertEquals(6, node.getRight().getPoint());
    }
}
