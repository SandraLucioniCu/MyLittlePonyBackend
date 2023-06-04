package com.sandra.game.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserLogin {

    @JsonProperty(value = "user")
    private String user;

    @JsonProperty(value = "password")
    private String password;
}
