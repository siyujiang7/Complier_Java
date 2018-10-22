package simplec;

import java.util.*;

public enum CType {
  VOID,
  CHAR,
  CHARSTAR,
  CHARSTARSTAR,
  LONG,
  LONGSTAR,
  DOUBLE,
  DOUBLESTAR,
  ERROR;

  public static HashMap<String, CType> stringMap =
    new HashMap<>();
  public static EnumMap<CType, String> enumMap =
    new EnumMap<>(CType.class);

  static {
    stringMap.put("void", CType.VOID);
    stringMap.put("char", CType.CHAR);
    stringMap.put("char*", CType.CHARSTAR);
    stringMap.put("char**", CType.CHARSTARSTAR);
    stringMap.put("long", CType.LONG);
    stringMap.put("long*", CType.LONGSTAR);
    stringMap.put("double", CType.DOUBLE);
    stringMap.put("double*", CType.DOUBLESTAR);
    for (Map.Entry<String, CType> entry : stringMap.entrySet()) {
      enumMap.put(entry.getValue(), entry.getKey());
    }
  }

  public static CType fromString(String str) {
    return stringMap.getOrDefault(str, CType.ERROR);
  }

  public boolean isPointer() {
    return this == CType.CHARSTAR ||
           this == CType.CHARSTARSTAR ||
           this == CType.LONGSTAR ||
           this == CType.DOUBLESTAR;
  }

  public boolean isInteger() {
    return this == CType.CHAR ||
           this == CType.LONG;
  }

  public CType ref() {
    switch (this) {
      case CHAR:
        return CHARSTAR;
      case CHARSTAR:
        return CHARSTARSTAR;
      case LONG:
        return LONGSTAR;
      case DOUBLE:
        return DOUBLESTAR;
      default:
        return ERROR;
    }
  }

  public CType deref() {
    switch (this) {
      case CHARSTAR:
        return CHAR;
      case CHARSTARSTAR:
        return CHARSTAR;
      case LONGSTAR:
        return LONG;
      case DOUBLESTAR:
        return DOUBLE;
      default:
        return ERROR;
    }
  }

  public boolean canNegate() {
    if (this == null)
      return false;
    return isInteger() || this == CType.DOUBLE;
  }

  public static boolean castWithWarning(CType a, CType b) {
    if (a == null || b == null)
      return false;
    if (a.isPointer() && b.isInteger())
      return true;
    if (b.isPointer() && a.isInteger())
      return true;
    return false;
  }

  public boolean castWithWarning(CType b) {
    if (b == null)
      return false;
    return castWithWarning(this, b);
  }

  // This code isn't the best
  public static boolean assignable(CType a, CType b) {
    if (a == null || b == null)
      return false;
    if (a.isInteger() && b.isPointer())
      return false;
    if (a == CType.DOUBLE && b.isPointer())
      return false;
    if (b == CType.DOUBLE && a.isPointer())
      return false;
    return true;
  }

  public boolean assignable(CType b) {
    return assignable(this, b);
  }

  @Override
  public String toString() {
    assert(enumMap.containsKey(this));
    return enumMap.get(this);
  }
}
