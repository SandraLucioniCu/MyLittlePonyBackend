package com.sandra.game.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "statistics")
@Getter
@Setter
public class Statistics {
    @Id
    private String id;

    //popularidad
    private int popularity;
    //conocimiento
    private int knowledge;
    //diversion
    private int fun;

}
