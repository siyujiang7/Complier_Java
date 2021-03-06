/* SimpleC.java */
/* Generated By:JavaCC: Do not edit this line. SimpleC.java */
/** Simple brace matcher. */
public class SimpleC implements SimpleCConstants {

  /** Main entry point. */
  public static void main(String args[]) throws ParseException {
    SimpleC parser = new SimpleC(System.in);
  try{
    parser.Input();
  }catch(ParseException e){
    int line = e.currentToken.beginLine+1;
    //line += 1;
    System.out.println(line+": Syntax Error");
    System.exit(1);
  }
    System.out.println("Program compiled Successfully!");
    //parser.Input();
  }

/**Root production. */
  static final public void Input() throws ParseException {
    goal();
    jj_consume_token(0);
  }

  static final public void goal() throws ParseException {
    program();
  }

  static final public void program() throws ParseException {
    function_or_val_list();
  }

  static final public void function_or_val_list() throws ParseException {
    function_or_val_list_P();
  }

  static final public void function_or_val_list_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VOID:
    case CHARSS:
    case CHARS:
    case CHAR:
    case LONG:
    case LONGS:
    case DOUBLES:
    case DOUBLE:{
      var_type();
      function_or_val_list_P_P();
      break;
      }
    default:
      jj_la1[0] = jj_gen;

    }
  }

  static final public void function_or_val_list_P_P() throws ParseException {
    jj_consume_token(ID);
    function_or_val_list_P_P_P();
  }

  static final public void function_or_val_list_P_P_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPARENT:{
      function();
      function_or_val_list();
      break;
      }
    case VOID:
    case CHARSS:
    case CHARS:
    case CHAR:
    case LONG:
    case LONGS:
    case DOUBLES:
    case DOUBLE:{
      global_var();
      function_or_val_list();
      break;
      }
    default:
      jj_la1[1] = jj_gen;

    }
  }

  static final public void function() throws ParseException {
    jj_consume_token(LPARENT);
    arguments();
    jj_consume_token(RPARENT);
    compound_statement();
  }

  static final public void arguments() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VOID:
    case CHARSS:
    case CHARS:
    case CHAR:
    case LONG:
    case LONGS:
    case DOUBLES:
    case DOUBLE:{
      arg_list();
      break;
      }
    default:
      jj_la1[2] = jj_gen;

    }
  }

  static final public void arg_list() throws ParseException {
    arg();
    arg_list_P();
  }

  static final public void arg_list_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      arg();
      arg_list_P();
      break;
      }
    default:
      jj_la1[3] = jj_gen;

    }
  }

  static final public void arg() throws ParseException {
    var_type();
    jj_consume_token(ID);
  }

  static final public void global_var() throws ParseException {
    var_type();
    global_var_list();
    jj_consume_token(SEMICOLON);
  }

  static final public void global_var_list() throws ParseException {
    global_var_list_P();
  }

  static final public void global_var_list_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      jj_consume_token(ID);
      global_var_list_P();
      break;
      }
    default:
      jj_la1[4] = jj_gen;

    }
  }

  static final public void var_type() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CHAR:{
      jj_consume_token(CHAR);
      break;
      }
    case CHARS:{
      jj_consume_token(CHARS);
      break;
      }
    case CHARSS:{
      jj_consume_token(CHARSS);
      break;
      }
    case DOUBLE:{
      jj_consume_token(DOUBLE);
      break;
      }
    case DOUBLES:{
      jj_consume_token(DOUBLES);
      break;
      }
    case LONG:{
      jj_consume_token(LONG);
      break;
      }
    case LONGS:{
      jj_consume_token(LONGS);
      break;
      }
    case VOID:{
      jj_consume_token(VOID);
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void assignment() throws ParseException {
    assignment_P();
  }

  static final public void assignment_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQUAL:{
      jj_consume_token(EQUAL);
      expression();
      break;
      }
    case LBRAC:{
      jj_consume_token(LBRAC);
      expression();
      jj_consume_token(RBRAC);
      jj_consume_token(EQUAL);
      expression();
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void call() throws ParseException {
    jj_consume_token(LPARENT);
    call_arguments();
    jj_consume_token(RPARENT);
  }

  static final public void call_arg_list() throws ParseException {
    expression();
    call_arg_list_P();
  }

  static final public void call_arg_list_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      expression();
      call_arg_list_P();
      break;
      }
    default:
      jj_la1[7] = jj_gen;

    }
  }

  static final public void call_arguments() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPARENT:
    case AND:
    case PLUS:
    case MINUS:
    case MULTIPLE:
    case ID:
    case CHAR_CONST:
    case STRING_CONST:
    case DOUBLE_CONST:
    case INTEGER_CONST:{
      call_arg_list();
      break;
      }
    default:
      jj_la1[8] = jj_gen;

    }
  }

  static final public void expression() throws ParseException {
    logical_or_expr();
  }

  static final public void logical_or_expr() throws ParseException {
    logical_and_expr();
    logical_or_expr_P();
  }

  static final public void logical_or_expr_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OR:{
      jj_consume_token(OR);
      logical_and_expr();
      logical_or_expr_P();
      break;
      }
    default:
      jj_la1[9] = jj_gen;

    }
  }

  static final public void logical_and_expr() throws ParseException {
    eqaulity_expr();
    logical_and_expr_P();
  }

  static final public void logical_and_expr_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ANDAND:{
      jj_consume_token(ANDAND);
      eqaulity_expr();
      logical_and_expr_P();
      break;
      }
    default:
      jj_la1[10] = jj_gen;

    }
  }

  static final public void eqaulity_expr() throws ParseException {
    relational_expr();
    eqaulity_expr_P();
  }

  static final public void eqaulity_expr_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQUALEQUAL:{
      jj_consume_token(EQUALEQUAL);
      relational_expr();
      eqaulity_expr_P();
      break;
      }
    case NOTEQUAL:{
      jj_consume_token(NOTEQUAL);
      relational_expr();
      eqaulity_expr_P();
      break;
      }
    default:
      jj_la1[11] = jj_gen;

    }
  }

  static final public void relational_expr() throws ParseException {
    additive_expr();
    relational_expr_P();
  }

  static final public void relational_expr_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LESS:{
      jj_consume_token(LESS);
      additive_expr();
      relational_expr_P();
      break;
      }
    case GREATER:{
      jj_consume_token(GREATER);
      additive_expr();
      relational_expr_P();
      break;
      }
    case LESSEQUAL:{
      jj_consume_token(LESSEQUAL);
      additive_expr();
      relational_expr_P();
      break;
      }
    case GREATEQUAL:{
      jj_consume_token(GREATEQUAL);
      additive_expr();
      relational_expr_P();
      break;
      }
    default:
      jj_la1[12] = jj_gen;

    }
  }

  static final public void additive_expr() throws ParseException {
    multiplicative_expr();
    additive_expr_P();
  }

  static final public void additive_expr_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PLUS:{
      jj_consume_token(PLUS);
      multiplicative_expr();
      additive_expr_P();
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
      multiplicative_expr();
      additive_expr_P();
      break;
      }
    default:
      jj_la1[13] = jj_gen;

    }
  }

  static final public void multiplicative_expr() throws ParseException {
    unary_expr();
    multiplicative_expr_P();
  }

  static final public void multiplicative_expr_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MULTIPLE:{
      jj_consume_token(MULTIPLE);
      unary_expr();
      multiplicative_expr_P();
      break;
      }
    case DIVIDE:{
      jj_consume_token(DIVIDE);
      unary_expr();
      multiplicative_expr_P();
      break;
      }
    case MOD:{
      jj_consume_token(MOD);
      unary_expr();
      multiplicative_expr_P();
      break;
      }
    default:
      jj_la1[14] = jj_gen;

    }
  }

  static final public void unary_expr() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPARENT:
    case ID:
    case CHAR_CONST:
    case STRING_CONST:
    case DOUBLE_CONST:
    case INTEGER_CONST:{
      primary_expr();
      break;
      }
    case PLUS:{
      jj_consume_token(PLUS);
      unary_expr();
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
      unary_expr();
      break;
      }
    case AND:{
      jj_consume_token(AND);
      unary_expr();
      break;
      }
    case MULTIPLE:{
      jj_consume_token(MULTIPLE);
      unary_expr();
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void primary_expr() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case STRING_CONST:{
      jj_consume_token(STRING_CONST);
      break;
      }
    case ID:{
      jj_consume_token(ID);
      primary_expr_P();
      break;
      }
    case INTEGER_CONST:{
      jj_consume_token(INTEGER_CONST);
      break;
      }
    case DOUBLE_CONST:{
      jj_consume_token(DOUBLE_CONST);
      break;
      }
    case CHAR_CONST:{
      jj_consume_token(CHAR_CONST);
      break;
      }
    case LPARENT:{
      jj_consume_token(LPARENT);
      expression();
      jj_consume_token(RPARENT);
      break;
      }
    default:
      jj_la1[16] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void primary_expr_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LBRAC:{
      primary_expr_P_P();
      break;
      }
    case LPARENT:{
      call();
      break;
      }
    default:
      jj_la1[17] = jj_gen;

    }
  }

  static final public void primary_expr_P_P() throws ParseException {
    jj_consume_token(LBRAC);
    expression();
    jj_consume_token(RBRAC);
  }

  static final public void compound_statement() throws ParseException {
    jj_consume_token(LCURLY);
    statement_list();
    jj_consume_token(RCURLY);
  }

  static final public void statement_list() throws ParseException {
    statement_list_P();
  }

  static final public void statement_list_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VOID:
    case CHARSS:
    case CHARS:
    case CHAR:
    case LONG:
    case LONGS:
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
    case ID:{
      statement();
      statement_list_P();
      break;
      }
    default:
      jj_la1[18] = jj_gen;

    }
  }

  static final public void local_var() throws ParseException {
    var_type();
    local_var_list();
    jj_consume_token(SEMICOLON);
  }

  static final public void local_var_list() throws ParseException {
    jj_consume_token(ID);
    local_var_list_P();
  }

  static final public void local_var_list_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      jj_consume_token(ID);
      local_var_list_P();
      break;
      }
    default:
      jj_la1[19] = jj_gen;

    }
  }

  static final public void statement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ID:{
      jj_consume_token(ID);
      statement_P();
      break;
      }
    case VOID:
    case CHARSS:
    case CHARS:
    case CHAR:
    case LONG:
    case LONGS:
    case DOUBLES:
    case DOUBLE:{
      local_var();
      break;
      }
    case LCURLY:{
      compound_statement();
      break;
      }
    case IF:{
      jj_consume_token(IF);
      jj_consume_token(LPARENT);
      expression();
      jj_consume_token(RPARENT);
      statement();
      else_optional();
      break;
      }
    case WHILE:{
      jj_consume_token(WHILE);
      jj_consume_token(LPARENT);
      expression();
      jj_consume_token(RPARENT);
      statement();
      break;
      }
    case DO:{
      jj_consume_token(DO);
      statement();
      jj_consume_token(WHILE);
      jj_consume_token(LPARENT);
      expression();
      jj_consume_token(RPARENT);
      jj_consume_token(SEMICOLON);
      break;
      }
    case FOR:{
      jj_consume_token(FOR);
      jj_consume_token(LPARENT);
      jj_consume_token(ID);
      assignment();
      jj_consume_token(SEMICOLON);
      expression();
      jj_consume_token(SEMICOLON);
      jj_consume_token(ID);
      assignment();
      jj_consume_token(RPARENT);
      statement();
      break;
      }
    case CONTINUE:
    case BREAK:
    case RETURN:{
      jump_statement();
      break;
      }
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void statement_P() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPARENT:{
      call();
      jj_consume_token(SEMICOLON);
      break;
      }
    case LBRAC:
    case EQUAL:{
      assignment();
      jj_consume_token(SEMICOLON);
      break;
      }
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void else_optional() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ELSE:{
      jj_consume_token(ELSE);
      statement();
      break;
      }
    default:
      jj_la1[22] = jj_gen;

    }
  }

  static final public void jump_statement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CONTINUE:{
      jj_consume_token(CONTINUE);
      jj_consume_token(SEMICOLON);
      break;
      }
    case BREAK:{
      jj_consume_token(BREAK);
      jj_consume_token(SEMICOLON);
      break;
      }
    case RETURN:{
      jj_consume_token(RETURN);
      return_val_opt();
      jj_consume_token(SEMICOLON);
      break;
      }
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void return_val_opt() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPARENT:
    case AND:
    case PLUS:
    case MINUS:
    case MULTIPLE:
    case ID:
    case CHAR_CONST:
    case STRING_CONST:
    case DOUBLE_CONST:
    case INTEGER_CONST:{
      expression();
      break;
      }
    default:
      jj_la1[24] = jj_gen;

    }
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
  static final private int[] jj_la1 = new int[25];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xc01f800,0xc01fa00,0xc01f800,0x0,0x0,0xc01f800,0x80000000,0x0,0x200,0x0,0x0,0x0,0x0,0x0,0x0,0x200,0x200,0x80000200,0x1ff9f800,0x0,0x1ff9f800,0x80000200,0x40000,0x3800000,0x200,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x2,0x2,0x0,0x8,0x2,0x3e7010,0x20,0x40,0x84,0xf00,0x3000,0x1c000,0x3e7010,0x3e0000,0x0,0x20000,0x2,0x20000,0x8,0x0,0x0,0x3e7010,};
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
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(SimpleCTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 25; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 25; i++) {
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
