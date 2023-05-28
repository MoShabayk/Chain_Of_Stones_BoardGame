package BoardGame.Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GameRecord {
     private int player1turns;
     private int player2turns;
    @NonNull private String startTime;
    @NonNull  private String player1Name;
    @NonNull private String player2Name;
     private String winnerName;
}