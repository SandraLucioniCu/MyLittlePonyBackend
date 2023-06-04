package com.sandra.game.stories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Settings {

    @JsonProperty("stats")
    private Stats stats;

    @JsonProperty("background")
    private String background;

    @JsonProperty("init")
    private String init;
}
