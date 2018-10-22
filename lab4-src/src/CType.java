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

  @Override
  public String toString() {
    assert(enumMap.containsKey(this));
    return enumMap.get(this);
  }
}
