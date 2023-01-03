package visitor;

import java.io.PrintStream;
import ast.Node;

public interface Visitor {
    void traverseAST(Node root);
    void changeOutput(PrintStream output);
}

