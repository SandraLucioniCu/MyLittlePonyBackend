package com.sandra.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandra.game.entities.AvailableStory;
import com.sandra.game.repositories.AvailableStoryRepository;
import com.sandra.game.stories.Story;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Esta clase sirve para añadir todas las historias de la carpeta resources a mongodb cuando la aplicacion arranca
 */
@Component
@RequiredArgsConstructor
public class StoryInjectionComponent implements ApplicationRunner {

    private final AvailableStoryRepository availableStoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<AvailableStory> availableStories = availableStoryRepository.findAll();
        List<Story> storiesInFolder = getStoryInResourcesFolder();

        List<AvailableStory> storiesToAdd = new ArrayList<>();

        for (Story story : storiesInFolder) {
            if(availableStories.stream().noneMatch(av -> Objects.equals(av.getStoryId(), story.getId()))){
                storiesToAdd.add(new AvailableStory(story.getId(), story.getTitle(), story.getDescription()));
            }
        }

        if(storiesToAdd.isEmpty()){
            return;
        }

        availableStoryRepository.saveAll(storiesToAdd);
    }


    private List<Story> getStoryInResourcesFolder() throws FileNotFoundException {
        List<Story> stories = new ArrayList<>();

        // carga la historia de la carpeta resources
        File storiesFolder = ResourceUtils.getFile("classpath:stories");
        File[] listOfFiles = storiesFolder.listFiles();

        if(listOfFiles == null){
            return stories;
        }

        ObjectMapper mapper = new ObjectMapper();
        for (File story : listOfFiles) {
            if (!story.isFile() || !story.getName().contains(".json")) {
                continue;
            }

            try {
                Object json = new JSONParser(new FileInputStream(story)).parse();
                stories.add(mapper.convertValue(json, Story.class));
            }catch (Exception e){
                System.out.println("An error happened running the story injection for story with id: " + story.getName());
            }
        }

        return stories;
    }
}
