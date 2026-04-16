package pegsolitaire.game.core;

public enum Pattern {
    STANDARD(
            new String[][]{
                    {"null", "null", "peg", "peg", "peg", "null", "null"},
                    {"null", "null", "peg", "peg", "peg", "null", "null"},
                    {"peg", "peg", "peg", "peg", "peg", "peg", "peg"},
                    {"peg", "peg", "peg", "empty", "peg", "peg", "peg"},
                    {"peg", "peg", "peg", "peg", "peg", "peg", "peg"},
                    {"null", "null", "peg", "peg", "peg", "null", "null"},
                    {"null", "null", "peg", "peg", "peg", "null", "null"}}),
    DIAMOND(
            new String[][]{
                    {"null", "null","null", "null", "peg","null", "null", "null", "null"},
                    {"null", "null","null", "peg", "peg","peg", "null", "null", "null"},
                    {"null", "null","peg", "peg","empty", "peg","peg", "null", "null"},
                    {"null", "peg", "peg","peg", "peg", "peg","peg", "peg", "null"},
                    {"peg", "peg","peg", "peg", "peg","peg", "peg","peg", "peg","peg"},
                    {"null", "peg", "peg","peg", "peg", "peg","peg", "peg", "null"},
                    {"null", "null","peg", "peg","peg", "peg","peg", "null", "null"},
                    {"null", "null","null", "peg", "peg","peg", "null", "null", "null"},
                    {"null", "null","null", "null", "peg","null", "null", "null", "null"}}),
    EUROPEAN(
            new String[][]{
                    {"null", "null", "peg", "peg", "peg", "null", "null"},
                    {"null", "peg", "peg", "peg", "peg", "peg", "null"},
                    {"peg", "peg", "peg", "empty", "peg", "peg", "peg"},
                    {"peg", "peg", "peg", "peg", "peg", "peg", "peg"},
                    {"peg", "peg", "peg", "peg", "peg", "peg", "peg"},
                    {"null", "peg", "peg", "peg", "peg", "peg", "null"},
                    {"null", "null", "peg", "peg", "peg", "null", "null"}}),
    TEST(
            new String[][]{
        {"null", "null", "empty", "empty", "empty", "null","null"},
        {"null", "null", "empty", "empty", "empty", "null","null"},
        {"empty", "empty", "empty", "empty", "empty", "empty", "empty"},
        {"empty", "empty", "peg", "empty", "peg", "peg", "empty"},
        {"empty", "empty", "empty", "empty", "empty", "empty", "empty"},
        {"null", "null", "empty", "empty", "empty", "null","null"},
        {"null", "null", "empty", "empty", "empty", "null","null"}});

    private final String[][] pattern;

    Pattern(String[][] pattern) {
        this.pattern = pattern;
    }

    public String[][] getPattern() {
        return pattern;
    }
    }