package com.sandra.game.repositories;

import com.sandra.game.entities.HistoryText;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<HistoryText, String> {
    HistoryText findByIndex(int index);
}
