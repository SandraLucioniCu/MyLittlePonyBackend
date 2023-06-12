package com.sandra.game.stories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandra.game.responses.AnswerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Scene {

    @JsonProperty("condition")
    private Condition condition;

    @JsonProperty("text")
    private String text;

    @JsonProperty("answers")
    private List<Answer> answers;

    @JsonProperty("isEnd")
    private boolean isEnd;

    public List<AnswerDto> getAnswersAsDto() {
        List<AnswerDto> dto = new ArrayList<>();

        for (Answer answer: answers) {
            dto.add(new AnswerDto(answer.getText(), answers.indexOf(answer)));
        }
        return dto;
    }
}
