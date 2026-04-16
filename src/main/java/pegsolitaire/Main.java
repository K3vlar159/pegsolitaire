package pegsolitaire;

import pegsolitaire.game.consoleui.ConsoleUI;
import pegsolitaire.game.core.GameField;
import pegsolitaire.game.core.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Peg Solitaire console mode (no Spring, no database, no scoring).");
        GameField field = new GameField(Pattern.TEST.getPattern());
        ConsoleUI ui = new ConsoleUI(field);
        ui.play();
    }
}

