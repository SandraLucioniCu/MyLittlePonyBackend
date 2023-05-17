package com.sandra.game.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "friends")
@Getter
@Setter
public class Friends {

    @Id
    private String id;

    private String name;
    private int increment;
    private int friendshipBar;

    public Friends(String id, String name, int increment, int friendshipBar) {
        this.id = id;
        this.name = name;
        this.increment = increment;
        this.friendshipBar = friendshipBar;
    }
}
