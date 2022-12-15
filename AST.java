import java.io.PrintStream;
import java.util.ArrayList;

// Class to represent nodes of the AST
public class AST {

    // Output
	  private static PrintStream out = System.out; 

    public static void changePrintOutput(PrintStream o) {
      AST.out = o;
    }


    // Variables globales para numerar etiquetas y variables
    private static int labelNumber = 0;
    private static int varNumber = 0;


    // Metodos para generar nuevas etiquetas y variables
    public static String getLabel() {
        return "L"+AST.labelNumber++;
    }

    public static String getVarName() {
        return "$t"+AST.varNumber++;
    }


    public abstract static class Node {}  // TODO: incluir metodo que permita la visita de los nodos


    public abstract static class ExpresionNode extends Node {
      String variable;
      public ExpresionNode() {
        this.variable = getVarName();
      }
      public ExpresionNode(String variableName) {
        this.variable = variableName;
      }
    }


    public static class BinaryExpresionNode extends ExpresionNode {  // Arithmetic binary operation expresions

        String operation;
        ExpresionNode firstExpresion;
        ExpresionNode secondExpresion;

        public BinaryExpresionNode(String op, ExpresionNode exp1, ExpresionNode exp2) {    
            super();
            this.operation = op;
            this.firstExpresion = exp1;
            this.secondExpresion = exp2;
        }
    }


    public static class UnaryExpresionNode extends ExpresionNode {  // Arithmetic unary operation expresions

        String operation;
        ExpresionNode expresion;

        public UnaryExpresionNode(String op, ExpresionNode exp) {    
            super();
            this.operation = op;
            this.expresion = exp;
        }
    }


    public static class BasicExpresionNode extends ExpresionNode { // Represents ids and constant values
        public BasicExpresionNode(String variableName) {
            super(variableName);
        }
    }


    public static class AsignationExpresionNode extends ExpresionNode {
        
        ExpresionNode expresion;

        public AsignationExpresionNode(String variableName, ExpresionNode expresion) {
            super(variableName);
            this.expresion = expresion;
        }
    }


    public abstract static class ConditionNode extends Node {
        abstract void not();
    }


    public static class ComparationNode extends ConditionNode {
        String operation;
        ExpresionNode firstExpresion;
        ExpresionNode secondExpresion;
        
        public ComparationNode(String operation, ExpresionNode exp1, ExpresionNode exp2) {
            this.operation = operation;
            this.firstExpresion = exp1;
            this.secondExpresion = exp2;
        }

        public void not() {
            if(this.operation.equals("==")) this.operation = "!=";
            else if(this.operation.equals("!=")) this.operation = "==";
            else if(this.operation.equals("<")) this.operation = ">=";
            else if(this.operation.equals(">")) this.operation = "<=";
            else if(this.operation.equals("<=")) this.operation = ">";
            else if(this.operation.equals(">=")) this.operation = "<";
        }
    }


    public static class BinaryConditionNode extends ConditionNode {

        String operation;
        ConditionNode firstCondition;
        ConditionNode secondCondition;
        
        public BinaryConditionNode(String operation, ConditionNode cond1, ConditionNode cond2) {
            this.operation = operation;
            this.firstCondition = cond1;
            this.secondCondition = cond2;
        }

        public void not() {
            this.firstCondition.not();
            this.secondCondition.not();
            switch(this.operation) {
                case "&&":
                  this.operation = "||";
                  break;
                case "||":
                  this.operation = "&&";
                  break;
            }
        }
    }

    
    public abstract static class SentenceNode extends Node {}

  public static class SentencesListNode extends SentenceNode {
        ArrayList<SentenceNode> sentences;
        
        public SentencesListNode() {
            this.sentences = new ArrayList(); 
        }

        public void add(SentenceNode node){
            this.sentences.add(node);
        }
    }

    public static class BasicSentenceNode extends SentenceNode {
        ExpresionNode body;

        public BasicSentenceNode(ExpresionNode exp){
          this.body = exp;
        }
    }


    public static abstract class BranchNode extends SentenceNode {
        public SentenceNode body;
        public ConditionNode condition;

        public BranchNode(SentenceNode exp, ConditionNode cond){
          this.body = exp;
          this.condition = cond;
        }
    }


    public static class IfNode extends BranchNode {
        public IfNode(SentenceNode exp, ConditionNode cond){
            super(exp, cond);
        }
    }
    

    public static class IfElseNode extends BranchNode {
        SentenceNode elseBody;

        public IfElseNode(SentenceNode body, ConditionNode cond, SentenceNode elseBody){
            super(body, cond);
            this.elseBody = elseBody;
        }
    }
    

    public static class WhileNode extends BranchNode {

        public WhileNode(SentenceNode body, ConditionNode cond){
            super(body, cond);
        }
    }
    

    public static class DoWhileNode extends BranchNode {

        public DoWhileNode(SentenceNode body, ConditionNode cond){
            super(body, cond);
        }
    }
    

    public static class ForNode extends BranchNode {

        ExpresionNode preExp, postExp;

        public ForNode(SentenceNode body, ConditionNode cond, ExpresionNode preExp, ExpresionNode postExp){
            super(body, cond);
            this.preExp = preExp;
            this.postExp = postExp;
        }
    }
    

    public static class FunctionNode extends SentenceNode {
      String functionName;
      ExpresionNode argument;

      public FunctionNode(String name, ExpresionNode arg){
          this.functionName = name;
          this.argument = arg;
      }
    } 
    
    public interface Visitor {
        void traverseAST(Node root);
    }

    public static class CtdVisitor implements Visitor {

        public CtdVisitor(Node root){
            traverseAST(root);
        }

        public void traverseAST(Node root, String trueLabel, String falseLabel){
          String className = root.getClass().getSimpleName();
          if(className.equals("ComparationNode")) visit((ComparationNode) root, trueLabel, falseLabel);
          else if (className.equals("BinaryConditionNode"))visit((BinaryConditionNode) root, trueLabel, falseLabel);
        }
        public void traverseAST(Node root){
          String className = root.getClass().getSimpleName();
          if(className.equals("FunctionNode")) visit((FunctionNode) root);
          else if (className.equals("SentencesListNode")) visit((SentencesListNode) root);
          else if (className.equals("BasicSentenceNode")) visit((BasicSentenceNode) root);
          else if (className.equals("BinaryExpresionNode")) visit((BinaryExpresionNode) root);
          else if (className.equals("UnaryExpresionNode")) visit((UnaryExpresionNode) root);
          else if (className.equals("AsignationExpresionNode")) visit((AsignationExpresionNode) root);
          // else if (className.equals("ComparationNode")) visit((ComparationNode) root);
          // else if (className.equals("BinaryConditionNode")) visit((BinaryConditionNode) root);
          else if (className.equals("IfNode")) visit((IfNode) root);
          else if (className.equals("IfElseNode")) visit((IfElseNode) root);
          else if (className.equals("WhileNode")) visit((WhileNode) root);
          else if (className.equals("DoWhileNode")) visit((DoWhileNode) root);
          else if (className.equals("ForNode")) visit((ForNode) root);
        }

        private void visit(SentencesListNode node){
          for(Node n : node.sentences) traverseAST(n);
        }

        private void visit(FunctionNode node){
          traverseAST(node.argument);
          AST.out.println(node.functionName+" "+node.argument.variable+";");
        }

        private void visit(BasicSentenceNode node){
          traverseAST(node.body);
        }
        
        private void visit(BinaryExpresionNode node){
          traverseAST(node.firstExpresion);
          traverseAST(node.secondExpresion);
          AST.out.println(node.variable+" = "+node.firstExpresion.variable+node.operation+node.secondExpresion.variable+";");
        }

        private void visit(UnaryExpresionNode node){
          traverseAST(node.expresion);
          AST.out.println(node.variable+" = "+node.operation+node.expresion.variable+";");
        }

        private void visit(AsignationExpresionNode node){
          traverseAST(node.expresion);
          AST.out.println(node.variable+" = "+node.expresion.variable+";");
        }

        private void visit(ComparationNode node, String trueLabel, String falseLabel){
          traverseAST(node.firstExpresion);
          traverseAST(node.secondExpresion);
          // TODO: hacer que funcione solo usando la operacion "<" o "=="
          if(node.operation.equals("<") || node.operation.equals("==")){
            AST.out.println("if ("+node.firstExpresion.variable+node.operation+node.secondExpresion.variable+") goto "+trueLabel+";");
            AST.out.println("goto "+falseLabel+";");
          } else if(node.operation.equals("<=") || node.operation.equals(">=")) {
            visit(new BinaryConditionNode("||",
                    new ComparationNode(node.operation.substring(1,2), node.firstExpresion, node.secondExpresion),
                    new ComparationNode("==", node.firstExpresion, node.secondExpresion)
                  ), trueLabel, falseLabel);
          } else if(node.operation.equals(">")){
            visit(new BinaryConditionNode("||",
                    new ComparationNode("<", node.firstExpresion, node.secondExpresion),
                    new ComparationNode("==", node.firstExpresion, node.secondExpresion)
                  ), falseLabel, trueLabel);
          } else if(node.operation.equals("!=")){
            visit(new ComparationNode("==", node.firstExpresion, node.secondExpresion), falseLabel, trueLabel);
          }
        }

        private void visit(BinaryConditionNode node, String trueLabel, String falseLabel){
          if(node.operation.equals("&&")){
            String intermediateTrueLabel = getLabel();
            traverseAST(node.firstCondition, intermediateTrueLabel, falseLabel);
            AST.out.println(intermediateTrueLabel+":");
            traverseAST(node.secondCondition, trueLabel, falseLabel);
          } else { // "||"
            String intermediateFalseLabel = getLabel();
            traverseAST(node.firstCondition, trueLabel, intermediateFalseLabel);
            AST.out.println(intermediateFalseLabel+":");
            traverseAST(node.secondCondition, trueLabel, falseLabel);
          }
        }

        private void visit(IfNode node){
          String trueLabel = getLabel();
          String falseLabel = getLabel();
          traverseAST(node.condition, trueLabel, falseLabel);
          AST.out.println(trueLabel+":");
          traverseAST(node.body);
          AST.out.println(falseLabel+":");
        }

        private void visit(IfElseNode node){
          String trueLabel = getLabel();
          String falseLabel = getLabel();
          String continueLabel = getLabel();
          traverseAST(node.condition, trueLabel, falseLabel);
          AST.out.println(trueLabel+":");
          traverseAST(node.body);
          AST.out.println("goto "+continueLabel+";");
          AST.out.println(falseLabel+":");
          traverseAST(node.elseBody);
          AST.out.println(continueLabel+":");
        }

        private void visit(WhileNode node){
          String trueLabel = getLabel();
          String falseLabel = getLabel();
          String repeatLabel = getLabel();
          AST.out.println(repeatLabel+":");
          traverseAST(node.condition, trueLabel, falseLabel);
          AST.out.println(trueLabel+":");
          traverseAST(node.body);
          AST.out.println("goto "+repeatLabel+";");
          AST.out.println(falseLabel+":");
        }

        private void visit(DoWhileNode node){
          String trueLabel = getLabel();
          String falseLabel = getLabel();
          AST.out.println(trueLabel+":");
          traverseAST(node.body);
          traverseAST(node.condition, trueLabel, falseLabel);
          AST.out.println(falseLabel+":");
        }

        private void visit(ForNode node){
          String trueLabel = getLabel();
          String falseLabel = getLabel();
          String repeatLabel = getLabel();
          traverseAST(node.preExp);
          AST.out.println(repeatLabel+":");
          traverseAST(node.condition, trueLabel, falseLabel);
          AST.out.println(trueLabel+":");
          traverseAST(node.body);
          traverseAST(node.postExp);
          AST.out.println("goto "+repeatLabel+";");
          AST.out.println(falseLabel+":");
        }
    }
}
