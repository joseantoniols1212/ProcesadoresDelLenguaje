package ast;

public class ConstantNode<T> extends Node { // Represents primitive types 

    public T content;

    public ConstantNode(T content) {
        this.content = content;
    }

}
