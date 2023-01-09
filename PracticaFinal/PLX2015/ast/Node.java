package ast;

import visitor.Visitor;

public abstract class Node {
  public void visit(Visitor visitor){
    visitor.traverseAST(this);
  }
}
