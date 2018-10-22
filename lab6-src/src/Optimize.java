package simplec;

import java.math.BigInteger;
import java.util.*;

import simplec.parse.*;
import static simplec.AST.*;

public class Optimize {
  public Optimize() {
  }

  static Value optimize(Value v) {
    if (v == null) return null;
    return v.accept(new Value.Visitor<Value>() {
      public Value visit(Value.Unit v) {
        List<Value> fovList = new ArrayList<Value>();
        for (Value fov : v.fovList) {
          fovList.add(optimize(fov));
        }
        v.fovList = fovList;
        return v;
      }

      public Value visit(Value.Variable v) {
        return v;
      }

      public Value visit(Value.Argument v) {
        return v;
      }

      public Value visit(Value.Type v) {
        return v;
      }

      public Value visit(Value.VarAccess v) {
        return v;
      }

      public Value visit(Value.Function v) {
        List<Value.Argument> argList = new ArrayList<Value.Argument>();
        for (Value.Argument arg : v.argList) {
          argList.add((Value.Argument)optimize(arg));
        }
        v.argList = argList;
        v.cStmt = (Statement.CompoundStatement)optimize(v.cStmt);
        return v;
      }

      public Value visit(Value.VariableList v) {
        List<Value.Variable> vars = new ArrayList<Value.Variable>();
        for (Value.Variable value : v.vars) {
          vars.add((Value.Variable)optimize(value));
        }
        v.vars = vars;
        return v;
      }
    });
  }

  static Statement optimize(Statement stmt) {
    if (stmt == null) return null;
    return stmt.accept(new Statement.Visitor<Statement>() {
      public Statement visit(Statement.CompoundStatement stmt) {
        List<Statement> stmtList = new ArrayList<Statement>();
        for (Statement s : stmt.stmtList) {
          stmtList.add(optimize(s));
        }
        stmt.stmtList = stmtList;
        return stmt;
      }

      public Statement visit(Statement.VariableDecls decls) {
        List<Value.Variable> vars = new ArrayList<Value.Variable>();
        for (Value.Variable var : decls.vars) {
          vars.add((Value.Variable)optimize(var));
        }
        decls.vars = vars;
        return decls;
      }

      public Statement visit(Statement.AssignStatement stmt) {
        if (stmt.index != null) {
          stmt.index = optimize(stmt.index);
        }

        stmt.expression = optimize(stmt.expression);
        return stmt;
      }

      public Statement visit(Statement.ForStatement stmt) {
        stmt.init = (Statement.AssignStatement)optimize(stmt.init);
        stmt.update = (Statement.AssignStatement)optimize(stmt.update);
        stmt.body = optimize(stmt.body);
        return stmt;
      }

      public Statement visit(Statement.WhileStatement stmt) {
        stmt.cond = optimize(stmt.cond);
        stmt.body = optimize(stmt.body);
        return stmt;
      }

      public Statement visit(Statement.DoWhileStatement stmt) {
        stmt.cond = optimize(stmt.cond);
        stmt.body = optimize(stmt.body);
        return stmt;
      }

      public Statement visit(Statement.IfStatement stmt) {
        stmt.body = optimize(stmt.body);
        stmt.cond = optimize(stmt.cond);
        if (stmt.elseStmt != null)
          stmt.elseStmt = (Statement.ElseStatement)optimize(stmt.elseStmt);
        return stmt;
      }

      public Statement visit(Statement.ElseStatement stmt) {
        stmt.body = optimize(stmt.body);
        return stmt;
      }

      public Statement visit(Statement.CallStatement stmt) {
        stmt.callExpr = (Expression.Call)optimize(stmt.callExpr);
        return stmt;
      }

      public Statement visit(Statement.ContinueStatement stmt) {
        return stmt;
      }

      public Statement visit(Statement.BreakStatement stmt) {
        return stmt;
      }

      public Statement visit(Statement.ReturnStatement stmt) {
        if (stmt.retVal != null)
          stmt.retVal = optimize(stmt.retVal);
        return stmt;
      }
    });

  }

  public static Expression optimize(Expression expr) {
    return expr.accept(new Expression.Visitor<Expression>() {
      public Expression visit(Expression.Or expr) {
        optimize(expr.left);
        optimize(expr.right);
        return expr;
      }

      public Expression visit(Expression.And expr) {
        optimize(expr.left);
        optimize(expr.right);
        return expr;
      }

      public Expression visit(Expression.Eq expr) {
        optimize(expr.left);
        optimize(expr.right);
        return expr;
      }

      public Expression visit(Expression.Rel expr) {
        optimize(expr.left);
        optimize(expr.right);
        return expr;
      }

      public Expression visit(Expression.Add expr) {
        expr.left = optimize(expr.left);
        expr.right = optimize(expr.right);
        // TODO - Constant Folding
        if (expr.id.image.equals("+")) {
        if (expr.left instanceof Expression.Int && expr.right instanceof Expression.Int){
        int newV = ((Expression.Int)expr.left).value + ((Expression.Int)expr.right).value;
        return new Expression.Int(ID(newV + ""));
        }
    	}
        if (expr.id.image.equals("-")) {
        if (expr.left instanceof Expression.Int && expr.right instanceof Expression.Int){
        int newV = ((Expression.Int)expr.left).value - ((Expression.Int)expr.right).value;
        return new Expression.Int(ID(newV + ""));
        }
    	}
        return expr;
      }

      public Expression visit(Expression.Mul expr) {
        expr.left = optimize(expr.left);
        expr.right = optimize(expr.right);
        // TODO - Constant Folding
        if (expr.id.image.equals("*")) {
        if (expr.left instanceof Expression.Int && expr.right instanceof Expression.Int){
        int newV = ((Expression.Int)expr.left).value * ((Expression.Int)expr.right).value;
        return new Expression.Int(ID(newV + ""));
        }
    	}
        if (expr.id.image.equals("/")) {
        if (expr.left instanceof Expression.Int && expr.right instanceof Expression.Int){
        int newV = ((Expression.Int)expr.left).value / ((Expression.Int)expr.right).value;
        return new Expression.Int(ID(newV + ""));
        }
    	}
        return expr;
      }

      public Expression visit(Expression.Ref expr) {
        return expr;
      }

      public Expression visit(Expression.Deref expr) {
        return expr;
      }

      public Expression visit(Expression.Negative expr) {
        expr.expr = optimize(expr.expr);
        if (expr.expr instanceof Expression.Int) {
          int newV = ((Expression.Int)expr.expr).value * -1;
          return new Expression.Int(ID(newV + ""));
        }
        return expr;
      }

      public Expression visit(Expression.Positive expr) {
        return optimize(expr.expr);
      }

      public Expression visit(Expression.Char expr) { return expr; }
      public Expression visit(Expression.Text expr) { return expr; }
      public Expression visit(Expression.Int expr)  { return expr; }
      public Expression visit(Expression.Double expr) { return expr; }

      public Expression visit(Expression.ID id) {
        return id;
      }

      public Expression visit(Expression.Call expr) {
        //System.out.println(expr.id.image);
        List<Expression> args = new ArrayList<Expression>();
        for (Expression arg : expr.args) {
          args.add(optimize(arg));
        }
        expr.args = args;
        if (expr.id.image.equals("sin")) {
            int newV = ((Expression.Int)expr.args.get(0)).value;
            return new Expression.Int(ID(newV + ""));
        }
        return expr;
      }

      public Expression visit(Expression.Array expr) {
        return expr;
      }
    });
  }
}

