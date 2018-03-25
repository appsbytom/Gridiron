package me.Tom.Gridiron.GameManager;

public enum GameState {

    IN_LOBBY(false), LOADING(false), IN_PROGRESS(false), POST_GAME(false);

    GameState(boolean canJoin) {
        this.canJoin = canJoin;
    }

    private boolean canJoin;
    public boolean canJoin() {
        return canJoin;
    }

    private static GameState currentState;
    public static void setCurrentState(GameState currentState) {
        GameState.currentState = currentState;
    }
    public static GameState getCurrentState() {
        return currentState;
    }
    public static boolean isState(GameState state) {
        return currentState == state;
    }
}
