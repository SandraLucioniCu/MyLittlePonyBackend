package com.sandra.game.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Decision {

    public String sceneId;

    public int answer;

    public Statistics statsFrom;

    public Statistics statsTo;
}
