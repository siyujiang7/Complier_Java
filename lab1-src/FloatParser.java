
import java.io.InputStream;
import java.io.InputStreamReader;
class FloatParser {
  public static void main(String args[]) throws Exception {
  	if (args.length==0) {
     System.out.println("Usage: java NumberParser value");
     System.exit(1);
   }
   double val = MyParseFloat(args[0]);
   System.out.println("Value="+val);
 }

 enum StateFloat { SSTART, SINTEGER, SDECIMAL, SEND, EEXPRESSION};

 public static double MyParseFloat(String s) throws Exception {
      // Using the code in DecimalParser.java write a finite state 
      // machine that parses a floating point number of the form 
      //             [-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)? 
  StateFloat state;

  state = StateFloat.SSTART;

  int i = 0;
  double divider = 10;
  double value = 0;
  double power = 0;
  double dmultipe = 1;
  double pmultipe = 1;
  int sign_count = 0;
  if(s.length() == 0)
    System.exit(1);
  if (s.length() == 1 && !Character.isDigit(s.charAt(0)))
    System.exit(1);
  while (i < s.length() && state != StateFloat.SEND) {
    char ch = s.charAt(i);
    switch (state) {
      case SSTART: 
      if (Character.isDigit(ch)) {
        state = StateFloat.SINTEGER;
        value = Character.getNumericValue(ch);  
        i++;
      }
      else if(ch == '+'){
        i++;
        sign_count++;
        if (s.charAt(i) == 'e' || s.charAt(i) == 'E')
          System.exit(1);
      }
      else if (ch == '-'){
        dmultipe = -1;
        i++;
        sign_count++;
       if (s.charAt(i) == 'e' || s.charAt(i) == 'E')
          System.exit(1);
      }
      else if(ch == '.'){
        state = StateFloat.SDECIMAL;
        i++;
        if (i == s.length())
          System.exit(1);
      }
      else {
        throw new Exception("Bad format");
      }
      break;
      case SINTEGER:
      if (sign_count > 1)
        System.exit(1);
      if (Character.isDigit(ch)) {
        state = StateFloat.SINTEGER;
        value = 10.0*value + Character.getNumericValue(ch);
        i++;
      }
      else if (ch == '.') {
        state = StateFloat.SDECIMAL;
        i++;  
        if (i == s.length())
          System.exit(1);
        char c = s.charAt(i);
        if(!Character.isDigit(c))
          System.exit(1);
      }
      else if (ch == 'e' || ch == 'E') {
        state = StateFloat.EEXPRESSION;
        i++;
        if(i == s.length())
          System.exit(1);
         char c = s.charAt(i);
        if((!Character.isDigit(c)) && c != '+' && c != '-')
          System.exit(1);
      }
      else if(ch == '+' || ch == '-')
        System.exit(1);
      break;
      case SDECIMAL:
      if (sign_count > 1)
        System.exit(1);
      if (Character.isDigit(ch)) {
        value = value + Character.getNumericValue(ch)/divider;
        divider = divider * 10;
        i++;
      }
     else if (ch == 'e' || ch == 'E') {
        state = StateFloat.EEXPRESSION;
        i++;
        sign_count = 0;
        if(i == s.length())
          System.exit(1);
         char c = s.charAt(i);
        if((!Character.isDigit(c)) && c != '+' && c != '-')
          System.exit(1);
      }
      else {
        throw new Exception("Bad format");
      }
      break;
      case EEXPRESSION:
      if (Character.isDigit(ch)) {
        power = 10*power + Character.getNumericValue(ch);
        i++;
      }
      //sign_count = 0;
      else if(ch == '-') {
        pmultipe = -pmultipe;
        i++;
        sign_count++;
        if (i == s.length())
          System.exit(1);
        if (sign_count > 1)
        System.exit(1); 
      }
      else if (ch == '+') {
        i++;
        sign_count++;
       if (i == s.length())
          System.exit(1);
        if(sign_count > 1)
          System.exit(1);
      }
      else if(ch == 'e' || ch == 'E')
        System.exit(1);
      else if(ch == '.')
        System.exit(1);
      break;
    }
  }
  value*=dmultipe;
  power*=pmultipe;
  //System.out.println(power);
  power = Math.pow(10.0, power);
  value*=power;
  return value;
}

}


