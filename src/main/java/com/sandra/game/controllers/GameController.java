package com.sandra.game.controllers;

import com.sandra.game.entities.User;
import com.sandra.game.exceptions.NotAuthorizedException;
import com.sandra.game.exceptions.NotFoundException;
import com.sandra.game.requests.UserLogin;
import com.sandra.game.responses.GameStatusDto;
import com.sandra.game.responses.StatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    // endpoint para recoger el estado del jugador en la partida
    // si no tiene partida empezada, crear una nueva
    @GetMapping
    public ResponseEntity<GameStatusDto> GetGameStatus(HttpServletRequest sessionRequest){

        // recoger user id
        HttpSession session = sessionRequest.getSession();
        String userId = (String) session.getAttribute("user");

        if(userId == null){
            throw new NotAuthorizedException();
        }
        // buscar en la base de datos si el usuario tiene un juego activo

        // si no lo tiene, crear uno nuevo, despues de crearlo transformar el objeto de base de datos en un gamestatusdto
        // si lo tiene, transformar el objeto de base de datos en un gamestatusdto
        // tiene que recoger el json de historia de la carpeta resources y transformarlo en un objeto Java para guardarlo en cache
        // incluir en game status el texto y las respuestas en las que se encuentra el usuario en este momento en el juego
        return ResponseEntity.ok(new GameStatusDto(new StatsDto(20, 50, 10)));
    }
}
