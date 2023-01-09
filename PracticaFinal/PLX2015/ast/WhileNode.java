package ast;

public class WhileNode extends Node { // Represents branches in execution flow

    public Node condition;
    public Node body;

    public WhileNode(Node condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

}
