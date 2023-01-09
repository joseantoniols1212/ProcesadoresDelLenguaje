package ast;

public class ComparationNode extends LogicNode {
    
    public String operation;
    public Node right;
    public Node left;

    public ComparationNode(String operation, Node left, Node right) {
        this.operation = operation;
        this.right = right;
        this.left = left;
    }

    public void not(){
        if(this.operation.equals("==")) this.operation = "!=";
        else if(this.operation.equals("!=")) this.operation = "==";
        else if(this.operation.equals("<")) this.operation = ">=";
        else if(this.operation.equals(">")) this.operation = "<=";
        else if(this.operation.equals("<=")) this.operation = ">";
        else if(this.operation.equals(">=")) this.operation = "<";
    }
}
