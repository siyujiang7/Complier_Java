/* SimpleC.java */
/* Generated By:JavaCC: Do not edit this line. SimpleC.java */
/** Simple brace matcher. */
public class SimpleC implements SimpleCConstants {

  /** Main entry point. */
  public static void main(String args[]) throws ParseException {
    SimpleC parser = new SimpleC(System.in);
    //parser.expr();
    parser.Input();
  }

/**Root production. */
  static final public void Input() throws ParseException {int count;
    count = anytoken();
    jj_consume_token(0);
System.out.println("The number of tokens is " + count);
  }

  static final public int anytoken() throws ParseException {Token t;
  int count=0;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LPARENT:{
        t = jj_consume_token(LPARENT);
System.out.println("(LPARENT, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case RPARENT:{
        t = jj_consume_token(RPARENT);
System.out.println("(RPARENT, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case VOID:{
        t = jj_consume_token(VOID);
System.out.println( "(VOID, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case ID:{
        t = jj_consume_token(ID);
System.out.println( "(ID, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case LCURLY:{
        t = jj_consume_token(LCURLY);
System.out.println( "(LCURLY, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case RCURLY:{
        t = jj_consume_token(RCURLY);
System.out.println( "(RCURLY, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case SEMICOLON:{
        t = jj_consume_token(SEMICOLON);
System.out.println( "(SEMICOLON, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case STRING_CONST:{
        t = jj_consume_token(STRING_CONST);
System.out.println( "(STRING_CONST," +t.image+")" ); count++;
        break;
        }
      case CHARSS:{
        t = jj_consume_token(CHARSS);
System.out.println( "(CHARSS, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case CHARS:{
        t = jj_consume_token(CHARS);
System.out.println( "(CHARS, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case CHAR:{
        t = jj_consume_token(CHAR);
System.out.println( "(CHAR, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case LONG:{
        t = jj_consume_token(LONG);
System.out.println( "(LONG, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case LONGS:{
        t = jj_consume_token(LONGS);
System.out.println( "(LONGS, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case INT:{
        t = jj_consume_token(INT);
System.out.println( "(INT, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case ELSE:{
        t = jj_consume_token(ELSE);
System.out.println( "(ELSE, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case IF:{
        t = jj_consume_token(IF);
System.out.println( "(IF, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case WHILE:{
        t = jj_consume_token(WHILE);
System.out.println( "(WHILE, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case DO:{
        t = jj_consume_token(DO);
System.out.println( "(DO, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case FOR:{
        t = jj_consume_token(FOR);
System.out.println( "(FOR, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case CONTINUE:{
        t = jj_consume_token(CONTINUE);
System.out.println( "(CONTINUE, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case BREAK:{
        t = jj_consume_token(BREAK);
System.out.println( "(BREAK, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case RETURN:{
        t = jj_consume_token(RETURN);
System.out.println( "(RETURN, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case DOUBLES:{
        t = jj_consume_token(DOUBLES);
System.out.println( "(DOUBLES, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case DOUBLE:{
        t = jj_consume_token(DOUBLE);
System.out.println( "(DOUBLE, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case PRINTING_CHAR:{
        t = jj_consume_token(PRINTING_CHAR);
System.out.println( "(PRINTING_CHAR, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case OTHER_CHAR:{
        t = jj_consume_token(OTHER_CHAR);
System.out.println( "(OTHER_CHAR, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case LBRAC:{
        t = jj_consume_token(LBRAC);
System.out.println( "(LBRAC, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case RBRAC:{
        t = jj_consume_token(RBRAC);
System.out.println( "(RBRAC, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case INTEGER_CONST:{
        t = jj_consume_token(INTEGER_CONST);
System.out.println( "(INTEGER_CONST, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case CHAR_CONST:{
        t = jj_consume_token(CHAR_CONST);
System.out.println( "(CHAR_CONST, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case DIGIT:{
        t = jj_consume_token(DIGIT);
System.out.println( "(DIGIT, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case OCTAL:{
        t = jj_consume_token(OCTAL);
System.out.println( "(OCTAL, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case HEX_DIGIT:{
        t = jj_consume_token(HEX_DIGIT);
System.out.println( "(HEX_DIGIT, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case LETTER:{
        t = jj_consume_token(LETTER);
System.out.println( "(LETTER, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case COMMA:{
        t = jj_consume_token(COMMA);
System.out.println( "(COMMA, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case EQUAL:{
        t = jj_consume_token(EQUAL);
System.out.println( "(EQUAL, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case AND:{
        t = jj_consume_token(AND);
System.out.println( "(AND, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case OR:{
        t = jj_consume_token(OR);
System.out.println( "(OR, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case ANDAND:{
        t = jj_consume_token(ANDAND);
System.out.println( "(ANDAND, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case EQUALEQUAL:{
        t = jj_consume_token(EQUALEQUAL);
System.out.println( "(EQUALEQUAL, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case NOTEQUAL:{
        t = jj_consume_token(NOTEQUAL);
System.out.println( "(NOTEQUAL, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case LESS:{
        t = jj_consume_token(LESS);
System.out.println( "(LESS, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case GREATER:{
        t = jj_consume_token(GREATER);
System.out.println( "(GREATER, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case LESSEQUAL:{
        t = jj_consume_token(LESSEQUAL);
System.out.println( "(LESSEQUAL, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case GREATEQUAL:{
        t = jj_consume_token(GREATEQUAL);
System.out.println( "(GREATEQUAL, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case PLUS:{
        t = jj_consume_token(PLUS);
System.out.println( "(PLUS, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case MINUS:{
        t = jj_consume_token(MINUS);
System.out.println( "(MINUS, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case MULTIPLE:{
        t = jj_consume_token(MULTIPLE);
System.out.println( "(MULTIPLE, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case DIVIDE:{
        t = jj_consume_token(DIVIDE);
System.out.println( "(DIVIDE, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case MOD:{
        t = jj_consume_token(MOD);
System.out.println( "(MOD, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      case DOUBLE_CONST:{
        t = jj_consume_token(DOUBLE_CONST);
System.out.println( "(DOUBLE, \u005c""+t.image+"\u005c")" ); count++;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LPARENT:
      case RPARENT:
      case VOID:
      case CHARSS:
      case CHARS:
      case CHAR:
      case LONG:
      case LONGS:
      case INT:
      case ELSE:
      case IF:
      case WHILE:
      case DO:
      case FOR:
      case CONTINUE:
      case BREAK:
      case RETURN:
      case DOUBLES:
      case DOUBLE:
      case LCURLY:
      case RCURLY:
      case SEMICOLON:
      case LBRAC:
      case RBRAC:
      case COMMA:
      case EQUALEQUAL:
      case EQUAL:
      case AND:
      case OR:
      case ANDAND:
      case NOTEQUAL:
      case LESS:
      case GREATER:
      case LESSEQUAL:
      case GREATEQUAL:
      case PLUS:
      case MINUS:
      case MULTIPLE:
      case DIVIDE:
      case MOD:
      case ID:
      case CHAR_CONST:
      case STRING_CONST:
      case DOUBLE_CONST:
      case INTEGER_CONST:
      case PRINTING_CHAR:
      case DIGIT:
      case OCTAL:
      case HEX_DIGIT:
      case LETTER:
      case OTHER_CHAR:{
        ;
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
    }
{if ("" != null) return count;}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public SimpleCTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[2];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xfffffe00,0xfffffe00,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0xfffffff,0xfffffff,};
   }

  /** Constructor with InputStream. */
  public SimpleC(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public SimpleC(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new SimpleCTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public SimpleC(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new SimpleCTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public SimpleC(SimpleCTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(SimpleCTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[60];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 2; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 60; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
