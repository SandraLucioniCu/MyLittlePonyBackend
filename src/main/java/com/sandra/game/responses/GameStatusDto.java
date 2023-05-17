package com.sandra.game.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GameStatusDto {

    @JsonProperty(value = "stats")
    public StatsDto statsDto;
}