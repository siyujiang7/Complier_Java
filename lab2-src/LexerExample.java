// this class is fairly uninteresting -- all it does is initialize the lexer and print the tokens
public class LexerExample {
    public static void main(String[] args) {
        new Lexer(args[0]).printTokens();
    }
}
