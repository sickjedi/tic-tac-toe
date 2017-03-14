package application.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Move {
    private String player;
    private int row;
    private int column;
}
