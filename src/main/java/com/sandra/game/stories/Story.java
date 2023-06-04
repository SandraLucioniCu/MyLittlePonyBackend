package com.sandra.game.stories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandra.game.exceptions.SceneNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Story {

    @JsonProperty("settings")
    private Settings settings;

    @JsonProperty("scenes")
    private HashMap<String, Scene> scenes;

    public Scene getSceneById(String sceneId){

        if(!this.scenes.containsKey(sceneId))
        {
            throw new SceneNotFoundException(sceneId);
        }

        return this.scenes.get(sceneId);
    }
}
