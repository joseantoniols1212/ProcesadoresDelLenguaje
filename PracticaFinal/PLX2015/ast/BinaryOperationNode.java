package ast;

public class BinaryOperationNode extends Node { // Represents binary operations
    
    public String operation;
    public Node right;
    public Node left;

    public BinaryOperationNode(String operation, Node left, Node right) {
        this.operation = operation;
        this.right = right;
        this.left = left;
    }
}
