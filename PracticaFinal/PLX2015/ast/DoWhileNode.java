package ast;

public class DoWhileNode extends Node {

    public Node condition;
    public Node body;

    public DoWhileNode(Node condition, Node body) {
        this.condition = condition;
        this.body = body;
    }

}
