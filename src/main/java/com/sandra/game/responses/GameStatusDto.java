package com.sandra.game.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class GameStatusDto {

    @JsonProperty(value = "stats")
    public StatsDto statsDto;

    @JsonProperty("question")
    private String question;

    @JsonProperty("answers")
    private List<AnswerDto> answers;
}
