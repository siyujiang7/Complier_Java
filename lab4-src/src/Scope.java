  package simplec;

  import java.util.*;
  import simplec.parse.*;
  import java.util.HashMap;
  import java.util.Map;
  import java.util.Iterator;
  import java.util.Set;

  public class Scope {
    public static Scope rootScope = null;
    public static Scope topScope = null;
    public String key;
    public AST.Value node;
    public Scope parentScope;
    public HashMap<String,AST.Node> symbol_table;

    public AST.Node lookup(String id) {
      for (Scope scope = this; scope != null; scope = scope.parentScope) {
        AST.Node found = scope.symbol_table.get(id);
        if (found != null) return found;
      }
      return null;
    }
    public void insert(String key, AST.Node node){
      AST.Node func = this.lookup(key);
      if ((func != null && func instanceof AST.Value.Function) || symbol_table.containsKey(key)){
        Error.DuplicateVariable(node.id);
      }

      //this.key = key;
      //this.node = node;
      this.symbol_table.put(key,node);
    }
    public Scope(Scope par) {
    // TODO: Implement scope/symbol tables
    // Feel free to implement this in any way you want
    // Just make sure that while parsing, topScope refers
    // to the current scope. Whenever we make a new variable
    // or statement, we just set that objects scope to be
    // Scope.topScope, which makes some other things easier.
      this.symbol_table = new HashMap<String, AST.Node>();
      this.parentScope = par;
    }
  }
