package ast;

import java.util.ArrayList;

public class CallNode extends Node { // Represents call to function
    
    public String name;
    public ArrayList<Node> args;    

    public CallNode(String name, ArrayList<Node> args) {
        this.name = name;
        this.args = args;
    }

    public CallNode(String name, Node arg) {
        this.name = name;
        this.args = new ArrayList<Node>();
        this.args.add(arg);
    }

}
