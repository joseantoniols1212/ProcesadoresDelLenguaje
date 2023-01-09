package visitor;

import java.io.PrintStream;
import ast.*;
import myTable.*;

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
      // out.println("-------");
      // out.println("-"+className);
      // symbolTable.debug();
      // out.println("-------");
      if(className.equals("CallNode")) var = visit((CallNode) root);
      else if (className.equals("BlockNode")) {symbolTable.startScope(); var = visit((BlockNode) root); symbolTable.endScope();}
      else if (className.equals("BinaryOperationNode")) var = visit((BinaryOperationNode) root);
      else if (className.equals("UnaryOperationNode")) var = visit((UnaryOperationNode) root);
      else if (className.equals("ConstantNode")) var = visit((ConstantNode) root);
      else if (className.equals("AsignationNode")) var = visit((AsignationNode) root);
      else if (className.equals("DeclarationNode")) var = visit((DeclarationNode) root);
      else if (className.equals("IfNode")) var = visit((IfNode) root);
      else if (className.equals("IfElseNode")) var = visit((IfElseNode) root);
      else if (className.equals("ForNode")) var = visit((ForNode) root);
      else if (className.equals("ForToNode")) var = visit((ForToNode) root);
      else if (className.equals("ForDownToNode")) var = visit((ForDownToNode) root);
      else if (className.equals("WhileNode")) var = visit((WhileNode) root);
      else if (className.equals("DoWhileNode")) var = visit((DoWhileNode) root);
      else if (className.equals("IdentifierNode")) var = visit((IdentifierNode) root);
      return var;
    }

    public String visit(BlockNode node){
      for(Node n : node.sentences) visit(n);
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
      String operand = visit(node.operand);
      String className = node.operand.getClass().getSimpleName();
      if(node.operation.equals("pre++")){
        if(!className.equals("IdentifierNode")) out.println("error;");
        out.println(operand+" = 1 +"+operand+";");
        out.println(var+" = "+operand+";");
      } else if(node.operation.equals("pre--")){
        if(!className.equals("IdentifierNode")) out.println("error;");
        out.println(operand+" = "+operand+" - 1;");
        out.println(var+" = "+operand+";");
      } else if(node.operation.equals("post++")){
        if(!className.equals("IdentifierNode")) out.println("error;");
        out.println(var+" = "+operand+";");
        out.println(operand+" = "+var+"+1;");
      } else if(node.operation.equals("post--")){
        if(!className.equals("IdentifierNode")) out.println("error;");
        out.println(var+" = "+operand+";");
        out.println(operand+" = "+var+"-1;");
      } else {  // -
        out.println(var+" = "+node.operation+" "+operand+";");
      }
      return var;
    }

    private String visit(BinaryOperationNode node){
      String var = getVar();
      String varDiv = getVar();
      String varPro = getVar();
      if(node.operation.equals("%")){
        String left = visit(node.left);
        String right = visit(node.right);
        out.println(varDiv +"="+ left +"/"+ right+";");        // t0 = l / r
        out.println(varPro +"="+ varDiv +"*"+ right+";");      // t1 = r * t0
        out.println(var +"="+ left +"-"+ varPro+";");          // res = l - t1
      } else {
        out.println(var+" = "+visit(node.left)+" "+node.operation+" "+visit(node.right)+";");
      }
      return var;
    }

    private String visit(ConstantNode node){
      return node.content.toString();
    }

    private String visit(AsignationNode node){
      String id = symbolTable.get(node.name);
      if(id!=null){
        String var = visit(node.content);
        out.println(id+" = "+var+";");
      } else {
        out.println("error;");
        out.println("# variable no declarada");
      }
      return id;
    }
    
    private String visit(DeclarationNode node){
      for(Node declaration : node.declarations){
        String className = declaration.getClass().getSimpleName();
        if(className.equals("AsignationNode")){
          if(!symbolTable.add(((AsignationNode)declaration).name, node.type)){
            out.println("error;");
            out.println("# variable ya declarada");
          }
          visit(declaration);
        }
        else if(className.equals("IdentifierNode")){
          if(!symbolTable.add(((IdentifierNode)declaration).name, node.type)){
            out.println("error;");
            out.println("# variable ya declarada");
          }
        }  
        else {
          out.println("Error visitando declaration node.");
          System.exit(1);
        }
      }
      return null;
    }

    private String visit(IdentifierNode node){
      String id = symbolTable.get(node.name);
      if(id == null){
        out.println("error;");
        out.println("# variable no declarada");
      }
      return id;
    }

    private String visit(IfNode node){
      symbolTable.startScope();
      String trueLabel = getLabel();
      String falseLabel = getLabel();
      visit((LogicNode) node.condition, trueLabel, falseLabel);
      out.println(trueLabel+":");
      if(node.body.getClass().getSimpleName().equals("BlockNode")) visit((BlockNode) node.body);
      else visit(node.body);
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
      if(node.body.getClass().getSimpleName().equals("BlockNode")) visit((BlockNode) node.body);
      else visit(node.body);
      out.println("goto "+continueLabel+";");
      out.println(falseLabel+":");
      if(node.elseBody.getClass().getSimpleName().equals("BlockNode")) visit((BlockNode) node.elseBody);
      else visit(node.elseBody);
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
      if(node.body.getClass().getSimpleName().equals("BlockNode")) visit((BlockNode) node.body);
      else visit(node.body);
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
      if(node.body.getClass().getSimpleName().equals("BlockNode")) visit((BlockNode) node.body);
      else visit(node.body);
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
      if(node.body.getClass().getSimpleName().equals("BlockNode")) visit((BlockNode) node.body);
      else visit(node.body);
      visit(node.postExpresion);
      out.println("goto "+loopLabel+";");
      out.println(falseLabel+":");
      symbolTable.endScope();
      return null;
    }

    private String visit(ForToNode node){
      symbolTable.startScope();
      String trueLabel = getLabel();
      String falseLabel = getLabel();
      String loopLabel = getLabel();
      String var = visit(node.asignation);
      String bound = visit(node.bound);
      out.println(loopLabel+":");
      out.println("if ("+bound+"<"+var+") goto "+ falseLabel+";");
      out.println("goto "+trueLabel+";");
      out.println("goto "+loopLabel+";");
      out.println(trueLabel+":");
      if(node.body.getClass().getSimpleName().equals("BlockNode")) visit((BlockNode) node.body);
      else visit(node.body);
      String step = visit(node.step);
      out.println(var +"="+var+"+"+step+";");
      out.println("goto "+loopLabel+";");
      out.println(falseLabel+":");
      symbolTable.endScope();
      return null;
    }

    private String visit(ForDownToNode node){
      symbolTable.startScope();
      String trueLabel = getLabel();
      String falseLabel = getLabel();
      String loopLabel = getLabel();
      String var = visit(node.asignation);
      String bound = visit(node.bound);
      out.println(loopLabel+":");
      out.println("if ("+var+"<"+bound+") goto "+ falseLabel+";");
      out.println("goto "+trueLabel+";");
      out.println(trueLabel+":");
      if(node.body.getClass().getSimpleName().equals("BlockNode")) visit((BlockNode) node.body);
      else visit(node.body);
      String step = visit(node.step);
      out.println(var +"="+var+"-"+step+";");
      out.println("goto "+loopLabel+";");
      out.println(falseLabel+":");
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
        out.println("if ("+right+"<"+left+") goto "+falseLabel+";");
        out.println("goto "+trueLabel+";");
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
