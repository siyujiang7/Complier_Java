package simplec;

public enum Reg {
  // 64-bit
  RAX, RBX, RCX, RDX, RSI, RDI, RBP, RSP,
  R8, R9, R10, R11, R12, R13, R14, R15,
  // 32-bit
  EAX, EBX, ECX, EDX, ESI, EDI, EBP, ESP,
  
  // 16-bit
  AX, BX, CX, DX, SI, DI, BP, SP,

  // High 8-bit
  AH, BH, CH, DH,

  // Low 8-bit
  AL, BL, CL, DL, SIL, DIL, BPL, SPL

  // Some are missing here, feel free to add any if
  // you find that you need them
  ;

  @Override
  public String toString() {
    return String.format("%%%s", name().toLowerCase());
  }
}
