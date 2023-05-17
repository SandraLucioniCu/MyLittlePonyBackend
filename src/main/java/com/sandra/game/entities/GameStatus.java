package com.sandra.game.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "gameStatus")
@Getter
@Setter
public class GameStatus {

    @Id
    private String id;

    private boolean complete;
    private Decisions decisions;
    private Statistics statistics;

    private int userCurrentIndex;
}
