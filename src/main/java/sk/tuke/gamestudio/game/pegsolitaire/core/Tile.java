package sk.tuke.gamestudio.game.pegsolitaire.core;

public class Tile {
    private TileState state;

    public Tile(TileState state) {
        this.state = state;
    }

    public TileState getState() {
        return state;
    }

    public void setState(TileState state) {
        this.state = state;
    }
}
