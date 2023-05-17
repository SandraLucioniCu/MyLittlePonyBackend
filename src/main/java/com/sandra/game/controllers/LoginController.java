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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;


    @PostMapping(value="/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> singUp(@RequestBody NewUserForm request){

        String name = request.getNickname();
        String email = request.getEmail();
        String password = request.getPassword();
        String ponyImage = request.getPonyImg();

        var encodedPassword = passwordEncoder.encode(password);

       User user = new User(name, email, encodedPassword, ponyImage);
       userLoginService.update(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    @PostMapping(value="/enter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody UserLogin request, HttpServletRequest sessionRequest) throws NotFoundException {

        String user = request.getUser();
        String password = request.getPassword();

        var encodedPassword = passwordEncoder.encode(password);

        User userSession = userLoginService.searchLoginUser(user, encodedPassword);

        if(!passwordEncoder.matches(password, userSession.getPassword()))
        {
            throw new NotFoundException();
        }

        HttpSession session = sessionRequest.getSession();
        session.setAttribute("user", userSession.getId());
        return ResponseEntity.ok(userSession.getId());
    }
}
