package ast;

import java.util.ArrayList;

public class BlockNode extends Node {

  public ArrayList<Node> sentences;
  
  public BlockNode() {
      this.sentences = new ArrayList(); 
  }

  public void add(Node node){
      this.sentences.add(node);
  }
}
