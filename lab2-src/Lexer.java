/**
 * Created by siyujiang on 2/9/17.
 */
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

// the Lexer class encapsulates all of the logic related to tokens --
// It handles converting an expression string into an array of tokens,
// as well as the functions that the parser uses to consume that array of tokens
public class Lexer {
    private String expression;
    public ArrayList<Token1> tokens;
    private int i = 0;
    public Lexer(String expression) {
        this.expression = expression.replaceAll("\\s+", ""); // remove all of the spaces from the input expression
        this.tokenize(); // initialize our Token1 array from the expression string
    }
    public Token1 lex(){
        if(i > tokens.size() - 1){
            Token1 t = new Token1(TokenType.ERROR, "error");
            return t;
        }
        //System.out.print(tokens.get(i).toString());
        return tokens.get(i++);
    }
    // Simply prints each Token1. You wouldn't need anything like this except for debugging
    public void printTokens() {
        for (Token1 t : tokens) {
            System.out.printf("%s ", t.toString());
        }
        System.out.print("\n");
    }

    // This function converts this.expression into an array of tokens
    private void tokenize() {
        tokens = new ArrayList<Token1>();
        StringBuilder buffer = new StringBuilder(); // use a StringBuilder because string concatenation in Java is expensive

        // loop through all of the enum values (In this example, it will give NUMBER, PLUS, LPAREN, and RPAREN)
        for (TokenType tokenType : TokenType.values()) {
            // here lies the magic...
            // as an `enum`, the TokenType class provides a default String name() method which returns the literal name of the Token1 (such as NUMBER, PLUS, etc...)
            String tokenName = tokenType.name();

            // remember that each TokenType had an associated string called "pattern" that contained the regex needed to match that particular type of Token1
            String tokenPattern = tokenType.pattern;

            // what we are doing is building up one really long regex from a bunch of small regexes here
            // this creates a series of "matching groups" -- see http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#groupname
            // It allows you to match a specific portion of a longer regex and refer to it later by name
            // the () creates a group
            // the ?<string> at the beginning gives that group the name "string"
            // after the name to before the end of the group is the actual pattern that that group should match
            buffer.append(String.format("|(?<%s>%s)", tokenName, tokenPattern));
        }

        // the result of the above loop will be the following string:
        // "|(?<NUMBER>[0-9]+(\\.[0-9]+)?)|(?<PLUS>\+)|(?<LPAREN>\()|(?<RPAREN>\))"

        // we create a pattern object from the above regex, minus the first character (since its an extraneous | character that was just an artifact of looping)
        Pattern pattern = Pattern.compile(buffer.substring(1));

        // the pattern object gives us access to a customized matcher object based on the pattern and the input string
        Matcher matcher = pattern.matcher(expression);

        // matcher.find() starts at the beginning the string or the end of the last thing found by matcher.find() and returns true if there is anything in the remainder of the string matches anything in the regex
        while (matcher.find()) {
            // figure out which Token1 it matched
            for (TokenType tokenType : TokenType.values()) {
                if (matcher.group(tokenType.name()) != null) {
                    // the name of the group that was matched corresponds to the name of the TokenType
                    // we have found a Token1!
                    // a new Token1 object to the list of tokens with the correct type and data
                    tokens.add(new Token1(tokenType, matcher.group(tokenType.name())));
                    break;
                }
            }
        }
    }
}
