package sk.tuke.gamestudio.game.pegsolitaire.core;

import static java.lang.Math.round;

public class GameField {
    private final Tile[][] tiles;
    private final String[][] pattern;
    private final int rowCount;
    private final int columnCount;
    private FieldState state;
    private long startTime;

    public GameField(String[][] pattern) {
        this.tiles = new Tile[pattern.length][pattern[0].length];
        this.pattern = pattern;
        this.rowCount = pattern.length;
        this.columnCount = pattern[0].length;
        this.state = FieldState.PLAYING;
        build();
    }

    public void reset(){
        this.state = FieldState.PLAYING;
        build();
    }

    private void build() {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                switch (pattern[row][col]) {
                    case "null":
                        tiles[row][col] = new Tile(TileState.CLOSED);
                        break;
                    case "peg":
                        tiles[row][col] = new Tile(TileState.PEG);
                        break;
                    case "empty":
                        tiles[row][col] = new Tile(TileState.EMPTY);
                        break;
                }

            }
        }
        startTime = System.currentTimeMillis();
    }

    private boolean canBeMoved(int row, int col, MoveDirection dir) {
        Tile tile = tiles[row][col];
        if (tile.getState() != TileState.PEG) {
            //System.out.println("Not a Peg !!!");
            return false;
        }
        else {
            switch (dir) {
                case UP:
                    return (row >= 2 && tiles[row - 1][col].getState() == TileState.PEG && tiles[row - 2][col].getState() == TileState.EMPTY);
                case DOWN:
                    return (row < rowCount - 2 && tiles[row + 1][col].getState() == TileState.PEG && tiles[row + 2][col].getState() == TileState.EMPTY);
                case LEFT:
                    return (col >= 2 && tiles[row][col - 1].getState() == TileState.PEG && tiles[row][col - 2].getState() == TileState.EMPTY);
                case RIGHT:
                    return (col < columnCount - 2 && tiles[row][col + 1].getState() == TileState.PEG && tiles[row][col + 2].getState() == TileState.EMPTY);
                default:
                    return false;
            }
        }
    }

    private boolean anyMovesLeft() {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                for (MoveDirection direction : MoveDirection.values()) {
                    if (canBeMoved(row, col, direction)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void destroyPeg(int row, int col) {
        if (tiles[row][col].getState() == TileState.PEG) {
            tiles[row][col].setState(TileState.EMPTY);
        }
    }

    private boolean isSolved() {
        int pegCount = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if (tiles[row][col].getState() == TileState.PEG) {
                    pegCount += 1;

                }
            }
        }
        return pegCount == 1;
    }

    public void movePeg(int row, int col, MoveDirection dir) {
        if (canBeMoved(row, col, dir)) {
            destroyPeg(row, col);
            switch (dir) {
                case UP:
                    destroyPeg(row - 1, col);
                    tiles[row - 2][col].setState(TileState.PEG);
                    break;
                case DOWN:
                    destroyPeg(row + 1, col);
                    tiles[row + 2][col].setState(TileState.PEG);
                    break;
                case LEFT:
                    destroyPeg(row, col - 1);
                    tiles[row][col - 2].setState(TileState.PEG);
                    break;
                case RIGHT:
                    destroyPeg(row, col + 1);
                    tiles[row][col + 2].setState(TileState.PEG);
                    break;
            }


        }
    }
    public void updateState()
    {
        if(!anyMovesLeft())
        {
            if(isSolved()){
                this.state = FieldState.SOLVED;
                return;
            }
            this.state = FieldState.FAILED;
        }
    }

    public int getScore() {
        return round(50000 / ((int) (System.currentTimeMillis() - startTime) / 1000));
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public FieldState getState() {
        return state;
    }

    public void setState(FieldState state) {
        this.state = state;
    }
}
