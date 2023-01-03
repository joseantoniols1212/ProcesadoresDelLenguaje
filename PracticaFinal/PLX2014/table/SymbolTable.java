package table;

import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Iterator;

public class SymbolTable {

  private ArrayDeque<HashMap<String, String>> table;

  public SymbolTable(){
    this.table = new ArrayDeque<>();
  }

  public void startScope() { // Crea scope(tabla) y lo mete en el stack
    HashMap<String, String> newScope = new HashMap<>();
    table.push(newScope);
  }

  public void endScope(){ // Elimina el ultimo scope del stack
    table.pop();
  }

  public void add(String id, String type){ // Añadimos al ultimo scope
			table.peek().put(id, type);
  }

  public String get(String id){ // Devuelve el elemento de id , null si no se encuentra
      for(HashMap<String, String> scope : table) {
        if(scope.containsKey(id)) return scope.get(id);
      } 
			return null;
  }

  public boolean inScope(String id){ // Devuelve TRUE si el identificador esta en el scope
    return get(id) != null;
  }

  public void debug(){ 
    for(HashMap<String, String> scope : table) {
      System.out.println(scope.toString());
    }
  }
}
