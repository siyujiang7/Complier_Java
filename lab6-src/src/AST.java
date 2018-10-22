package simplec;

import java.util.*;
import simplec.parse.*;
import static simplec.Assem.*;

public abstract class AST {
  public static void main(String... args) {
    try {
      Value.Unit goal = new SimpleC(System.in).goal();
      new Print(goal);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Token ID(String str) {
    return Token.newToken(SimpleCConstants.ID, str);
  }

  interface Visitor<T> extends 
    Statement.Visitor<T>, 
    Expression.Visitor<T>, 
    Value.Visitor<T>{}

  /* Print is a class that we'll use to traverse our AST,
   * printing out the nodes as we do so.
   * Whenever you make a new AST node class, you'll need
   * to make make a new print method, indicating how you
   * want it to print. Make you sure you get nesting the
   * same as in the tests.
   */
  public static class Print implements Visitor<Void> {
    int i = 0;
    public Print(Value.Unit astNode) {
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

    void print(Value value, int i) {
      if (value == null) {
        sayln("????;");
      } else {
        int save = this.i;
        this.i = i;
        value.accept(this);
        this.i = save;
      }
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

    /* Units */

    @Override
    public Void visit(Value.Unit program) {
      for (Value fov : program.fovList)
        print(fov, i);
      return null;
    }

    @Override
    public Void visit(Value.Function func) {
      // Prints signatures Douglas Comer style :)
      // See: Xinu source code
      print(func.varType, i);
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
    public Void visit(Value.VariableList varList) {
      for (Value.Variable var : varList.vars)
        print(var, i);
      return null;
    }

    /* Values */
    @Override
    public Void visit(Value.Variable variable) {
      indent(i);
      print(variable.type, i); sayln(" " + variable.id + ";");
      return null;
    }

    @Override
    public Void visit(Value.Argument argument) {
      indent(i);
      print(argument.type, i);
      say(" ");
      say(argument.id);
      return null;
    }

    @Override
    public Void visit(Value.VarAccess vAccess) {
      say(vAccess.id);
      return null;
    }

    @Override
    public Void visit(Value.Type type) {
      say(type.type.toString());
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
    public Void visit(Expression.Deref expr) {
      say("*"); print(expr.expr, i);
      return null;
    }

    @Override
    public Void visit(Expression.Negative expr) {
      say("-"); print(expr.expr, i);
      return null;
    }

    @Override
    public Void visit(Expression.Positive expr) {
      say("+"); print(expr.expr, i);
      return null;
    }

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
    public Void visit(Expression.Eq expr) {
      visit(expr.left, expr);
      say(" "); say(expr.token); say(" ");
      visit(expr.right, expr);
      return null;
    }

    @Override
    public Void visit(Expression.Rel expr) {
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

    @Override
    public Void visit(Expression.Text expr) {
      say(expr.token);
      return null;
    }

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
      say(expr.token);
      return null;
    }

    @Override
    public Void visit(Expression.Double expr) {
      say(expr.token);
      return null;
    }

    @Override
    public Void visit(Expression.Call expr) {
      say(expr.id);
      say("(");
      int argIndex = 0;
      for (Expression arg : expr.args) {
        print(arg, i);
        if (argIndex++ < expr.args.size() - 1)
          say(", ");
      }
      say(")");
      return null;
    }

    /* Statements */

    @Override
    public Void visit(Statement.AssignStatement aStmt) {
      indent(i);
      print(aStmt.var, i);
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
    public Void visit(Statement.WhileStatement wStmt) {
      indent(i); say("while (");
      print(wStmt.cond, i + 2);
      sayln(")");
      print(wStmt.body, i + 2);
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
    public Void visit(Statement.IfStatement iStmt) {
      indent(i); say("if (");
      print(iStmt.cond, i);
      sayln(")");
      print(iStmt.body, i + 2);
      if (iStmt.elseStmt != null)
        print(iStmt.elseStmt, i);
      return null;
    }

    @Override
    public Void visit(Statement.ElseStatement eStmt) {
      indent(i); sayln("else");
      print(eStmt.body, i + 2);
      return null;
    }

    @Override
    public Void visit(Statement.CallStatement cStmt) {
      indent(i); print(cStmt.callExpr, i);
      sayln(";");
      return null;
    }

    @Override
    public Void visit(Statement.ContinueStatement cStmt) {
      indent(i); sayln("continue;");
      return null;
    }

    @Override
    public Void visit(Statement.BreakStatement bStmt) {
      indent(i); sayln("break;");
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
  }

  public static abstract class Node {
    public Token token, id;
    Node(Token token) {
      this.token = token;
      this.id = token;
    }
  }

  public static abstract class Expression extends Node {
    public Scope scope;
    public int precendence;
    public Expression(Token op, int precendence) {
      super(op);
      this.precendence = precendence;
      this.scope = Scope.topScope;
    }

    public interface Visitor<T> { 
      T visit(Or expr); 
      T visit(And expr); 
      T visit(Eq expr); 
      T visit(Rel expr); 
      T visit(Add expr); 
      T visit(Mul expr); 
      T visit(Ref expr); 
      T visit(Deref expr); 
      T visit(Negative expr); 
      T visit(Positive expr); 
      T visit(Text expr); 
      T visit(Char expr); 
      T visit(Int expr); 
      T visit(Double expr); 
      T visit(ID expr); 
      T visit(Call expr); 
      T visit(Array expr);
    }

    public abstract <T> T accept(Visitor<T> visitor);

    public static abstract class Binary extends Expression {
      public Expression left, right;
      Binary(Token op, int precedence, Expression left, Expression right) {
        super(op, precedence);
        this.left = left;
        this.right = right;
      }
    }

    public static abstract class Unary extends Expression {
      public Expression expr;
      Unary(Token op, int precedence, Expression expr) {
        super(op, precedence);
        this.expr = expr;
      }
    }

    public static class Ref extends Unary {
      public Ref(Token op, Expression expr) {
        super(op, 7, expr);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Deref extends Unary {
      public Deref(Token op, Expression expr) {
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

    public static class Positive extends Unary {
      public Positive(Token op, Expression expr) {
        super(op, 7, expr);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

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

    public static class Eq extends Binary {
      public Eq(Token tok, Expression left, Expression right) {
        super(tok, 2, left, right);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Rel extends Binary {
      public Rel(Token tok, Expression left, Expression right) {
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

    public static class Text extends Expression {
      public String text;
      public Text(Token tok) {
        super(tok, 8);
        this.text = tok.image;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Char extends Expression {
      public char ch;
      public Char(Token tok) {
        super(tok, 8);
        this.ch = parseChar(id.image.substring(1));
      }

      private char parseChar(String token) {
        char c;
        if (token.charAt(0) == '\\') {
          c = token.charAt(1);
          if (c == 'x') {
            // Hex char constant
            return (char) Integer.parseInt(token.substring(2, token.length()), 16);
          } else if (0 <= Character.valueOf(c) && Character.valueOf(c) <= 7) {
            // Oct char constant
            return (char) Integer.parseInt(token.substring(1, token.length()), 8);
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
      public int value;
      
      public Int(Token tok) {
        super(tok, 8);
        String value = tok.image;
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
      public double value;
      public Double(Token tok) {
        super(tok, 8);
        this.value = java.lang.Double.parseDouble(tok.image);
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Call extends Expression {
      public Token id;
      public List<Expression> args;
      public Call(Token id, List<Expression> args) {
        super(id, 8);
        this.id = id;
        this.args = args;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
  }

  public static abstract class Statement extends Node {
    public Scope scope;
    public Scope childScope = null;
    public Label label = null;
    public Statement(Token id) {
      super(id);
      this.scope = Scope.topScope;
    }

    public interface Visitor<T> { 
      T visit(CompoundStatement cStmt); 
      T visit(VariableDecls decls); 
      T visit(AssignStatement aStmt); 
      T visit(ForStatement fStmt);
      T visit(WhileStatement wStmt);
      T visit(DoWhileStatement dwStmt);
      T visit(IfStatement iStmt);
      T visit(ElseStatement eStmt);
      T visit(CallStatement cStmt);
      T visit(ContinueStatement cStmt);
      T visit(BreakStatement bStmt);
      T visit(ReturnStatement rStmt);
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
      public Value.VarAccess var;
      public Expression expression;
      public Expression index = null;
      public Token id;
      public AssignStatement(
        Token id, 
        Value.VarAccess varName, 
        Expression expression
      ) {
        super(id);
        this.id = id;
        this.var = varName;
        this.expression = expression;
        this.index = null;
      }

      public AssignStatement(
        Token id, 
        Value.VarAccess varName, 
        Expression index, 
        Expression expression
      ) {
        super(id);
        this.id = id;
        this.var = varName;
        this.expression = expression;
        this.index = index;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    
    public static class CompoundStatement extends Statement {
      public List<Statement> stmtList;
      public Scope scope;
      public Scope childScope;
      public CompoundStatement(Token id, List<Statement> stmtList, Scope childScope) {
        super(id);
        this.stmtList = stmtList;
        this.childScope = childScope;
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
        this.label = new Label();
      }

      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }

    public static class WhileStatement extends Statement {
      public Expression cond;
      public Statement body;
      public WhileStatement(Token id, Expression cond, Statement body) {
        super(id);
        this.cond = cond;
        this.body = body;
        this.label = new Label();
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
        this.label = new Label();
      }

      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }

    public static class IfStatement extends Statement {
      public Expression cond;
      public Statement body;
      public Statement.ElseStatement elseStmt = null;
      public IfStatement(
          Token id, 
          Expression cond, 
          Statement body, 
          Statement.ElseStatement elseStmt
        ) 
      {
        super(id);
        this.cond = cond;
        this.body = body;
        this.elseStmt = elseStmt;
        this.label = new Label();
      }

      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }

    public static class ElseStatement extends Statement {
      public Statement body;
      public ElseStatement(Token id, Statement body) 
      {
        super(id);
        this.body = body;
      }

      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }

    public static class CallStatement extends Statement {
      public Expression.Call callExpr;
      public CallStatement(Token id, Expression.Call callExpr) 
      {
        super(id);
        this.callExpr = callExpr;
      }

      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }

    public static class ContinueStatement extends Statement {
      public ContinueStatement(Token id)
      {
        super(id);
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

    public static class ReturnStatement extends Statement {
      public Expression retVal;
      public ReturnStatement(Token id, Expression retVal)
      {
        super(id);
        this.retVal = retVal;
      }

      public <T> T accept(Visitor<T> visitor) {return visitor.visit(this);}
    }
  }

  public static abstract class Value extends Node {
    public Scope scope;
    public Value(Token id) {
      super(id);
      this.scope = Scope.topScope;
    }

    public interface Visitor<T> { 
      T visit(Unit unit); 
      T visit(Variable value);
      T visit(Argument argument);
      T visit(Type type);
      T visit(VarAccess vAccess);
      T visit(Function func); 
      T visit(VariableList varList); 
    }

    public abstract <T> T accept(Visitor<T> visitor);
    public static class Variable extends Value {
      public Value.Type type;
      public Token id;
      public Variable(Token id, Value.Type type) {
        super(id);
        this.type = type;
        this.id = id;
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Argument extends Value {
      public Value.Type type;
      public Token id;
      public Argument(Token id, Value.Type type) {
        super(id);
        this.type = type;
        this.id = id;
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class VarAccess extends Value {
      public Token id;
      public VarAccess(Token id) {
        super(id);
        this.id = id;
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class Type extends Value {
      public Token id;
      public CType type;
      public Type(Token id) {
        super(id);
        this.type = CType.fromString(id.image);
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

   public static class Function extends Value {
      public Token id;
      public Value.Type varType;
      public List<Value.Argument> argList;
      public Statement.CompoundStatement cStmt;
      // Maps an id to a location in the stack
      // (only if this scope is a function)
      public Map<String, Integer> stackMap = null;
      public Function(
          Token id, 
          Value.Type varType, 
          List<Value.Argument> argList, 
          Statement.CompoundStatement cStmt
        )
      {
        super(id);
        this.id = id;
        this.varType = varType;
        this.argList = argList;
        this.cStmt = cStmt;
        cStmt.childScope.type = this;
        this.stackMap = new LinkedHashMap<>();
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public static class VariableList extends Value {
      public List<Value.Variable> vars;
      public boolean global;

      public VariableList(List<Value.Variable> vars, boolean global) {
        super(ID("VarList"));
        this.vars = vars;
        this.global = global;
      }

      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
    public static class Unit extends Value {
      public List<Value> fovList;
      // progName Would be the file/class name, but just hardcode it in our case
      public String progName;
      public Unit(Token id, List<Value> fovList) {
        super(id);
        this.fovList = fovList;
        this.progName = id.image;
      }
      public <T> T accept(Visitor<T> visitor) { return visitor.visit(this); }
    }
  }
}
