package com.sandra.game.controllers;

import com.sandra.game.entities.User;
import com.sandra.game.services.GameService;
import com.sandra.game.services.PonyService;
import com.sandra.game.services.UserLoginService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Controller
@RequestMapping("/menu")
public class MenuController {
    private PonyService ponyService;
    private UserLoginService userService;
    private GameService gameService;

    @GetMapping("/menu")
    public String getText(HttpServletRequest request){
        try{
            HttpSession session = request.getSession();
            String userSession = session.getAttribute("user").toString();
            Optional<User> optionalUser = userService.searchUser(userSession);

            if(!optionalUser.isPresent()){
                System.out.println("User not found");
                return "redirect:/login";
            }

            String question = gameService.historyText(optionalUser.get().getId());
            List<String> answers =gameService.answerText(optionalUser.get().getId());
            String answerOne = answers.get(0);
            String answerTwo = answers.get(1);
            String answerThree = answers.get(2);

        }catch (Exception e){
            System.out.println(e + " null");
        }
        return "start";
    }


}
