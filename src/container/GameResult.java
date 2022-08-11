package container;

public class GameResult {

    private final int loser, winner;

    public GameResult(int loser, int winner) {
        this.loser = loser;
        this.winner = winner;
    }

    public int getLoser() {
        return loser;
    }

    public int getWinner() {
        return winner;
    }
}
