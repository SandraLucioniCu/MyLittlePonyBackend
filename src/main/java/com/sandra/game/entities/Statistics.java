package com.sandra.game.entities;

import com.sandra.game.responses.StatsDto;
import com.sandra.game.stories.Changes;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Statistics {

    //popularidad
    private int popularity;
    //conocimiento
    private int knowledge;
    //diversion
    private int fun;

    // transforma este objeto en el objeto que devolvemos al lado cliente
    public StatsDto toDto()
    {
        return new StatsDto(this.knowledge, this.fun, this.popularity);
    }

    // suma o resta a las estadisticas actuales las que se encuentran en el json de historia
    public Statistics apply(Changes changes){
        return new Statistics(
                this.popularity + changes.getPopularity(),
                this.knowledge + changes.getKnowledge(),
                this.fun + changes.getFun()
        );
    }

}
