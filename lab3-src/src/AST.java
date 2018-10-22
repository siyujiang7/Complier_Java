package simplec;

import java.util.*;
import simplec.parse.*;

public abstract class AST {
  public static void main(String... args) {
    try {
      Unit.Program goal = new SimpleC(System.in).goal();
      new Print(goal);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Token ID(String str) {
    return Token.newToken(SimpleCConstants.ID, str);
  }

  // This lists all of our superclasses that we can visit.
  // You shouldn't need to add anything to the extends here,
  // but you can if you really want.
  interface Visitor<T> extends 
    Unit.Visitor<T>, 
    Statement.Visitor<T>, 
    Expression.Visitor<T>, 
    Value.Visitor<T>,
    FunctionOrVarList.Visitor<T>{}

  /* Print is a class that we'll use to traverse our AST,
   * printing out the nodes as we do so.
   * Whenever you make a new AST node class, you'll need
   * to make make a new visit method, indicating how you
   * want it to print. Make you sure you get nesting the
   * same as in the tests.
   */
  public static class Print implements Visitor<Void> {
    int i = 0;

    public Print(Unit.Program astNode) {
      if (astNode == null)
        return;
      astNode.accept(this);
      System.out.flush();
    }

    private void indent(int level) {
      for (int i = 0; i < level; i++)
        System.out.print(' ');
    }

    private void say(String str) {
      System.out.print(str);
    }

    private void sayln(String str) {
      System.out.println(str);
      System.out.flush();
    }

    private void say(Token token) {
      System.out.print(token.image);
    }

    private void sayln(Token token) {
      System.out.println(token.image);
      System.out.flush();
    }

    // These few void print functions represent
    // the way to print our superclass types (Unit, Value, etc)
    // and lets us specify that any null Statement gets printed
    // as ??; and any null expression gets printed as ??.
    // Look at the visit methods for individial subclasses
    // for the actual place they get printed
    void print(Unit unit, int i) {
      int save = this.i;
      this.i = i;
      unit.accept(this);
      this.i = save;
    }

    void print(Value value, int i) {
      int save = this.i;
      this.i = i;
      value.accept(this);
      this.i = save;
    }

    void print(FunctionOrVarList fov, int i) {
      int save = this.i;
      this.i = i;
      fov.accept(this);
      this.i = save;
    }

    void print(Statement stmt, int i) {
      if (stmt == null) {
        indent(i);
        sayln("??;");
      } else {
        int save = this.i;
        this.i = i;
        stmt.accept(this);
        this.i = save;
      }
    }

    void print(Expression expr, int i) {
      if (expr == null) {
        say("??");
      } else {
        int save = this.i;
        this.i = i;
        expr.accept(this);
        this.i = save;
      }
    }

    // Now we get into our actual visit methods. We need one for
    // every non-abstract node class. Most compile errors you'll
    // get will be about either not having a visit method for your
    // class, or your class (i.e. Program) not being listed
    // in the visitor interface for its superclass (i.e. Unit).
    /* Units */
    @Override
    public Void visit(Unit.Program program) {
      for (FunctionOrVarList fov : program.fovList)
        print(fov, i);
      return null;
    }

    /* FunctionOrVarLists */
    @Override
    public Void visit(FunctionOrVarList.Function func) {
      // Prints signatures Douglas Comer style :)
      // See: Xinu source code
      say(func.varType);
      say(" ");
      say(func.id);
      if (func.argList == null ||
          func.argList.size() == 0) {
        sayln("()");
      } else {
        sayln("(");
        int argIndex = 0;
        for (Value.Argument arg : func.argList) {
          print(arg, i + 4);
          if (argIndex++ < func.argList.size() - 1)
            sayln(",");
        }
        sayln("");
        indent(i + 2); sayln(")");
      }
      print(func.cStmt, i);
      return null;
    }

    @Override
    public Void visit(FunctionOrVarList.VariableList varList) {
      for (Value.Variable var : varList.vars)
        print(var, i);
      return null;
    }

    @Override
    public Void visit(Expression.Call expr) {
      say(expr.id);
      say("(");
      LinkedList<Expression> list =new LinkedList<Expression>(expr.args);
      for(int j=0;j<list.size();j++){
        print(list.get(j),i);
        if(j < list.size()-1){
          say(", ");
        }
      }
      say(")");
      return null;
    }


    /* Values */
    @Override
    public Void visit(Value.Variable variable) {
      indent(i);
      sayln(variable.type + " " + variable.id + ";");
      return null;
    }

    @Override
    public Void visit(Value.Argument argument) {
      indent(i);
      say(argument.type);
      say(" ");
      say(argument.id);
      return null;
    }

    /* Expressions */
    private void visit(Expression child, Expression parent) {
      if (child == null || parent == null) {
        print(child, i);
        return;
      }

      if (child.precendence <= parent.precendence)
        say("(");
      print(child, i);
      if (child.precendence <= parent.precendence)
        say(")");
    }

    // Unary expressions
    @Override
    public Void visit(Expression.Ref expr) {
      say("&"); print(expr.expr, i);
      return null;
    }

    @Override
    public Void visit(Expression.Positive expr) {
      say("+"); print(expr.expr, i);
      return null;
    }

    @Override
    public Void visit(Expression.Negative expr) {
      say("-"); print(expr.expr, i);
      return null;
    }
    @Override
    public Void visit(Expression.Multiple expr) {
      say("*"); print(expr.expr, i);
      return null;
    }
    // TODO: Add more expressions

    // Binary expressions
    @Override
    public Void visit(Expression.Or expr) {
      visit(expr.left, expr);
      say(" "); say(expr.token); say(" ");
      visit(expr.right, expr);
      return null;
    }
    @Override
    public Void visit(Expression.And expr) {
      visit(expr.left, expr);
      say(" "); say(expr.token); say(" ");
      visit(expr.right, expr);
      return null;
    }
    @Override
    public Void visit(Expression.Equal expr) {
      visit(expr.left, expr);
      say(" "); say(expr.token); say(" ");
      visit(expr.right, expr);
      return null;
    }
    @Override
    public Void visit(Expression.Relation expr) {
      visit(expr.left, expr);
      say(" "); say(expr.token); say(" ");
      visit(expr.right, expr);
      return null;
    }
    @Override
    public Void visit(Expression.Add expr) {
      visit(expr.left, expr);
      say(" "); say(expr.token); say(" ");
      visit(expr.right, expr);
      return null;
    }
    @Override
    public Void visit(Expression.Mul expr) {
      visit(expr.left, expr);
      say(" "); say(expr.token); say(" ");
      visit(expr.right, expr);
      return null;
    }
    // TODO: Add more expressions

    // Primary Expressions

    @Override
    public Void visit(Expression.Char expr) {
      say("'");
      // Handle printing escape characters individually
      switch(expr.ch) {
        case 0x07: say("\\a");
                   break;
        case 0x08: say("\\b");
                   break;
        case 0x09: say("\\t");
                   break;
        case 0x0A: say("\\n");
                   break;
        case 0x0B: say("\\v");
                   break;
        case 0x0C: say("\\f");
                   break;
        case 0x0D: say("\\r");
                   break;
        default :  say(String.valueOf(expr.ch));
      }
      say("'");
      return null;
    }

    @Override
    public Void visit(Expression.ID id) {
      say(id.id);
      return null;
    }
    
    @Override
    public Void visit(Expression.Array array) {
      say(array.id);
      say("[");
      print(array.index, i);
      say("]");
      return null;
    }

    @Override
    public Void visit(Expression.Int expr) {
      say(String.valueOf(expr.value));
      return null;
    }

    @Override
    public Void visit(Expression.Double expr) {
      say(String.valueOf(expr.value));
      return null;
    }

    // TODO: Add more expression

    /* Statements */

    @Override
    public Void visit(Statement.AssignStatement aStmt) {
      indent(i);
      say(aStmt.varName);
      if (aStmt.index != null) {
        say("[");
        print(aStmt.index, i);
        say("]");
      }
      say(" = ");
      print(aStmt.expression, i);
      sayln(";");
      return null;
    }

    @Override
    public Void visit(Statement.CompoundStatement cStmt) {
      indent(i); sayln("{");
      for (Statement stmt : cStmt.stmtList)
        print(stmt, i + 2);
      indent(i); sayln("}");
      return null;
    }

    @Override
    public Void visit(Statement.ForStatement fStmt) {
      indent(i); sayln("for (");
      print(fStmt.init, i + 4);
      indent(i + 4); print(fStmt.cond, i);
      sayln(";");
      print(fStmt.update, i + 4);
      indent(i + 2); sayln(")");
      print(fStmt.body, i + 2);
      return null;
    }

    @Override
    public Void visit(Statement.IfStatement ifstmt){
      indent(i);
      say("if (");
      print(ifstmt.cond, i+2);
      sayln(")");
      print(ifstmt.body, i+2);
      if(ifstmt.elseStmt !=null){
        indent(i);
        sayln("else");
        print(ifstmt.elseStmt, i+2);
      }
      return null;
    }

    @Override
    public Void visit(Statement.WhileStatement whstmt){
      indent(i);
      say("while (");
      print(whstmt.cond, i+2);
      sayln(")");
      print(whstmt.body, i+2);
      return null;
    }

    @Override
    public Void visit(Statement.DoWhileStatement dwStmt) {
      indent(i); sayln("do");
      print(dwStmt.body, i + 2);
      indent(i); say("while (");
      print(dwStmt.cond, i + 2);
      sayln(");");
      return null;
    }

    @Override
    public Void visit(Statement.BreakStatement bStmt) {
      indent(i); sayln("break;");
      return null;
    }
    @Override
    public Void visit(Statement.ContinueStatement bStmt) {
      indent(i); sayln("continue;");
      return null;
    }

    @Override
    public Void visit(Statement.CallStatement callStmt) {
      indent(i); print(callStmt.expr,i);sayln(";");
      return null;
    }

    @Override
    public Void visit(Statement.ReturnStatement rStmt) {
      indent(i); say("return");
      if (rStmt.retVal != null) {
        say(" ");
        print(rStmt.retVal, i);
      }
      sayln(";");
      return null;
    }

    @Override
    public Void visit(Statement.VariableDecls decls) {
      for (Value.Variable var : decls.vars)
        print(var, i);
      return null;
    }
    //TODO, add more statements

  }

  public static abstract class Node {
    public final Token token;
      Node(Token token) {
        this.token = token;
      }
    }

  public static abstract class Expression extends Node {
    public final int precendence;
    public Expression(Token op, int precendence) {
      super(op);
      this.precendence = precendence;
    }
    public static class Call extends Expression {
      public Token id;
      public List<Expression>args;
      public Call(Token id, List<Expression> args) {
        super(id, 8);
        this.id = id;
        this.args=args;
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public interface Visitor<T> { 
      T visit(Or expr); 
      T visit(And expr);
      T visit(Equal expr);
      T visit(Relation expr);
      T visit(Add expr);
      T visit(Mul expr);
      T visit(Call expr);
      T visit(Positive expr); 
      T visit(Ref expr); 
      T visit(Negative expr); 
      T visit(Multiple expr); 
      // TODO, add more here
      T visit(Char expr); 
      T visit(Int expr); 
      T visit(Double expr); 
      T visit(ID expr); 
      T visit(Array expr);
    }

    public abstract <T> T accept(Visitor<T> visitor);


    public static abstract class Binary extends Expression {
      public final Expression left, right;
      Binary(Token op, int precedence, Expression left, Expression right) {
        super(op, precedence);
        this.left = left;
        this.right = right;
      }
    }

    public static abstract class Unary extends Expression {
      public final Expression expr;
      Unary(Token op, int precedence, Expression expr) {
        super(op, precedence);
        this.expr = expr;
      }
    }

    // Unary operators (<op> expr)
    public static class Ref extends Unary {
      public Ref(Token op, Expression expr) {
        super(op, 7, expr);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Positive extends Unary {
      public Positive(Token op, Expression expr) {
        super(op, 7, expr);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public static class Negative extends Unary {
      public Negative(Token op, Expression expr) {
        super(op, 7, expr);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public static class Multiple extends Unary {
      public Multiple(Token op, Expression expr) {
        super(op, 7, expr);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    // Binary operators (expr <op> expr)
    public static class Or extends Binary {
      public Or(Token tok, Expression left, Expression right) {
        super(tok, 0, left, right);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public static class And extends Binary {
      public And(Token tok, Expression left, Expression right) {
        super(tok, 1, left, right);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public static class Equal extends Binary {
      public Equal(Token tok, Expression left, Expression right) {
        super(tok, 2, left, right);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public static class Relation extends Binary {
      public Relation(Token tok, Expression left, Expression right) {
        super(tok, 3, left, right);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public static class Add extends Binary {
      public Add(Token tok, Expression left, Expression right) {
        super(tok, 4, left, right);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public static class Mul extends Binary {
      public Mul(Token tok, Expression left, Expression right) {
        super(tok, 5, left, right);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Char extends Expression {
      public final char ch;
      public Char(Token tok, String ch) {
        super(tok, 8);
        this.ch = parseChar(ch.substring(1, ch.length() - 1));
      }

      private char parseChar(String token) {
        char c;
        if (token.charAt(0) == '\\') {
          c = token.charAt(1);
          if (c == 'x') {
            // Hex char constant
            return (char) Integer.parseInt(token.substring(2), 16);
          } else if (0 <= Character.valueOf(c) && Character.valueOf(c) <= 7) {
            // Oct char constant
            return (char) Integer.parseInt(token.substring(1), 8);
          } else {
            // Escaped char constant
            switch(token.charAt(1)) {
              case 'a': return (char) 0x07;
              case 'b': return (char) 0x08;
              case 't': return (char) 0x09;
              case 'n': return (char) 0x0A;
              case 'v': return (char) 0x0B;
              case 'f': return (char) 0x0C;
              case 'r': return (char) 0x0D;
              default : return '?';
            }
          }
        } else {
          // Not escape sequence
          return token.charAt(0);
        }
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class ID extends Expression {
      public Token id;
      public ID(Token id) {
        super(id, 8);
        this.id = id;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Array extends Expression {
      public Token id;
      public Expression index;
      public Array(Token id, Expression index) {
        super(id, 8);
        this.id = id;
        this.index = index;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Int extends Expression {
      public final int value;
      public Int(Token tok, String value) {
        super(tok, 8);
        if (value.charAt(0) == '0' && value.length() > 1) {
          if (value.charAt(1) == 'x')
            this.value = Integer.parseInt(value.substring(2), 16);
          else
            this.value = Integer.parseInt(value.substring(1), 8);
        } else {
          this.value = Integer.parseInt(value);
        }
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Double extends Expression {
      public final double value;
      public Double(Token tok, double value) {
        super(tok, 8);
        this.value = value;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
  }

  public static abstract class Statement extends Node {
    public Statement(Token id) {
      super(id);
    }

    public interface Visitor<T> { 
      T visit(CompoundStatement cStmt); 
      T visit(VariableDecls decls); 
      T visit(AssignStatement aStmt); 
      T visit(ForStatement fStmt);
      T visit(DoWhileStatement dwStmt);
      T visit(BreakStatement bStmt);
      T visit(ReturnStatement rStmt);
      T visit(ContinueStatement conStmt);
      T visit(CallStatement callStmt);
      T visit(IfStatement ifstmt);
      T visit(WhileStatement whstmt);
      // TODO: Add more statements here
    }
      public static class ContinueStatement extends Statement {
      public Expression expr;
      public ContinueStatement(Token id)
      {
        super(id);
      }
      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }

     public static class IfStatement extends Statement{
      public Expression cond;
      public Statement body;
      public Statement elseStmt;
      public IfStatement(Token id, Expression cond, Statement body, Statement elseStmt){
        super(id);
        this.cond=cond;
        this.body=body;
        this.elseStmt=elseStmt;
      }
       public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public static class WhileStatement extends Statement{
      public Expression cond;
      public Statement body;
      public WhileStatement(Token id, Expression cond, Statement body){
        super(id);
        this.cond=cond;
        this.body=body;
      }
       public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public static class CallStatement extends Statement {
      public Expression expr;
      public CallStatement(Token id, Expression expr){
        super(id);
        this.expr=expr;
      }
      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }

    public abstract <T> T accept(Visitor<T> visitor);

    public static class VariableDecls extends Statement {
      public List<Value.Variable> vars;
      public VariableDecls(Token id, List<Value.Variable> vars) {
        super(id);
        this.vars = vars;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class AssignStatement extends Statement {
      public Token varName;
      public Expression expression;
      public Expression index;
      public AssignStatement(Token id, Token varName, Expression expression) {
        super(id);
        this.varName = varName;
        this.expression = expression;
        this.index = null;
      }
      public AssignStatement(Token id, Token varName, Expression index, Expression expression) {
        super(id);
        this.varName = varName;
        this.index = index;
        this.expression = expression;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    
    public static class CompoundStatement extends Statement {
      public List<Statement> stmtList;
      public CompoundStatement(Token id, List<Statement> stmtList) {
        super(id);
        this.stmtList = stmtList;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class ForStatement extends Statement {
      public Statement.AssignStatement init;
      public Expression cond;
      public Statement.AssignStatement update;
      public Statement body;
      public ForStatement(
          Token id, 
          Statement.AssignStatement init, 
          Expression cond, 
          Statement.AssignStatement update, 
          Statement body
        ) 
      {
        super(id);
        this.init = init;
        this.cond = cond;
        this.update = update;
        this.body = body;
      }

      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }

    public static class DoWhileStatement extends Statement {
      public Expression cond;
      public Statement body;
      public DoWhileStatement(Token id, Expression cond, Statement body) {
        super(id);
        this.cond = cond;
        this.body = body;
      }

      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }

    public static class ReturnStatement extends Statement {
      public Expression retVal;
      public ReturnStatement(Token id, Expression retVal)
      {
        super(id);
        this.retVal = retVal;
      }

      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }

   public static class BreakStatement extends Statement {
      public BreakStatement(Token id)
      {
        super(id);
      }

      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }
  }

  public static abstract class FunctionOrVarList extends Node {
    public FunctionOrVarList(Token id) {
      super(id);
    }

    public interface Visitor<T> { 
      T visit(Function func); 
      T visit(VariableList varList); 
    }

    public abstract <T> T accept(Visitor<T> visitor);

    public static class Function extends FunctionOrVarList {
      public Token id;
      public Token varType;
      public List<Value.Argument> argList;
      public Statement.CompoundStatement cStmt;
      public Function(
          Token id, 
          Token varType, 
          List<Value.Argument> argList, 
          Statement.CompoundStatement cStmt
        )
      {
        super(id);
        this.id = id;
        this.varType = varType;
        this.argList = argList;
        this.cStmt = cStmt;
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class VariableList extends FunctionOrVarList {
      public List<Value.Variable> vars;
      public boolean global;

      public VariableList(List<Value.Variable> vars, boolean global) {
        super(ID("VarList"));
        this.vars = vars;
        this.global = global;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
  }

  public static abstract class Value extends Node {
    public Value(Token id) {
      super(id);
    }

    public interface Visitor<T> { 
      T visit(Variable value);
      T visit(Argument argument);
    }

    public abstract <T> T accept(Visitor<T> visitor);
    public static class Variable extends Value {
      public Token type;
      public Token id;
      public Variable(Token id, Token type) {
        super(id);
        this.type = type;
        this.id = id;
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Argument extends Value {
      public Token type;
      public Token id;
      public Argument(Token id, Token type) {
        super(id);
        this.type = type;
        this.id = id;
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
  }

  public static abstract class Unit extends Node {
    // progName Would be the file/class name, but just hardcode it in our case
    public final String progName; 
    public String extName;
    //public Scope scope;
    public int checkDepth = -1;

    public Unit(Token id) {
      super(id);
      this.progName = id.image;
    }

    public interface Visitor<T> {
      T visit(Program program);
    }

    public abstract <T> T accept(Visitor<T> visitor);
    public static class Program extends Unit {
      public List<FunctionOrVarList> fovList;
      public Program(Token id, List<FunctionOrVarList> fovList) {
        super(id);
        this.fovList = fovList;
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
  }
}
