// the Token1 class encapsulates a single recognized Token1, which has a type and the actual portion of the expression it matched
// So, for the expression "3.7356" (which is a valid input on its own),
// there would be a single Token1 instance, whose type is TokenType.NUMBER and whose data is "3.7356"
public class Token1 {
    public TokenType type;
    public String data;

    public Token1(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }
    /// toString just lets us easily print tokens, it shouldnt be required for anything other than debugging
    @Override
    public String toString() {
        return String.format("(%s %s)", type.name(), data);
    }
}
