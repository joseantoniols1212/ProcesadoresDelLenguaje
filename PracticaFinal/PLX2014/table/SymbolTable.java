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

  public boolean add(String id, String type){  // Devolvemos true si se añade correctamente
      if(!inScope(id)){
			  table.peek().put(id, type);
        return true;
      } else if(!inLastScope(id)){
			  table.peek().put(id+"_"+table.size(), type);
        return true;
      } 
      return false;
  }

  private String getShadowed(String id){
      id += "_"+table.size();
      for(HashMap<String, String> scope : table) {
        if(scope.containsKey(id)) return id;
      } 
			return null;
  }

  public String get(String id){ // Devuelve el elemento de id , null si no se encuentra
      // Si tenemos shadowed la variable la devolvemos
      String shadowed = getShadowed(id);
      if(shadowed!=null) return shadowed;
      // En caso contrario buscamos la variable normal
      for(HashMap<String, String> scope : table) {
        if(scope.containsKey(id)) return id;
      }
			return null; // Si no la encontramos devolvemos nul
  }


  public boolean inScope(String id){ // Devuelve TRUE si el identificador esta en el scope
    return get(id) != null;
  }

  public boolean inLastScope(String id){ // Devuelve TRUE si el identificador esta en el ultimo scope
    return table.peek().get(id) != null;
  }

  public void debug(){ 
    for(HashMap<String, String> scope : table) {
      System.out.println(scope.toString());
    }
  }
}
