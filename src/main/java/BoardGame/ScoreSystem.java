package BoardGame;

import java.util.ArrayList;
import java.util.List;

public class ScoreSystem {
    private int player1Score;
    private int player2Score;
    private List<String> matchLog;

    public ScoreSystem() {
        player1Score = 0;
        player2Score = 0;
        matchLog = new ArrayList<>();
    }

    public void addPlayer1Score() {
        player1Score++;
    }

    public void addPlayer2Score() {
        player2Score++;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void addMatchLog(String log) {
        matchLog.add(log);
    }

    public List<String> getMatchLog() {
        return matchLog;
    }
}
