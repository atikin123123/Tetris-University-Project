package tetris;
public class GameThread extends Thread {

    GameArea ga;
    GameForm gf;

    private int score;
    private int level = 1;
    private int scorePerLevel = 3;
    private static int counter = 0;

    private int pause = 1000;
    private int speedupPerLevel = 50;

    public GameThread(GameArea ga, GameForm gf) {
        this.ga = ga;
        this.gf = gf;

        gf.updateScore(score);
        gf.updateLevel(level);
    }

    @Override
    public void run() {

        try {
            level = Integer.parseInt(StartupForm.getInitialLevel());
            gf.updateLevel(level);
        } catch(Exception e) {
            level = 1;
        }

        while (true) {
            ga.spawnBlock();
            
            while (ga.moveBlockDown()) {
                try {
                    pause = Math.max(100, 1000 - level * speedupPerLevel);
                    Thread.sleep(pause);
                } catch (InterruptedException e) {
                    return;
                }
            }

            if (ga.isBlockOutOfBounds()) {
                Tetris.gameOver(score);
                break;
            }

            ga.moveBlockToBackground();
            score += ga.checkLines();
            gf.updateScore(score);

            if (counter >= scorePerLevel) {
                level++;
                gf.updateLevel(level);
                counter = 0;
            }
        }
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int newCounter) {
        counter = newCounter;
    }
}