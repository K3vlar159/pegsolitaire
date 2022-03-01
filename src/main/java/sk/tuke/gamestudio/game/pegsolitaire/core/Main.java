package sk.tuke.gamestudio.game.pegsolitaire.core;

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

        drawField(field);
    }

    private static void drawField(GameField field) {
        for (int row = 0; row < field.getRowCount(); row++) {
            for (int col = 0; col < field.getColumnCount(); col++) {
                Tile tile = field.getTile(row, col);
                System.out.print("  ");
                switch (tile.getState()) {
                    case CLOSED:
                        System.out.print('-');
                        break;
                    case PEG:
                        System.out.print('O');
                        break;
                    case EMPTY:
                        System.out.print('X');
                        break;
                }
            }
            System.out.println();
        }
    }

}
