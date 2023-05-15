package BoardGame;

import java.time.LocalDateTime;

public class GameRecord {
    private String startTime;
    private String player1Name;
    private String player2Name;
    private String winnerName;

    public GameRecord() {
        // Default constructor for deserialization
    }

    public GameRecord(String startTime, String player1Name, String player2Name, String winnerName) {
        this.startTime = startTime;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.winnerName = winnerName;
    }

    // Getters and setters

    public String getStartTime() {
        return startTime;
    }
    public String getPlayer1Name() {
        return player1Name;
    }
    public String getPlayer2Name() {
        return player2Name;
    }

    public String getWinnerName() {
        return winnerName;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }
    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }
    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "startTime=" + startTime +
                ", player1Name='" + player1Name + '\'' +
                ", player2Name='" + player2Name + '\'' +
                ", winnerName='" + winnerName + '\'' +
                '}';
    }

}