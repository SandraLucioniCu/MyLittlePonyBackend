package com.sandra.game.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class FinishedGameDto {

    @JsonProperty("storyId")
    public String storyId;

    @JsonProperty("title")
    public String title;

    @JsonProperty("description")
    public String description;

    @JsonProperty("initDate")
    public Date initDate;

    @JsonProperty("endDate")
    public Date endDate;

    @JsonProperty("decisions")
    public List<DecisionDto> decisions;

}
