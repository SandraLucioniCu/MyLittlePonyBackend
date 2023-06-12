package com.sandra.game.controllers;

import com.sandra.game.exceptions.NotAuthorizedException;
import com.sandra.game.responses.GameHistoryDto;
import com.sandra.game.services.GameService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/history")
@RequiredArgsConstructor
public class GameHistoryController {

    private final GameService gameService;

   // endpoint para recoger todas las partidas finalizadas del usuario
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameHistoryDto> getGameHistory(HttpServletRequest sessionRequest)
            throws FileNotFoundException, ParseException {

        // recoger user id
        HttpSession session = sessionRequest.getSession();
        String userId = (String) session.getAttribute("user");

        if (userId == null) {
            throw new NotAuthorizedException();
        }

        return ResponseEntity.ok(gameService.getFinishedGames(userId));
    }
}
