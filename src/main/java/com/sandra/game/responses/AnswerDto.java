package com.sandra.game.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {

    @JsonProperty(value = "text")
    private String text;

    @JsonProperty(value = "index")
    private int index;
}
