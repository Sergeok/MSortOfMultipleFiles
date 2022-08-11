package container;

public class Node {

    private int index;
    private Node parent;

    public Node(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Node getParent() {
        return parent;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}