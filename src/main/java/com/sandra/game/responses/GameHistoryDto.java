package com.sandra.game.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class GameHistoryDto {

    @JsonProperty("finishedGames")
    public List<FinishedGameDto> finishedGames;

}
