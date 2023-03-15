import java.awt.Point;
import java.util.ArrayList;

/**
 * This program implements a TwoDTree data structure.
 * Using iteration and recursion, it can be used to find a point within the tree,
 * or to find all points within a given range of 2 points.
 * Creating X and Y nodes, and adding them to the tree.
 * with X and Y nodes existing on different levels (even and odd) levels respectively
 * @author Connor Norris & Mike Stoj
 * @edu.uwp.cs.340.course CSCI 340 - Data Structures and Algorithm Design
 * @edu.uwp.cs.340.section 001
 * @edu.uwp.cs.340.assignment 4
 * @bugs None
 */

public class TwoDTree {

    private TwoDTreeNode root;


    /**
     * Default constructor that creates an empty TwoDTree
     */
    public TwoDTree() {
        root = null;
    }

    /**
     * One parameter Constructor that takes an ArrayList of points
     * and inserts into a TwoDTree
     *
     * @param pointArrayList an array of points to insert into a TwoDTree
     */
    public TwoDTree(ArrayList<Point> pointArrayList) {

        // for-each loop to iterate through all points in array and insert each element into tree
        for (Point pt : pointArrayList) {
            insert(pt);
        }
    }

    /**
     * This method inserts a single point into a TwoDTree alternating between X and Y nodes
     * depending on the size of the tree and the point
     *
     * @param p point to be inserted
     * @return - true if the point is inserted, false if the point is already in the tree
     */
    public void insert(Point p) {

        int size = 0; // size of the tree

        // insert a point into the tree
        if (root == null) { // if the tree is empty
            root = new TwoDTreeNodeX(); // create a new root
            root.set(p.getX(), p.getY()); // set the root to the point
        } else {
            TwoDTreeNode current = root; // start at the root
            TwoDTreeNode parent = null; // parent is null

            // while the current node is not null
            while (current != null) {
                // if the current node is a leaf
                if (p.getX() == current.getX() && p.getY() == current.getY()) {
                    return;
                }

                // if counter is even
                if (size % 2 == 0) {
                    // if the point is less than the current node
                    if (p.getY() <= current.getY()) {
                        parent = current; // set the parent to the current node
                        current = current.getLeft(); // set the current node to the left child
                        size++;
                        // if the point is greater than the current node
                    } else if (p.getY() > current.getY()) {
                        // set the parent to the current node
                        parent = current;
                        // set the current node to the right child
                        current = current.getRight();
                        size++;
                    }
                    // if counter is odd
                } else if (size % 2 == 1) {
                    // if the point is less than the current node
                    if (p.getX() <= current.getX()) {
                        parent = current; // set the parent to the current node
                        current = current.getLeft(); // set the current node to the left child
                        size++; // increment counter
                    } else if (p.getX() > current.getX()) {
                        parent = current; // set the parent to the current node
                        current = current.getRight(); // set the current node to the right child
                        size++; // increment counter
                    }
                }
            }
            createNode(p, parent, size); // use helper method "createNode" to create an X or Y node
        }
    }

    /**
     * This method creates an X or Y node for a TwoDTree
     * @param p point to be inserted
     * @param parent parent of the node
     * @param size size of the node
     */
    public void createNode(Point p, TwoDTreeNode parent, int size) {
        // if the size is even
        if (size % 2 == 0) {
            TwoDTreeNode newNode = new TwoDTreeNodeY(); // create a new node
            newNode.set(p.getX(), p.getY()); // set the new node to the point
            if (p.getX() < parent.getX()) { // if the point is less than the parent
                parent.setLeft(newNode); // set the left child of the parent to the new node
            } else {
                parent.setRight(newNode); // set the right child of the parent to the new node
            }
        } else { // if the size is odd

            // create a new node
            TwoDTreeNode newNode = new TwoDTreeNodeX();
            // set the new node to the point
            newNode.set(p.getX(), p.getY());

            // if the point is less than the parent
            if (p.getY() < parent.getY()) {
                // set the left child of the parent to the new node
                parent.setLeft(newNode);
            } else {
                // set the right child of the parent to the new node
                parent.setRight(newNode);
            }
        }
    }

    /**
     * This method traverses the tree using a given point to see if the point is in the tree
     *
     * @param p point to be searched for
     * @return true if the point is in the tree, false otherwise
     */
    public boolean search(Point p) {

        // if the root is null
        if (root == null) {
            return false;
        } else {

            // set the current node to the root
            TwoDTreeNode current = root;

            // set the counter to 0
            int size = 0;
            // while the current node is not null
            while (current != null) {
                // if counter is even
                if (size % 2 == 0) {
                    // if the point is less than the current node
                    if (p.getY() < current.getY()) {
                        current = current.getLeft(); // set the current node to the left child
                        size++;
                    } else if (p.getY() > current.getY()) {
                        current = current.getRight(); // set the current node to the right child
                        size++;
                    } else if (p.getX() == current.getX() && p.getY() == current.getY()) { // if the point is equal to the current node
                        return true;
                    } else {
                        size++;
                    }
                } else if (size % 2 == 1) { // if counter is odd
                    // if the point is less than the current node
                    if (p.getX() < current.getX()) {
                        current = current.getLeft(); // set the current node to the left child
                        size++;
                        // if the point is greater than the current node
                    } else if (p.getX() > current.getX()) {
                        current = current.getRight(); // set the current node to the right child
                        size++;
                    } else if (p.getX() == current.getX() && p.getY() == current.getY()) { // if the point is equal to the current node
                        return true;
                    } else {
                        size++;
                    }
                }
            }
        }
        return false; // if the point is not in the tree
    }


    /**
     * This method creates an ArrayList containing all the points
     * within a certain range of points
     *
     * @param p1 beginning bound
     * @param p2 ending bound
     * @return - ArrayList of all points lying in the rectangle bounded by p1 and p2, inclusive.
     */
    public ArrayList<Point> searchRange(Point p1, Point p2) {
        ArrayList<Point> arr = new ArrayList<>(); // arraylist to store the points

        preorder(root, p1, p2, arr); // call helper method to begin to search the tree

        return arr; // return the array list
    }

    /**
     * This method is a helper method to searchRange
     *
     * @param root  the current node
     * @param p1    beginning bound
     * @param p2    ending bound
     * @param arr   array list to store the points
     */
    private void preorder(TwoDTreeNode root, Point p1, Point p2, ArrayList<Point> arr) {
        if (root == null) {
            return;
        }
        if (root.getX() >= Math.min(p1.getX(), p2.getX()) && root.getX() <= Math.max(p1.getX(), p2.getX())
                && root.getY() >= Math.min(p1.getY(), p2.getY()) && root.getY() <= Math.max(p1.getY(), p2.getY())) {
            Point p = new Point((int) root.getX(),(int) root.getY());
            arr.add(p);
        }
        preorder(root.getLeft(), p1, p2, arr);
        preorder(root.getRight(), p1, p2, arr);
    }


    /**
     * Private Interface for the TwoDTreeNode
     */
    private interface TwoDTreeNode {

        // getters and setters
        public void set(double a, double b);

        public double getX();

        public double getY();

        TwoDTreeNode getLeft(); // get the left child

        TwoDTreeNode getRight(); // getters and setters

        void setLeft(TwoDTreeNode newNode); // set the left child of the node

        void setRight(TwoDTreeNode newNode); // set the right child of the node

        Point getPoint(); // get the point of the node
    }

    /**
     * Private class for the TreeNode's x value
     */
    private class TwoDTreeNodeX implements TwoDTreeNode {
        double x;
        double y;

        // two child nodes
        public TwoDTreeNode left;
        public TwoDTreeNode right;

        // set the x and y values
        public void set(double a, double b) {
            this.x = a;
            this.y = b;
        }

        public double getX() {
            return this.x;
        } // get the x value


        public double getY() {
            return this.y;
        } // get the y value

        public TwoDTreeNode getLeft() {
            return (TwoDTreeNode) this.left;
        } // get the left child

        public TwoDTreeNode getRight() {
            return (TwoDTreeNode) this.right;
        } // get the right child

        @Override
        public void setLeft(TwoDTreeNode newNode) {
            this.left = newNode;
        } // set the left child


        @Override
        public void setRight(TwoDTreeNode newNode) {
            this.right = newNode;
        } // set the right child

        public Point getPoint() {
            return new Point((int) this.x, (int) this.y);
        } // get the point within the node
    }

    /**
     * Private class for the TreeNode's y value
     */
    private class TwoDTreeNodeY implements TwoDTreeNode {
        double y;
        double x;
        public TwoDTreeNode left; // left child
        public TwoDTreeNode right; // right child

        // set the x and y values
        public void set(double a, double b) {
            this.x = a;
            this.y = b;
        }

        public double getX() {
            return this.x;
        } // get the x value

        public double getY() {
            return this.y;
        } // get the y value

        public TwoDTreeNode getLeft() {
            return (TwoDTreeNode) this.left;
        } // get the left child

        public TwoDTreeNode getRight() {
            return this.right;
        } // get the right child

        @Override
        public void setLeft(TwoDTreeNode newNode) {  // set the left child
            this.left = newNode;
        } // set the left child

        @Override
        public void setRight(TwoDTreeNode newNode) { // set the right child
            this.right = newNode;
        } // set the right child

        @Override
        public Point getPoint() {
            return new Point((int) this.x, (int) this.y);
        } // get the point within the node
    }
}