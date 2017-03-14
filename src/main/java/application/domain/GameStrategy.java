package application.domain;

public enum  GameStrategy {
    WEAK, STRONG;

    public static GameStrategy getRandomStrategy() {
        double randomNumber = Math.random();

        if (randomNumber > 0.5) {
            return WEAK;
        } else {
            return STRONG;
        }
    }
}
