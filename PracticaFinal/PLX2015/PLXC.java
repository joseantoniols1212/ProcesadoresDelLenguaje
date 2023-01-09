import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.Reader;

import visitor.*;
import ast.*;
import myTable.*;

public class PLXC {	   
	public static void main(String argv[]) {    
    try {
      Visitor visitor = new CtdVisitor();
      Reader in = new InputStreamReader(System.in);	
      if (argv.length>0) {
    	  in = new FileReader(argv[0]);
      }
      if (argv.length>1) {
        if(argv[1].equals("-d")){
          visitor = new DebugVisitor();
        } else {
          visitor.changeOutput(new PrintStream(new FileOutputStream(argv[1])));
        }
      }
      parser p = new parser(new Yylex(in));
      ((Node) p.parse().value).visit(visitor);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
