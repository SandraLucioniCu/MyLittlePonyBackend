package com.sandra.game.entities;

import com.sandra.game.responses.AvailableStoryDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Document(collection = "story")
@NoArgsConstructor
@Getter
@Setter
public class AvailableStory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Indexed()
    private String storyId;

    private String title;

    private String description;

    public AvailableStory(String storyId, String title, String description) {
        this.storyId = storyId;
        this.title = title;
        this.description = description;
    }

    public AvailableStoryDto toDto(){
        return new AvailableStoryDto(this.storyId, this.title, this.description);
    }
}
