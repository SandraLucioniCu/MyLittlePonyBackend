package com.sandra.game.controllers;

import com.sandra.game.entities.User;
import com.sandra.game.services.UserLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/informacion")
public class ProfileFormController {

    private final UserLoginService userLoginService;

    public ProfileFormController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @RequestMapping("/act")
    public String showDataProfile(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(); //recoger sesion

            String userSession = session.getAttribute("user").toString();

            Optional<User> optionalUser = userLoginService.searchUser(userSession);
            if (!optionalUser.isPresent()) {
                System.out.println("User not found.");
                return "login";
            }


        } catch (Exception e) {
            System.out.println(e + " null");
        }
        return "clientForm";
    }

    @PostMapping("/actualizado")
    public String updateUserInformation(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();

            String userSession = session.getAttribute("user").toString();

            Optional<User> optionalUser = userLoginService.searchUser(userSession);
            if (!optionalUser.isPresent()) {
                System.out.println("User not found.");
                return "act";
            }
        } catch (Exception e) {
            System.out.println(e + " null");
        }
        return "information";

    }
}
