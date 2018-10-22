package simplec;

import simplec.parse.Token;
import static simplec.AST.*;

public abstract class Error {
  static enum Level {
    WARN("warning: "), // warnings
    ERROR("error: "); // errors
    final String label;
    private int count = 0;
    Level(String l) {
      label = l;
    }
  };

  static StringBuilder spare = null;

  // Statements
  public static void MixedDeclarations(Token t) {
    Err(t, "SimpleC forbids mixed declarations and code");
  }

  public static void BreakNotInLoop(Token t) {
    Err(t, "break statement not within loop");
  }

  public static void ContinueNotInLoop(Token t) {
    Err(t, "continue statement not within loop");
  }

  public static void ReturnValueInVoidFunc(Token t) {
    Warn(t, "'return' with a value, in function returning void");
  }

  public static void IncompatibleReturnType(Token t, CType got, CType expected) {
    Err(t, "incompatible types when returning type " + 
        "'" + got + "' but '" + expected + "' was expected");
  }

  public static void DuplicateVariable(Token t) {
    Err(t, "redeclaration of '" + t.image + "'");
  }

  // AssignStatement
  public static void IncompatibleAssignType(Token t, CType to, CType from) {
    Err(t, "incompatible types when assigning to type " + 
        "'" + to + "' from type '" + from + "'");
  }

  public static void UndeclaredAssign(Token t, Value v) {
    Err(t, "'" + v.id + "' undeclared");
  }

  public static void NonIntSubscript(Token t) {
    Err(t, "array subscript is not an integer");
  }

  public static void AssignPointerToInt(Token t) {
    Err(t, "assignment makes integer from pointer without a cast");
  }
  
  // Expressions
  public static void UndeclaredVariable(Token t) {
    Err(t, "id '" + t.image + "' is undeclared");
  }

  public static void DivideByZero(Token t) {
    Warn(t, "division by zero");
  }

  public static void WrongTypeUnary(Token t, String op) {
    Err(t, "wrong type argument to unary '" + op + "'");
  }

  public static void WrongTypeBinary(Token t, String op, CType left, CType right) {
    Err(t, "invalid operands to binary " + op + " (have '" + left + "' and '" + right + "')");
  }

  // Values/Functions
  public static void IncompatibleArgs(Token t, int argNum, Value.Function func) {
    Err(t, "incompatible type for argument " + argNum + " of '" + func.id + "'");
  }

  public static void TooFewArguments(Token t, Value.Function func) {
    Err(t, "too few arguments for function '" + func.id + "'");
  }

  public static void TooManyArguments(Token t, Value.Function func) {
    Err(t, "too many arguments for function '" + func.id + "'");
  }

  public static void Err(Token t, String msg) {
    StringBuilder wr = Header(Level.ERROR);
    out(wr, msg);
    Trailer(t, wr);
  }

  public static void Warn(Token t, String msg) {
    StringBuilder wr = Header(Level.WARN);
    out(wr, msg);
    Trailer(t, wr);
  }

  public static void Int(Token t, int n, String msg) {
    StringBuilder wr = Header(Level.ERROR);
    out(wr, msg);
    out(wr, " (");
    out(wr, String.valueOf(n));
    out(wr, ")");
    Trailer(t, wr);
  }

  public static void ID(Token id, String msg) {
    StringBuilder wr = Header(Level.ERROR);
    out(wr, msg);
    out(wr, " (");
    out(wr, id.image);
    out(wr, ")");
    Trailer(id, wr);
  }

  public static void Txt(Token t, String id, String msg) {
    StringBuilder wr = Header(Level.ERROR);
    out(wr, msg);
    out(wr, ": ");
    out(wr, id);
    Trailer(t, wr);
  }

  public static void WarnID(Token t, String msg) {
    StringBuilder wr = Header(Level.WARN);
    out(wr, msg);
    out(wr, " (");
    out(wr, t.image);
    out(wr, ")");
    Trailer(t, wr);
  }

  private static StringBuilder Header(Level level) {
    StringBuilder wr;
    if (spare != null) {
      wr = spare;
      spare = null;
    } else {
      wr = new StringBuilder();
    }
    level.count++;
    out(wr, level.label);
    return wr;
  }

  private static void Trailer(Token t, StringBuilder wr) {
    System.err.print(" line ");
    System.err.print(t.beginLine);
    System.err.print(", column ");
    System.err.print(t.beginColumn);
    System.err.print(": ");
    System.err.println(wr);
    wr.setLength(0);
    spare = wr;
  }

  private static void out(StringBuilder wr, String t) {
    if (t != null)
      wr.append(t);
  }

  public static int nErrors() {
    return Level.ERROR.count;
  }

  public static int nWarnings() {
    return Level.WARN.count;
  }
}
