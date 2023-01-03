package ast;

public class IfElseNode extends Node { // Represents branches in execution flow

    public Node condition, body, elseBody;

    public IfElseNode(Node condition, Node body, Node elseBody) {
        this.condition = condition;
        this.body = body;
        this.elseBody = elseBody;
    }

}
