package com.sandra.game.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatsDto {

    @JsonProperty(value = "knowledge")
    public int knowledge;

    @JsonProperty(value = "fun")
    public int fun;

    @JsonProperty(value = "popularity")
    public int popularity;
}
