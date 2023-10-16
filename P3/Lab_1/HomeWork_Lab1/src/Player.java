public class Player {
    private int score;

    public Player() {
        this.score = 0;
    }

    public void increaseScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }
}
