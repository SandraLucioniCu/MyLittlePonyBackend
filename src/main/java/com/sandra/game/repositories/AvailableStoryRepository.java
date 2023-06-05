package com.sandra.game.repositories;

import com.sandra.game.entities.AvailableStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableStoryRepository extends MongoRepository<AvailableStory, String> {
}
