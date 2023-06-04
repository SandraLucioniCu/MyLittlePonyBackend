package com.sandra.game.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "gameStatus")
@NoArgsConstructor
@Getter
@Setter
public class GameStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Indexed()
    private String userId;

    private String currentScene;
    private boolean isFinished;
    private Statistics statistics;
    private List<Decision> decisions;

    @CreatedDate
    private Date date;

    public GameStatus(String userId, String currentScene, Statistics statistics) {
        this.userId = userId;
        this.currentScene = currentScene;
        this.statistics = statistics;
        this.isFinished = false;
        this.decisions = new ArrayList<>();
    }
}
