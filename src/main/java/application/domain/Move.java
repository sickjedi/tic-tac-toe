package application.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Move {
    private char mark;
    private int row;
    private int column;
}
