package ast;

import java.util.ArrayList;

public class DeclarationNode extends Node {

    public String type;
    public ArrayList<Node> declarations;

    public DeclarationNode(String type, ArrayList<Node> declarations) {
        this.type = type;
        this.declarations = declarations;
    }
}
