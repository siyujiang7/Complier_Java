package simplec;

import java.util.*;
import simplec.parse.*;
import static simplec.AST.*;

public class Scope {
  public static final List<Scope> allScopes = 
    new LinkedList<Scope>();

  public final Map<String, Value> map = 
    new LinkedHashMap<String, Value>();

  public static Scope rootScope = null;
  public static Scope topScope  = null;

  public int i;
  public Scope parent;
  public Node type;

  public Scope(Node type, Scope par) {
    this.i = allScopes.size();
    allScopes.add(this);
    if (rootScope == null)
      rootScope = this;
    else if (par == null)
      this.parent = rootScope;
    else
      this.parent = par;
    this.type = type;
  }

  public Scope(Scope par) {
    this(null, par);
  }

  // Gets the AST node for whatever loop we're in, or null
  // if we aren't in a loop
  public Statement getLoop() {
    if (this.type instanceof Statement.WhileStatement ||
        this.type instanceof Statement.DoWhileStatement ||
        this.type instanceof Statement.ForStatement)
      return (Statement)this.type;
    if (this.parent != null)
      return this.parent.getLoop();
    return null;
  }

  public boolean isInsideLoop() {
    return getLoop() != null;
  }

  public Value put(String id, Value value) {
    if (this.map.containsKey(id))
      Error.DuplicateVariable(value.id);
    return map.put(id, value);
  }

  public Value insert(String id, Value value) { return put(id, value); }

  public Value get(Token id) { return get(id.image); }

  public Value lookup(Token id) { return get(id.image); }

  public Value get(String str) {
    for (Scope s = this; 
         s != null; 
         s = s.parent)
      if (s.map.containsKey(str))
        return s.map.get(str);
    return null;
  }

  public Value lookup(String str) { return get(str); }

  public Value.Function getFunc() {
    for (Scope s = this; 
         s != null; 
         s = s.parent)
      if (s.type instanceof Value.Function)
        return (Value.Function)s.type;
    return null;
  }

  public void insertStdLibFunc(String name, String retType, String... argTypes) {
    Value.Type type = new Value.Type(ID(retType));
    List<Value.Argument> argList = new ArrayList<>();
    int i = 0;
    for (String argType : argTypes) {
      argList.add(
          new Value.Argument(ID(name + i), 
            new Value.Type(ID(argType))));
      i += 1;
    }
    Statement.CompoundStatement cStmt = 
      new Statement.CompoundStatement(ID("{"), 
          new ArrayList<>(), new Scope(this));

    insert(name, new Value.Function(ID(name), type, argList, cStmt));
  }

  // Making Value.Function AST nodes for stdlib functions
  // so we can typecheck them
  public void insertStdLibFuncs() {
    // Stdlib
    insertStdLibFunc("malloc", "long*", "long");

    // Stdio
    insertStdLibFunc("puts", "long", "char*");
    insertStdLibFunc("putchar", "long", "long");

    // Math
    insertStdLibFunc("sin", "double", "double");
    insertStdLibFunc("cos", "double", "double");
    insertStdLibFunc("atan2", "double", "double", "double");

    // String
    insertStdLibFunc("memset", "long*", "long*", "long", "long");
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Symbol table: " + this.i);
    if (this.parent != null)
      sb.append("\tParent: " + this.parent.i + "\t");
    else
      sb.append("\t");
    sb.append("Type: " + this.type + "\n");
    for (Map.Entry symbol : this.map.entrySet()) {
      sb.append("\t"   + symbol.getKey());
      sb.append(" -> " + symbol.getValue());
      sb.append("\n");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }
}
