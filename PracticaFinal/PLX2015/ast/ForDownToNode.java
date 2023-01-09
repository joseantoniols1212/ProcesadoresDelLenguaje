package ast;

public class ForDownToNode extends Node {

    public AsignationNode asignation;
    public Node bound, body, step;

    public ForDownToNode(AsignationNode asignation, Node bound, Node step, Node body){
        this.asignation = asignation;
        this.bound = bound;
        this.body = body;
        this.step = step;
    }

    public ForDownToNode(AsignationNode asignation, Node bound, Node body){
        this.asignation = asignation;
        this.bound = bound;
        this.body = body;
        this.step = new ConstantNode<Integer>(1);
    }
}
