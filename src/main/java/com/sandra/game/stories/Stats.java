package com.sandra.game.stories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandra.game.entities.Statistics;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Stats {

    @JsonProperty("fun")
    private int fun;

    @JsonProperty("knowledge")
    private int knowledge;

    @JsonProperty("popularity")
    private int popularity;

    public Statistics ToMongoDbData() {
        return new Statistics(this.popularity, this.knowledge, this.fun);
    }
}
