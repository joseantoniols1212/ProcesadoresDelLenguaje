package ast;

public class ForNode extends Node {

    public Node preExpresion, postExpresion, body, condition;

    public ForNode(Node condition, Node preExpresion, Node postExpresion, Node body){
        this.preExpresion = preExpresion;
        this.postExpresion= postExpresion;
        this.body = body;
        this.condition = condition;
    }
}
