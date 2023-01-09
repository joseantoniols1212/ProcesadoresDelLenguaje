package ast;

public class UnaryOperationNode extends Node { // Represents binary operations
    
    public String operation;
    public Node operand;

    public UnaryOperationNode(String operation, Node operand) {
        this.operation = operation;
        this.operand = operand;
    }

}
