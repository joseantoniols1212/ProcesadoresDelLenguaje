package ast;

public class IfNode extends Node { // Represents branches in execution flow

    public Node condition;
    public Node body;

    public IfNode(Node condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

}
