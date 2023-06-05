package com.sandra.game.controllers;

import com.sandra.game.exceptions.NotAuthorizedException;
import com.sandra.game.requests.UpdateGameRequest;
import com.sandra.game.responses.GameStatusDto;
import com.sandra.game.services.GameService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    // endpoint para recoger el estado del jugador en la partida
    // si no tiene partida empezada, crear una nueva
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameStatusDto> getGameStatus(HttpServletRequest sessionRequest, @RequestParam String storyId)
            throws FileNotFoundException, ParseException {

        // recoger user id
        HttpSession session = sessionRequest.getSession();
        String userId = (String) session.getAttribute("user");

        if (userId == null) {
            throw new NotAuthorizedException();
        }

        return ResponseEntity.ok(gameService.getGameStatus(userId, storyId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameStatusDto> updateGameStatus(HttpServletRequest sessionRequest, @RequestBody UpdateGameRequest request)
            throws FileNotFoundException, ParseException {

        // recoger user id
        HttpSession session = sessionRequest.getSession();
        String userId = (String) session.getAttribute("user");

        if (userId == null) {
            throw new NotAuthorizedException();
        }

        return ResponseEntity.ok(gameService.updateGameStatus(userId, request.getStoryId(), request.getAnswerIndex()));
    }
}
