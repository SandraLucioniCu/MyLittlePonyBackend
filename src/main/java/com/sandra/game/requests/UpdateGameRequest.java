package com.sandra.game.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateGameRequest {

    @JsonProperty(value = "storyId")
    private String storyId;

    @JsonProperty(value = "answerIndex")
    private int answerIndex;
}
