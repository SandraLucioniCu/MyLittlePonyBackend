package com.sandra.game.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String id;

    private String password;
    private String name;
    private String email;
    private String ponyImage;

    public User(String name, String email, String password, String ponyImage) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.ponyImage = ponyImage;
    }
}
