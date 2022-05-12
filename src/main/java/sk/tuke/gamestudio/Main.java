package sk.tuke.gamestudio;

import sk.tuke.gamestudio.game.pegsolitaire.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.pegsolitaire.core.GameField;
import sk.tuke.gamestudio.game.pegsolitaire.core.Pattern;

public class Main {
    private static final String[][] standard = {
            {"null", "null", "peg", "peg", "peg", "null", "null"},
            {"null", "null", "peg", "peg", "peg", "null", "null"},
            {"peg", "peg", "peg", "peg", "peg", "peg", "peg"},
            {"peg", "peg", "peg", "empty", "peg", "peg", "peg"},
            {"peg", "peg", "peg", "peg", "peg", "peg", "peg"},
            {"null", "null", "peg", "peg", "peg", "null", "null"},
            {"null", "null", "peg", "peg", "peg", "null", "null"}};
    private static final String[][] standardtest = {
            {"null", "null", "empty", "empty", "empty", "null","null"},
            {"null", "null", "empty", "empty", "empty", "null","null"},
            {"empty", "empty", "empty", "empty", "empty", "empty", "empty"},
            {"empty", "empty", "peg", "empty", "peg", "peg", "empty"},
            {"empty", "empty", "empty", "empty", "empty", "empty", "empty"},
            {"null", "null", "empty", "empty", "empty", "null","null"},
            {"null", "null", "empty", "empty", "empty", "null","null"}};

    public static void main(String[] args) {
        GameField field = new GameField(Pattern.STANDARD.getPattern());
        ConsoleUI ui = new ConsoleUI(field);
        ui.play();
    }

}
