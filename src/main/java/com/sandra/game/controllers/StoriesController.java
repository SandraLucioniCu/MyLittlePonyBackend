package com.sandra.game.controllers;

import com.sandra.game.exceptions.NotAuthorizedException;
import com.sandra.game.responses.AvailableStoryDto;
import com.sandra.game.responses.GameStatusDto;
import com.sandra.game.services.StoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoriesController {

    private final StoriesService storiesService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AvailableStoryDto>> getAvailableStories(HttpServletRequest sessionRequest) {

        // recoger user id
        HttpSession session = sessionRequest.getSession();
        String userId = (String) session.getAttribute("user");

        if (userId == null) {
            throw new NotAuthorizedException();
        }

        return ResponseEntity.ok(storiesService.getAvailableStory());
    }
}
