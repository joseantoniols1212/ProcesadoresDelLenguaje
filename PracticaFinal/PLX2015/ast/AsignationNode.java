package ast;

public class AsignationNode extends Node {

    public String name;
    public Node content;

    public AsignationNode(String name, Node content) {
        this.name = name;
        this.content = content;
    }
}
