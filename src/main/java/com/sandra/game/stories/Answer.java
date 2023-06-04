package com.sandra.game.stories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Answer {

    @JsonProperty("text")
    private String text;

    @JsonProperty("go")
    private String go;

    @JsonProperty("changes")
    private Changes changes;
}
