package ast;

import java.util.ArrayList;

public class DeclarationNode extends Node {

    public String type;
    public ArrayList<Node> declarations;

    public DeclarationNode(String type, ArrayList<Node> declarations) {
        this.type = type;
        this.declarations = declarations;
    }

    public DeclarationNode(String type, Node declaration) {
        this.type = type;
        this.declarations = new ArrayList<>();
        this.declarations.add(declaration);
    }

    public void add(Node declaration) {
        this.declarations.add(declaration);
    }
}
