package simplec;

import static simplec.Reg.*;

public class Assem {
  public static interface ASM {
    public String toString();
  }

  public static class Const implements ASM {
    public String string;

    public Const(int num) {
      this.string = num + "";
    }

    public Const(String string) {
      this.string = string;
    }

    @Override
    public String toString() {
      return "$" + string;
    }
  }

  public static Const CONST(int num) {
    return new Const(num);
  }

  public static Const CONST(Label label) {
    return new Const(label.name);
  }

  public static class Instr implements ASM {
    public String string;

    public Instr(String string) {
      this.string = string;
    }

    @Override
    public String toString() {
      return "\t" + string;
    }
  }

  public static Instr INSTR(String str) {
    return new Instr(str);
  } 
  
  public static Instr INSTR(String str, Object one) {
    return new Instr(str + "\t" + one);
  }
  
  public static Instr INSTR(String str, Object one, Object two) {
    return new Instr(str + "\t" + one + ", " + two);
  } 

  public static Instr INSTR(String str, Object one, Object two, Object three) {
    return new Instr(str + "\t" + 
                     one + ", " + 
                     two + "\t# " + 
                     three);
  } 

  public static class Comment implements ASM {
    private String string;

    public Comment(String string) {
      this.string = string;
    }

    @Override
    public String toString() {
      return "\t# " + string;
    }
  }

  public static Comment COMMENT(String string) {
    return new Comment(string);
  } 

  public static class Label implements ASM {
    public String name;
    public static int count;

    public Label(String name) {
      this.name = name;
    }

    /**
     * Makes a new label with an arbitrary name.
     */
    public Label() {
      this("L" + count++);
    }

    public Label(char ch) {
      this(ch + "" + count++);
    }

    @Override
    public String toString() {
      return name + ":";
    }
  }

  public static Label LABEL(String str) {
    return new Label(str);
  } 

  public static class Directive implements ASM {
    private String name;
    private boolean tabbed = false;

    public Directive(String name) {
      this.name = name;
    }

    public Directive(String one, String two) {
      this.name = one + "\t" + two;
      this.tabbed = true;
    }

    public Directive(String one, String two, String three) {
      this.name = one + "\t" + two + ", " + three;
      this.tabbed = true;
    }

    @Override
    public String toString() {
      if (tabbed)
        return "\t." + name;
      return "." + name;
    }
  }
  
  public static Directive DIRECTIVE(String str) {
    return new Directive(str);
  }

  public static Directive DIRECTIVE(String one, String two) {
    return new Directive(one, two);
  }

  public static Directive DIRECTIVE(String one, String two, String three) {
    return new Directive(one, two, three);
  }

  public static class ASMString implements ASM {
    private String name;

    public ASMString(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "\t.string " + name;
    }
  }
  
  public static ASMString STRING(String str) {
    return new ASMString(str);
  }

  static final Reg[]
  specialRegs = { RSP, RBP },
              // registers to pass outgoing arguments
              regArgs = { RDI, RSI, RDX, RCX, R8, R9 },
              // registers that a callee must preserve for its caller
              calleeSaves = { RBX, R12, R13, R14, R15 },
              // registers that a callee may use without preserving
              callerSaves = { RAX, R10, R11 };

  public static final int nregStk = 5;


  private static Reg[] registers = {
    RAX, RCX, RDX, RSP, RBP, RSI, RDI, R8,
    R9,  R10, R11, RBX, R12, R13, R14, R15,
  };

  public static String OFFSET(Object obj, int pos) {
    return pos + "(" + obj + ")";
  }

  public static String INDIRECT(Object obj) {
    return "(" + obj + ")";
  }

  public Reg[] registers() {
    return registers;
  }

  // Frame pointer
  public Reg FP() {
    return RBP;
  }

  private static Reg[] T(Reg... regs) {
    return regs;
  }

}
