package com.hessky.oosc.lab6;

public class Lab6 {
    public static void main(String[] args) {
        //leafs
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        //nodes
        Node node5 = new Node(5, node1, node2);
        Node node6 = new Node(6, node3, null);
        Node node7 = new Node(7, null, node4);
        Node node8 = new Node(8, node7, null);
        Node node9 = new Node(9, node8, null);
        Node node10 = new Node(10, node6, node5);
        //root
        Node root = new Node(11, node10, node9);

        root.print();
    }

    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public void print() {
            Node current = this;
            if (current == null) {
                return;
            }
/**
 *                  3
 *              4        5
 *            2    3
 */
            while (current != null) {
                if (current.left != null) {
                    Node temp = current.left;
                    while (true) {
                        if (temp.right == null) break;
                        if (temp.right == current) break;
                        temp = temp.right;
                    }

                    if (temp.right == null) {
                        temp.right = current;
                        current = current.left;
                    } else {
                        temp.right = null;
                        System.out.println(current.value);
                        current = current.right;
                    }
                } else {
                    System.out.println(current.value);
                    current = current.right;
                }
            }
        }

    }
}
