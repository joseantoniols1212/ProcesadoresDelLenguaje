package visitor;

import java.io.PrintStream;
import ast.*;

public class DebugVisitor implements Visitor {

    private int depth = -1;

    private void indent(int depth){
      for(int i = 0; i < depth; i++){
         System.out.print("\t");      
      }
    }
    private void print(String str){
      indent(depth);
      out.print(str+"\n");
    }

    private PrintStream out = System.out;

    public void changeOutput(PrintStream output) {
      this.out = output;
    } 

    public void traverseAST(Node root){
      String className = root.getClass().getSimpleName();
      depth++;
      print("-"+className);
      if(className.equals("CallNode")) visit((CallNode) root);
      else if (className.equals("BlockNode")) visit((BlockNode) root);
      else if (className.equals("BinaryOperationNode")) visit((BinaryOperationNode) root);
      else if (className.equals("UnaryOperationNode")) visit((UnaryOperationNode) root);
      else if (className.equals("ConstantNode")) visit((ConstantNode) root);
      else if (className.equals("AsignationNode")) visit((AsignationNode) root);
      else if (className.equals("DeclarationNode")) visit((DeclarationNode) root);
      else if (className.equals("IfNode")) visit((IfNode) root);
      else if (className.equals("IfElseNode")) visit((IfElseNode) root);
      else if (className.equals("ForNode")) visit((ForNode) root);
      else if (className.equals("ForToNode")) visit((ForToNode) root);
      else if (className.equals("ForDownToNode")) visit((ForDownToNode) root);
      else if (className.equals("WhileNode")) visit((WhileNode) root);
      else if (className.equals("DoWhileNode")) visit((DoWhileNode) root);
      else if (className.equals("ComparationNode")) visit((ComparationNode) root);
      else if (className.equals("LogicOperationNode")) visit((LogicOperationNode) root);
      else if (className.equals("IdentifierNode")) visit((IdentifierNode) root);
      depth--;
    }

    private void visit(CallNode node){
      print(node.name);
      for(Node n : node.args) traverseAST(n);
    }

    private void visit(ComparationNode node){
      print(node.operation);
      traverseAST(node.left);
      traverseAST(node.right);
    }

    private void visit(IdentifierNode node){
      print(node.name);
    }

    private void visit(LogicOperationNode node){
      print(node.operation);
      traverseAST(node.left);
      traverseAST(node.right);
    }
    
    private void visit(BlockNode node){
      for(Node n : node.sentences) traverseAST(n);
    }

    private void visit(DeclarationNode node){
      print(node.type);
      for(Node n : node.declarations) traverseAST(n);
    }
    
    private void visit(BinaryOperationNode node){
      print(node.operation);
      traverseAST(node.left);
      traverseAST(node.right);
    }
    
    private void visit(UnaryOperationNode node){
      print(node.operation);
      traverseAST(node.operand);
    }
    
    private void visit(ConstantNode node){
      print(node.content.toString());
    }
    
    private void visit(AsignationNode node){
      print(node.name);
      traverseAST(node.content);
    }
    
    private void visit(IfNode node){
      traverseAST(node.condition);
      traverseAST(node.body);
    }

    private void visit(IfElseNode node){
      traverseAST(node.condition);
      traverseAST(node.body);
      traverseAST(node.elseBody);
    }

    private void visit(WhileNode node){
      traverseAST(node.condition);
      traverseAST(node.body);
    }

    private void visit(DoWhileNode node){
      traverseAST(node.condition);
      traverseAST(node.body);
    }

    private void visit(ForNode node){
      traverseAST(node.condition);
      traverseAST(node.preExpresion);
      traverseAST(node.postExpresion);
      traverseAST(node.body);
    }

    private void visit(ForToNode node){
      traverseAST(node.asignation);
      traverseAST(node.bound);
      traverseAST(node.step);
      traverseAST(node.body);
    }

    private void visit(ForDownToNode node){
      traverseAST(node.asignation);
      traverseAST(node.bound);
      traverseAST(node.step);
      traverseAST(node.body);
    }
}
