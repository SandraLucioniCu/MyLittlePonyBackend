package com.sandra.game.controllers;

import com.sandra.game.entities.User;
import com.sandra.game.exceptions.NotFoundException;
import com.sandra.game.requests.NewUserForm;
import com.sandra.game.requests.UserLogin;
import com.sandra.game.services.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final UserLoginService userLoginService;

    @PostMapping(value="/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> singUp(@RequestBody NewUserForm request){

        userLoginService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    @PostMapping(value="/enter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody UserLogin request, HttpServletRequest sessionRequest) throws NotFoundException {

        User user = userLoginService.tryDoLogin(request);

        HttpSession session = sessionRequest.getSession();
        session.setAttribute("user", user.getId());
        return ResponseEntity.ok(user.getId());
    }
}
