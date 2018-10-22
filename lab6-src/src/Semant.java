package simplec;

import java.math.BigInteger;
import java.util.*;

import simplec.parse.*;
import static simplec.AST.*;

public class Semant {

  static void typeCheck(Value v) {
    if (v == null) return;
    v.accept(new Value.Visitor<Void>() {
      public Void visit(Value.Unit v) {
        for (Value fov : v.fovList) {
          typeCheck(fov);
        }
        return null;
      }

      public Void visit(Value.Variable v) {
        typeCheck(v.type);
        return null;
      }

      public Void visit(Value.Argument v) {
        typeCheck(v.type);
        return null;
      }

      public Void visit(Value.Type v) {
        return null;
      }

      public Void visit(Value.VarAccess v) {
        return null;
      }

      public Void visit(Value.Function v) {
        typeCheck(v.varType);
        for (Value arg : v.argList) {
          typeCheck(arg);
        }
        typeCheck(v.cStmt);
        return null;
      }

      public Void visit(Value.VariableList v) {
        for (Value value : v.vars) {
          typeCheck(value);
        }
        return null;
      }
    });
  }

  static void typeCheck(Statement stmt) {
    if (stmt == null) return;
    stmt.accept(new Statement.Visitor<Void>() {
      public Void visit(Statement.CompoundStatement stmt) {
        // SimpleC forbids mixed declarations and code (just like C90)
        // So we need to set a flag once we've seen code, and make sure
        // no variable declarations follow
        boolean seenNonDecl = false;
        boolean thrownError = false;
        for (Statement s : stmt.stmtList) {
          if (!thrownError) {
            if (s instanceof Statement.VariableDecls) {
              if (seenNonDecl) {
                Error.MixedDeclarations(stmt.id);
                thrownError = true;
              }
            } else {
              seenNonDecl = true;
            }
          }
          typeCheck(s);
        }
        return null;
      }

      public Void visit(Statement.VariableDecls decls) {
        for (Value.Variable var : decls.vars) {
          typeCheck(var);
        }
        return null;
      }

      public Void visit(Statement.AssignStatement stmt) {
        CType lhs = getType(stmt.var);
        CType rhs = getType(stmt.expression);
        if (lhs == null) {
          Error.UndeclaredAssign(stmt.id, stmt.var);
          return null;
        }

        if (stmt.index != null) {
          CType indexType = getType(stmt.index);
          if (!indexType.isInteger()) {
            Error.NonIntSubscript(stmt.id);
          }
          lhs = lhs.deref();
        }

        if (!CType.assignable(lhs, rhs)) {
          if (CType.castWithWarning(lhs, rhs)) {
            Error.AssignPointerToInt(stmt.id);
            return null;
          }
          Error.IncompatibleAssignType(stmt.id, lhs, rhs);
          return null;
        }
        
        return null;
      }

      public Void visit(Statement.ForStatement stmt) {
        typeCheck(stmt.init);
        CType condType = getType(stmt.cond);
        typeCheck(stmt.update);
        typeCheck(stmt.body);
        return null;
      }

      public Void visit(Statement.WhileStatement stmt) {
        CType condType = getType(stmt.cond);
        typeCheck(stmt.body);
        return null;
      }

      public Void visit(Statement.DoWhileStatement stmt) {
        CType condType = getType(stmt.cond);
        typeCheck(stmt.body);
        return null;
      }

      public Void visit(Statement.IfStatement stmt) {
        CType condType = getType(stmt.cond);
        typeCheck(stmt.body);
        if (stmt.elseStmt != null)
          typeCheck(stmt.elseStmt);
        return null;
      }

      public Void visit(Statement.ElseStatement stmt) {
        typeCheck(stmt.body);
        return null;
      }

      public Void visit(Statement.CallStatement stmt) {
        CType exprType = getType(stmt.callExpr);
        return null;
      }

      public Void visit(Statement.ContinueStatement stmt) {
        if (!stmt.scope.isInsideLoop())
          Error.ContinueNotInLoop(stmt.id);
        return null;
      }

      public Void visit(Statement.BreakStatement stmt) {
        if (!stmt.scope.isInsideLoop())
          Error.BreakNotInLoop(stmt.id);
        return null;
      }

      public Void visit(Statement.ReturnStatement stmt) {
        if (stmt.retVal == null)
          return null;
        CType type = getType(stmt.retVal);
        Value.Function func = stmt.scope.getFunc();
        if (func != null) {
          CType funcType = getType(func);
          if (funcType == CType.VOID &&
              stmt.retVal != null) {
            Error.ReturnValueInVoidFunc(stmt.id);
          }
          if (!CType.assignable(funcType, type)) {
            if (CType.castWithWarning(funcType, type)) {
              Error.AssignPointerToInt(stmt.id);
            } else {
              Error.IncompatibleReturnType(stmt.id, type, funcType);
            }
          }
        }
        return null;
      }
    });

  }

  public static CType getType(Value var) {
    if (var == null) return null;
    return var.accept(new Value.Visitor<CType>() {
      public CType visit(Value.Unit v) {
        return null;
      }

      public CType visit(Value.Variable v) {
        return getType(v.type);
      }

      public CType visit(Value.Argument v) {
        return getType(v.type);
      }

      public CType visit(Value.Type v) {
        return v.type;
      }

      public CType visit(Value.VarAccess v) { 
        AST.Value val = v.scope.get(v.id);
        return getType(val);
      }

      public CType visit(Value.Function v) {
        return getType(v.varType);
      }

      public CType visit(Value.VariableList v) {
        if (v.vars.size() > 0)
          return getType(v.vars.get(0));
        return null;
      }
    });
     
  }

  public static CType getType(Expression expr) {
    return expr.accept(new Expression.Visitor<CType>() {
      public CType visit(Expression.Or expr) {
        return binaryCheck(expr);
      }

      public CType visit(Expression.And expr) {
        return binaryCheck(expr);
      }

      public CType visit(Expression.Eq expr) {
        return binaryCheck(expr);
      }

      public CType visit(Expression.Rel expr) {
        return binaryCheck(expr);
      }

      public CType visit(Expression.Add expr) {
        return binaryCheck(expr);
      }

      public CType visit(Expression.Mul expr) {
        if (expr.id.image.equals("/")) {
          if (expr.right instanceof Expression.Int) {
            if (((Expression.Int)expr.right).value == 0) {
              Error.DivideByZero(expr.id);
            }
          }
        }

        return binaryCheck(expr);
      }

      public CType visit(Expression.Ref expr) {
        CType inside = getType(expr.expr);
        CType ret = inside.ref();
        if (ret == CType.ERROR) {
          Error.WrongTypeUnary(expr.id, expr.id.image);
          return CType.LONG;
        }
        return ret;
      }

      public CType visit(Expression.Deref expr) {
        CType inside = getType(expr.expr);
        CType ret = inside.deref();
        if (ret == CType.ERROR) {
          Error.WrongTypeUnary(expr.id, expr.id.image);
          return CType.LONG;
        }
        return ret;
      }

      public CType visit(Expression.Negative expr) {
        CType inside = getType(expr.expr);
        if (!inside.canNegate()) {
          Error.WrongTypeUnary(expr.id, expr.id.image);
        }
        return inside;
      }

      public CType visit(Expression.Positive expr) {
        CType inside = getType(expr.expr);
        if (!inside.canNegate()) {
          Error.WrongTypeUnary(expr.id, expr.id.image);
        }
        return inside;
      }

      public CType visit(Expression.Char expr) { return CType.CHAR; }
      public CType visit(Expression.Text expr) { return CType.CHARSTAR; }
      public CType visit(Expression.Int expr)  { return CType.LONG; }
      public CType visit(Expression.Double expr) { return CType.DOUBLE; }

      public CType visit(Expression.ID id) {
        return getType(id.scope.get(id.id));
      }

      public CType visit(Expression.Call expr) {
        // Easiest way of checking printf
        if (expr.id.equals("printf"))
          return CType.VOID;

        List<Expression> args = expr.args;
        Value val = expr.scope.get(expr.id);
        CType type = getType(val);
        if (val == null) {
          // Uninitialized
          return null;
        } else if (val instanceof Value.Function) {
          Value.Function func = (Value.Function)val;
          List<Value.Argument> argList = func.argList;
          if (args.size() < argList.size()) {
            Error.TooFewArguments(func.id, func);
          } else if (args.size() > argList.size()) {
            Error.TooManyArguments(func.id, func);
          } else { // Same length
            for (int i = 0; i < args.size(); i++) {
              CType one = getType(args.get(i));
              CType two = getType(argList.get(i));
              if (!CType.assignable(one, two)) {
                Error.IncompatibleArgs(func.id, i, func);
                return type;
              }
            }
          }

        }
        return type;
      }

      public CType visit(Expression.Array expr) {
        CType idType = getType(expr.scope.get(expr.id));
        if (expr.index != null) {
          CType indexType = getType(expr.index);
          if (!indexType.isInteger()) {
            Error.NonIntSubscript(expr.id);
            return CType.LONG;
          } else {
            idType = idType.deref();
          }
        }
        return idType;
      }
    });
  }

  public static CType binaryCheck(Expression.Binary expr) {
    CType lhs = getType(expr.left);
    CType rhs = getType(expr.right);
    if (rhs == null) {
      Error.UndeclaredVariable(expr.right.id);
      return CType.LONG;
    }
    if (lhs == null) {
      Error.UndeclaredVariable(expr.left.id);
      return CType.LONG;
    }

    if (lhs == rhs) {
      return lhs;
    }

    if (!CType.assignable(lhs, rhs)) {
      if (CType.castWithWarning(lhs, rhs)) {
        Error.AssignPointerToInt(expr.id);
        return CType.LONG;
      }
      Error.WrongTypeBinary(expr.id, expr.id.image, lhs, rhs);
      return CType.LONG;
    }
    return lhs;
  }
}

