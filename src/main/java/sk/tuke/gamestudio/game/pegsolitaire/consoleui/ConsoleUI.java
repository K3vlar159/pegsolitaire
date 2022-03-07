package sk.tuke.gamestudio.game.pegsolitaire.consoleui;

import sk.tuke.gamestudio.game.pegsolitaire.core.FieldState;
import sk.tuke.gamestudio.game.pegsolitaire.core.GameField;
import sk.tuke.gamestudio.game.pegsolitaire.core.MoveDirection;
import sk.tuke.gamestudio.game.pegsolitaire.core.Tile;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {

    private final GameField field;
    private final Scanner scanner = new Scanner(System.in);
    private static final Pattern INPUT_PATTERN = Pattern.compile("([A-G])([1-7])([UDLR])");

    public ConsoleUI(GameField field) {
        this.field = field;
    }

    public void start() {
        while (field.getState() == FieldState.PLAYING) {
            drawField(this.field);
            processInput();
            field.updateState();
        }
        if(field.getState() == FieldState.SOLVED)
        {
            System.out.println("Congratulations, you solved the puzzle :)");
        }
        else{
            System.out.println("Sorry, you failed :(");
        }
    }

    private void drawField(GameField field) {
        printHeader(field);
        printBody(field);
    }

    private void printBody(GameField field) {
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print((char) (row + 'A'));
            for (int col = 0; col < field.getColumnCount(); col++) {
                Tile tile = field.getTile(row, col);
                printTile(tile);
            }
            System.out.println();
        }
    }

    private void printTile(Tile tile) {
        System.out.print("  ");
        switch (tile.getState()) {
            case CLOSED:
                System.out.print(' ');
                break;
            case PEG:
                System.out.print('O');
                break;
            case EMPTY:
                System.out.print('-');
                break;
        }
    }

    private void printHeader(GameField field) {
        System.out.print(' ');
        for (int col = 0; col < field.getColumnCount(); col++) {
            System.out.print("  ");
            System.out.print(col + 1);
        }
        System.out.println();
    }

    private void processInput() {
        System.out.println("Commands: X - exit, A1L - move peg, directions - U=UP, D=DOWN, L=LEFT, R=RIGHT");
        String input = scanner.nextLine().toUpperCase();
        if ("X".equals(input)) {
            System.exit(0);
        }
        Matcher matcher = INPUT_PATTERN.matcher(input);
        processMatcher(matcher);
    }

    private void processMatcher(Matcher matcher) {
        if (matcher.matches()) {
            int row = matcher.group(1).charAt(0) - 'A';
            int column = Integer.parseInt(matcher.group(2)) - 1;
            switch (matcher.group(3)) {
                case "U":
                    field.movePeg(row, column, MoveDirection.UP);
                    break;
                case "D":
                    field.movePeg(row, column, MoveDirection.DOWN);
                    break;
                case "L":
                    field.movePeg(row, column, MoveDirection.LEFT);
                    break;
                case "R":
                    field.movePeg(row, column, MoveDirection.RIGHT);
                    break;
            }
        }
        else {
            System.out.println("Invalid input !!");
        }
    }
}
