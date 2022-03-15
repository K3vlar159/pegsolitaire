package sk.tuke.gamestudio.game.pegsolitaire.core;

import sk.tuke.gamestudio.game.pegsolitaire.consoleui.ConsoleUI;

public class Main {
    private static final String[][] standard = {
            {"null", "null", "peg", "peg", "peg", "null", "null"},
            {"null", "null", "peg", "peg", "peg", "null", "null"},
            {"peg", "peg", "peg", "peg", "peg", "peg", "peg"},
            {"peg", "peg", "peg", "empty", "peg", "peg", "peg"},
            {"peg", "peg", "peg", "peg", "peg", "peg", "peg"},
            {"null", "null", "peg", "peg", "peg", "null", "null"},
            {"null", "null", "peg", "peg", "peg", "null", "null"}};

    public static void main(String[] args) {
        GameField field = new GameField(standard);
        ConsoleUI ui = new ConsoleUI(field);
        ui.start();
    }

}
