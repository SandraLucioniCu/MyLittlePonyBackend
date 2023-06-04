package com.sandra.game.stories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Changes {

    @JsonProperty("fun")
    private int fun;

    @JsonProperty("knowledge")
    private int knowledge;

    @JsonProperty("popularity")
    private int popularity;
}
