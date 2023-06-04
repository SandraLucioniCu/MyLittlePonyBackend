package com.sandra.game.exceptions;

public class SceneNotFoundException extends RuntimeException{

    public SceneNotFoundException()
    {
        super();
    }

    public SceneNotFoundException(String sceneId)
    {
        super("Scene with id " + sceneId + " was not found");
    }
}
