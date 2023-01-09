package ast;

public class LogicOperationNode extends LogicNode {
    
    public String operation;
    public LogicNode right;
    public LogicNode left;

    public LogicOperationNode(String operation, LogicNode left, LogicNode right) {
        this.operation = operation;
        this.right = right;
        this.left = left;
    }

    public void not(){
        this.left.not(); 
        this.right.not(); 
        if(this.operation.equals("&&")) {
            this.operation = "||";
        } else if(this.operation.equals("||")){
            this.operation = "&&";
        }
    }
}
