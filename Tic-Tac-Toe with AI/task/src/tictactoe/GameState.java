package tictactoe;

public enum GameState {
    GNF("Game not finished"),
    DRAW("Draw"),
    X("X wins"),
    O("O wins");

    private final String state;

    GameState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
