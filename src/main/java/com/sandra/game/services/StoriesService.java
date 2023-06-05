package com.sandra.game.services;

import com.sandra.game.entities.AvailableStory;
import com.sandra.game.exceptions.NoAvailableStoriesException;
import com.sandra.game.repositories.AvailableStoryRepository;
import com.sandra.game.responses.AvailableStoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de operar con los datos que estan dentro del documento AvailableStory
 */
@Service
@RequiredArgsConstructor
public class StoriesService {

    private final AvailableStoryRepository availableStoryRepository;

    public List<AvailableStoryDto> getAvailableStory(){
        List<AvailableStory> availableStories = availableStoryRepository.findAll();

        if(availableStories.isEmpty())
        {
            throw new NoAvailableStoriesException();
        }

        List<AvailableStoryDto> response = new ArrayList<>();
        for (AvailableStory story: availableStories) {
            response.add(story.toDto());
        }

        return response;
    }
}
