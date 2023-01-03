package visitor;

import java.io.PrintStream;
import ast.*;
import table.*;

public class CtdVisitor implements Visitor {

    private SymbolTable symbolTable = new SymbolTable();

    private int label = 0;
    private int var = 0;

    private PrintStream out = System.out;

    public void changeOutput(PrintStream output) {
      this.out = output;
    } 

    private String getLabel(){
      return "L"+label++;
    }

    private String getVar(){
      return "t"+var++;
    }
    
    public void traverseAST(Node root){
      visit(root);
    }

    private String visit(Node root){
      String className = root.getClass().getSimpleName();
      String var = null;
			
      out.println("---------------------");
      out.println(className);
      symbolTable.debug();
      out.println(symbolTable.get("a"));
      out.println("---------------------");
      if(className.equals("CallNode")) var = visit((CallNode) root);
      else if (className.equals("BlockNode")) var = visit((BlockNode) root);
      else if (className.equals("BinaryOperationNode")) var = visit((BinaryOperationNode) root);
      else if (className.equals("UnaryOperationNode")) var = visit((UnaryOperationNode) root);
      else if (className.equals("ConstantNode")) var = visit((ConstantNode) root);
      else if (className.equals("AsignationNode")) var = visit((AsignationNode) root);
      else if (className.equals("DeclarationNode")) var = visit((DeclarationNode) root);
      else if (className.equals("IfNode")) var = visit((IfNode) root);
      else if (className.equals("ForNode")) var = visit((ForNode) root);
      else if (className.equals("ForToNode")) var = visit((ForToNode) root);
      else if (className.equals("ForDownToNode")) var = visit((ForDownToNode) root);
      else if (className.equals("WhileNode")) var = visit((WhileNode) root);
      else if (className.equals("DoWhileNode")) var = visit((DoWhileNode) root);
      else if (className.equals("IdentifierNode")) var = visit((IdentifierNode) root);
      return var;
    }

    public String visit(BlockNode node){
      symbolTable.startScope();
      for(Node n : node.sentences) visit(n);
      symbolTable.endScope();
      return null;
    }
    
    private String visit(CallNode node){
      if(node.name.equals("print")){
        for(Node n : node.args){
          String var = visit(n);
          out.println("print "+var+";");
        }
      }
      return null;
    }

    private String visit(UnaryOperationNode node){
      String var = getVar();
      out.println(var+" = "+node.operation+" "+visit(node.operand)+";");
      return var;
    }

    private String visit(BinaryOperationNode node){
      String var = getVar();
      out.println(var+" = "+visit(node.left)+" "+node.operation+" "+visit(node.right)+";");
      return var;
    }

    private String visit(ConstantNode node){
      return node.content.toString();
    }

    private String visit(AsignationNode node){
      if(symbolTable.inScope(node.name)){
        String var = visit(node.content);
        out.println(node.name+" = "+var+";");
      } else {
        out.println("error;");
        out.println("# variable no declarada");
      }
      return node.name;
    }
    
    private String visit(DeclarationNode node){
      for(Node declaration : node.declarations){
        String className = declaration.getClass().getSimpleName();
        if(className.equals("AsignationNode")){
          symbolTable.add(((AsignationNode)declaration).name, node.type);
          visit(declaration);
        }
        else if(className.equals("IdentifierNode")){
          symbolTable.add(((IdentifierNode)declaration).name, node.type);
        }  
        else {
          out.println("Error visitando declaration node.");
          System.exit(1);
        }
      }
      return null;
    }

    private String visit(IdentifierNode node){
      if(!symbolTable.inScope(node.name)){
        out.println("error;");
        out.println("# variable no declarada");
      }
      return node.name;
    }

    private String visit(IfNode node){
      symbolTable.startScope();
      String trueLabel = getLabel();
      String falseLabel = getLabel();
      visit((LogicNode) node.condition, trueLabel, falseLabel);
      out.println(trueLabel+":");
      visit(node.body);
      out.println(falseLabel+":");
      symbolTable.endScope();
      return null;
    }

    private String visit(IfElseNode node){
      symbolTable.startScope();
      String trueLabel = getLabel();
      String falseLabel = getLabel();
      String continueLabel = getLabel();
      visit((LogicNode) node.condition, trueLabel, falseLabel);
      out.println(trueLabel+":");
      visit(node.body);
      out.println("goto "+continueLabel+";");
      out.println(falseLabel+":");
      visit(node.elseBody);
      out.println(continueLabel+":");
      symbolTable.endScope();
      return null;
    }

    private String visit(WhileNode node){
      symbolTable.startScope();
      String trueLabel = getLabel();
      String falseLabel = getLabel();
      String loopLabel = getLabel();
      out.println(loopLabel+":");
      visit((LogicNode) node.condition, trueLabel, falseLabel);
      out.println(trueLabel+":");
      visit(node.body);
      out.println("goto "+loopLabel+";");
      out.println(falseLabel+":");
      symbolTable.endScope();
      return null;
    }

    private String visit(DoWhileNode node){
      symbolTable.startScope();
      String trueLabel = getLabel();
      String falseLabel = getLabel();
      String loopLabel = getLabel();
      out.println(trueLabel+":");
      visit(node.body);
      visit((LogicNode) node.condition, trueLabel, falseLabel);
      out.println(falseLabel+":");
      symbolTable.endScope();
      return null;
    }

    private String visit(ForNode node){
      symbolTable.startScope();
      String trueLabel = getLabel();
      String falseLabel = getLabel();
      String loopLabel = getLabel();
      visit(node.preExpresion);
      out.println(loopLabel+":");
      visit((LogicNode) node.condition, trueLabel, falseLabel);
      out.println(trueLabel+":");
      visit(node.body);
      visit(node.postExpresion);
      out.println("goto "+loopLabel+";");
      out.println(falseLabel+":");
      symbolTable.endScope();
      return null;
    }

    private String visit(ForToNode node){
      symbolTable.startScope();
      Node condition = new ComparationNode("<", new IdentifierNode(node.asignation.name), node.bound);
      Node postExpresion = new BinaryOperationNode("+", new IdentifierNode(node.asignation.name), node.step);
      ForNode translation = new ForNode(condition, node.asignation, postExpresion, node.body);
      visit((ForNode)translation);
      symbolTable.endScope();
      return null;
    }

    private String visit(ForDownToNode node){
      symbolTable.startScope();
      Node condition = new ComparationNode("<", new IdentifierNode(node.asignation.name), node.bound);
      Node postExpresion = new BinaryOperationNode("-", new IdentifierNode(node.asignation.name), node.step);
      ForNode translation = new ForNode(condition, node.asignation, postExpresion, node.body);
      visit((ForNode)translation);
      symbolTable.endScope();
      return null;
    }

    private void visit(LogicNode node, String trueLabel, String falseLabel){
      String className = node.getClass().getSimpleName();
      if(className.equals("ComparationNode")) visit((ComparationNode) node, trueLabel, falseLabel);
      else if (className.equals("LogicOperationNode")) visit((LogicOperationNode) node, trueLabel, falseLabel);
    }

    private void visit(ComparationNode node, String trueLabel, String falseLabel){
      String left = visit(node.left);
      String right = visit(node.right);
      if(node.operation.equals("<") || node.operation.equals("==")){
        out.println("if ("+left+node.operation+right+") goto "+trueLabel+";");
        out.println("goto "+falseLabel+";");
      } else if(node.operation.equals("<=")) {
        out.println("if ("+right+"<"+left+") goto "+trueLabel+";");
        out.println("goto "+falseLabel+";");
      } else if(node.operation.equals(">=")) {
        out.println("if ("+left+"<"+right+") goto "+falseLabel+";");
        out.println("goto "+trueLabel+";");
      } else if(node.operation.equals(">")){
        out.println("if ("+right+"<"+left+") goto "+trueLabel+";");
        out.println("goto "+falseLabel+";");
      } else if(node.operation.equals("!=")){
        out.println("if ("+left+"=="+right+") goto "+falseLabel+";");
        out.println("goto "+trueLabel+";");
      }
    }

    private String visit(LogicOperationNode node, String trueLabel, String falseLabel){
      if(node.operation.equals("&&")){
        String intermediateTrueLabel = getLabel();
        visit(node.left, intermediateTrueLabel, falseLabel);
        out.println(intermediateTrueLabel+":");
        visit(node.right, trueLabel, falseLabel);
      } else { // "||"
        String intermediateFalseLabel = getLabel();
        visit(node.left, trueLabel, intermediateFalseLabel);
        out.println(intermediateFalseLabel+":");
        visit(node.right, trueLabel, falseLabel);
      }
      return null;
    }

}
