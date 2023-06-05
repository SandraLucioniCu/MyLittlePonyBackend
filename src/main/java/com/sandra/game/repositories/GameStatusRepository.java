package com.sandra.game.repositories;

import com.sandra.game.entities.GameStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStatusRepository extends MongoRepository<GameStatus, String> {

    List<GameStatus> findByUserId(String id);

    List<GameStatus> findByUserIdAndStoryId(String userId, String storyId);
}
