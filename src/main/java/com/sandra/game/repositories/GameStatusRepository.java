package com.sandra.game.repositories;

import com.sandra.game.entities.GameStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameStatusRepository extends MongoRepository<GameStatus, String> {
}
