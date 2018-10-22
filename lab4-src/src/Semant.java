package simplec;

import java.math.BigInteger;
import java.util.*;

import simplec.parse.*;
import static simplec.AST.*;
public class Semant {
public static CType global_type = null;
  private static void usage() {
    throw new java.lang.Error("Usage: java simplec.Semant <source>.c");
  }

  private Semant() { }

  public static void main(String... args) {
    try {
      //System.err.println("*************start***************");
      Value.Unit goal = new SimpleC(System.in).goal();
      //Uncomment this if you want the AST to print
      //new Print(goal);
      Semant semant = new Semant();
      semant.typeCheck(goal);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void typeCheck(Value v) {
    if (v == null) return;
    v.accept(new Value.Visitor<Void>() {
      public Void visit(Value.Unit v) {
        for (Value fov : v.fovList) {
          //System.err.println(fov.id.image);
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

      public Void visit(Value.ScalarAccess v) {
        return null;
      }

      public Void visit(Value.Function v) {
      	System.err.println("enter function");
        typeCheck(v.varType);
        global_type = v.varType.type;
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

  void typeCheck(Statement stmt) {
    if (stmt == null) return;
    stmt.accept(new Statement.Visitor<Void>() {
      public Void visit(Statement.CompoundStatement stmt) {
        // SimpleC forbids mixed declarations and code (just like C90)
        // So we need to set a flag once we've seen code, and make sure
        // no variable declarations follow
        boolean seenNonDecl = false;
        for (Statement s : stmt.stmtList) {
            //System.err.println(s.id.image);
          if (s instanceof Statement.VariableDecls) {
            if (seenNonDecl) {
              Error.MixedDeclarations(stmt.id);
            }
          } else {
            seenNonDecl = true;
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
        Scope current_scope = stmt.scope;
        //CType lhs = getType(stmt.var);
        String varName = stmt.var.id.image;
        AST.Node node = current_scope.lookup(varName);
        if(stmt.index!= null){
          //System.err.println("index: "+stmt.index.id.image);
          CType indexType = getType(stmt.index);
          //System.err.println("index type: "+indexType.toString());
          if(!indexType.toString().equals("long"))
            Error.NonIntSubscript(stmt.id);
        }
        //System.err.println("node: "+node.id.image);
        CType lhs = getType((Value)node);
        //System.err.println("node type: "+lhs.toString());        
        CType rhs = getType(stmt.expression);
        //System.err.println("rhs type: "+rhs.toString());        

        if (lhs == null) {
          // Note that until you add scope, this might
          // say every variable is undeclared
          Error.UndeclaredAssign(stmt.id, stmt.var);
          return null;
        }
        System.err.println("varType: "+lhs.toString());
        System.err.println("varType: "+rhs.toString());
        if (lhs != rhs) {
          if(rhs != null){
          String r_string = rhs.toString();
          if(r_string.charAt(r_string.length()-1) == '*' && lhs.toString().equals("long"))
          Error.AssignPointerToInt(stmt.id);
      		else if((lhs == CType.DOUBLE && rhs == CType.LONG) 
      			|| (lhs == CType.LONG && rhs == CType.DOUBLE)
      			||(lhs == CType.LONG && rhs == CType.CHAR)
      			|| (lhs == CType.CHAR && rhs == CType.LONG)
      			||(lhs == CType.LONGSTAR && rhs == CType.LONG))
      			return null;
      		else
      	  	Error.IncompatibleAssignType(stmt.id, rhs, lhs);
        }
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
          typeCheck(stmt.body);
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
            Scope scope = stmt.scope.parentScope;
            if (scope.lookup("for") != null
              ||scope.lookup("while") != null
              ||scope.lookup("do") != null) {           }
            else
            Error.ContinueNotInLoop(stmt.id);
            return null;
          }

      public Void visit(Statement.BreakStatement stmt) {
            Scope scope = stmt.scope;
            if (scope.lookup("for") != null
              ||scope.lookup("while") != null
              ||scope.lookup("do") != null) {
              return null;}
            else{
            Error.BreakNotInLoop(stmt.id);
            return null;
            }

      }

      public Void visit(Statement.ReturnStatement stmt) {
      	System.err.println("enter return");
        CType var_type = getType(stmt.retVal);
       	if(var_type != global_type){
       		if (global_type.toString().equals("void"))
       		Error.ReturnValueInVoidFunc(stmt.id); 
       		else if((var_type.toString().equals("long") && global_type.toString().equals("double") )
       				|| (var_type.toString().equals("double") && global_type.toString().equals("long"))
       				 || (var_type.toString().equals("long") && global_type.toString().equals("char") )
       				 || (var_type.toString().equals("char") && global_type.toString().equals("long") ) ){
       				return null;
       		}
       		else if(global_type.toString().equals("long") && var_type.toString().charAt(var_type.toString().length()-1) == '*'){
       			Error.AssignPointerToInt(stmt.id);
       		}
       		else
       		Error.IncompatibleReturnType(stmt.id, var_type, global_type);
       	}
        return null;
      }
    });

  }

  public CType getType(Value var) {
    if (var == null) return null;
    return var.accept(new Value.Visitor<CType>() {
      public CType visit(Value.Unit v) {
        return null;
      }

      public CType visit(Value.Variable v) {
        return getType(v.type);
      }

      public CType visit(Value.Argument v) {
        return v.type.type;
      }

      public CType visit(Value.Type v) {
        return v.type;
      }

      public CType visit(Value.ScalarAccess v) { 
        return null;
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

  public CType getType(Expression expr) {
    return expr.accept(new Expression.Visitor<CType>() {
      public CType visit(Expression.Or expr) {
        CType lhs = getType(expr.left);
        CType rhs = getType(expr.right);

        if (lhs == rhs)
          return lhs;
        return null;
      }

      public CType visit(Expression.And expr) {
        CType lhs = getType(expr.left);
        CType rhs = getType(expr.right);

        if (lhs == rhs)
          return lhs;
        return null;
      }

      public CType visit(Expression.Eq expr) {
        CType lhs = getType(expr.left);
        CType rhs = getType(expr.right);
        if (lhs == rhs)
          return lhs;
        return null;
      }

      public CType visit(Expression.Rel expr) {
        CType lhs = getType(expr.left);
        CType rhs = getType(expr.right);
        System.err.println(lhs.toString() +" "+rhs.toString());
        if (lhs == rhs)
          return lhs;
        else if(lhs != rhs){
     		if((lhs == CType.LONG&&rhs==CType.CHARSTAR) 
     			|| (lhs == CType.CHARSTAR&&rhs==CType.LONG)
     			||(lhs == CType.LONG&&rhs==CType.DOUBLE)
     			|| (lhs == CType.DOUBLE&&rhs==CType.LONG)
     			|| (lhs == CType.CHAR&&rhs==CType.DOUBLE)
     			||(lhs == CType.DOUBLE&&rhs==CType.CHAR))
     		return CType.LONG;	
        else
          Error.WrongTypeBinary(expr.id, expr.id.toString(), lhs, rhs);
  		}
        return null;
      }

      public CType visit(Expression.Add expr) {
        CType lhs = getType(expr.left);
        System.err.println("id: "+expr.left.id.image+expr.id.toString()+expr.right.id.image);
        //System.err.print("type: "+lhs.toString());
        //System.err.print(" id: "+expr.left.id.image+" ");
        CType rhs = getType(expr.right);
        //System.err.print("type: "+rhs.toString());
        //System.err.println(" id: "+expr.right.id.image+ " ");
        if (lhs == null && rhs != null) {
            Error.UndeclaredVariable(expr.left.id);  
            lhs = rhs;         
        }
        if (rhs == null && lhs != null){
        	Error.UndeclaredVariable(expr.right.id);
        	rhs = lhs;
    	}
        if (lhs == rhs)
          return lhs;
        else if (lhs != rhs) {
          String l_type = lhs.toString();
          //System.err.println("left type: "+l_type);
          if(l_type.charAt(l_type.length()-1) == '*'){
            return lhs;
          }
          else
          Error.WrongTypeBinary(expr.id,expr.id.toString(),lhs,rhs);
        }
        return null;
}
      public CType visit(Expression.Mul expr) {
        CType lhs = getType(expr.left);
        System.err.println("id: "+expr.left.id.image+expr.id.toString()+expr.right.id.image);
        //System.err.println("left type: "+lhs.toString());
        CType rhs = getType(expr.right);
        System.err.println(expr.right);
        //System.err.println("right type: "+rhs.toString());
        if(expr.right.id.image.equals("0") && expr.id.toString().equals("/"))
            Error.DivideByZero(expr.right.token);
        if (lhs == null && rhs != null) {
            Error.UndeclaredVariable(expr.left.id); 
            lhs = rhs;          
        }
        if (rhs == null && lhs != null){
        	Error.UndeclaredVariable(expr.right.id);
        	rhs = lhs;
    	}
        if (lhs == rhs){
          return lhs;
        }
        else if (lhs != rhs) {
          String l_type = lhs.toString();
          String r_type  = rhs.toString();
          System.err.println("left type: "+l_type);
          System.err.println("left type: "+r_type);          
          if(l_type.charAt(l_type.length()-1) == '*'){
            return lhs;
          }
          else if(r_type.charAt(r_type.length()-1) == '*'){
            return rhs;
          }
          else if(l_type.equals("long") && r_type.equals("double")){
          	return lhs;
          }
          else if(l_type.equals("double") && r_type.equals("long")){
          	return lhs;
          }
          else
          Error.WrongTypeBinary(expr.id,expr.id.toString(),lhs,rhs);
        }
        return null;
      }

      public CType visit(Expression.Ref expr) {
        //System.err.println(expr.id.image);
        CType inside = getType(expr.expr);
        if(inside.toString().equals("char")){
          return CType.CHARSTAR;
        }else if(inside.toString().equals("char*")){
          return CType.CHARSTARSTAR;
        }else if(inside.toString().equals("long")){
          return CType.LONGSTAR;
        } else if(inside.toString().equals("double")){
          return CType.DOUBLESTAR;
        }else{
        	Error.WrongTypeUnary(expr.id, expr.id.image);
          //return inside;
        }
        return null;
      }

      public CType visit(Expression.Deref expr) {
        CType inside = getType(expr.expr);
        if(inside.toString().equals("char**")){
          return CType.CHARSTAR;
        }else if(inside.toString().equals("char*")){
          return CType.CHAR;
        }else if(inside.toString().equals("long*")){
          return CType.LONG;
        } else if(inside.toString().equals("double*")){
          return CType.DOUBLE;
        }else{
          Error.WrongTypeUnary(expr.id, expr.id.image);
          //return inside;
        }
        return null;
      }

      public CType visit(Expression.Negative expr) {
        CType inside = getType(expr.expr);
        if(inside.toString() == "char*")
          Error.WrongTypeUnary(expr.id, "-");
        return inside;
      }

      public CType visit(Expression.Positive expr) {
        CType inside = getType(expr.expr);
        return inside;
      }

      public CType visit(Expression.Char expr) { return CType.CHAR; }
      public CType visit(Expression.Text expr) { return CType.CHARSTAR; }
      public CType visit(Expression.Int expr)  { return CType.LONG; }
      public CType visit(Expression.Double expr) { return CType.DOUBLE; }

      public CType visit(Expression.ID id) {
        Scope current_scope = id.scope;
        AST.Node var = current_scope.lookup(id.id.image);
        CType id_type = getType((Value)var);
        return id_type;      
      }
      public CType visit(Expression.Call expr) {
        //System.err.println("enter call");
        Scope current_scope = expr.scope;
        AST.Value.Function node = (AST.Value.Function)current_scope.lookup(expr.id.image);
        if (node != null) {
          int s1 = node.argList.size();
          int s2 = expr.args.size();
          if(s1 < s2){
            Error.TooManyArguments(expr.id, node);
            return null;
          }
          if(s1 > s2){
            Error.TooFewArguments(expr.id, node);
             return null;
            }
        
        for (int i = 0;i < expr.args.size() ; i++) {
            String g = expr.args.get(i).id.image;
            System.err.println(g);
            CType temp_type;
            if(g.charAt(0) == '"' && g.charAt(g.length()-1)=='"')
            	temp_type = CType.CHARSTAR;
            else if (g.matches("[-+]?d+")){
            	temp_type= CType.LONG;
            }
            else if(g.matches("[-+]?\\d*\\.?\\d+")){
            	temp_type = CType.DOUBLE;
            }
            else{
            AST.Node temp = current_scope.lookup(g);
            temp_type = getType((Value)temp);
            System.err.println("function name: "+node.id.image);
        	}	
            Value.Argument arg = node.argList.get(i);
            CType function_type = arg.type.type;       
            if (temp_type != function_type && !temp_type.toString().equals("long") && !function_type.toString().equals("char"))
              Error.IncompatibleArgs(expr.id, node.argList.size(),node);
          }
          Value.Function function_dec = (Value.Function)current_scope.lookup(expr.id.image);
          System.err.println("function_type " + function_dec.varType.type.toString());
          return function_dec.varType.type;
        }
          //Error.MixedDeclarations(expr.id);
        return null;
      }

      public CType visit(Expression.Array expr) {
        return null;
      }
    });
  }
}
