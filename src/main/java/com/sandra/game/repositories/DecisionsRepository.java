package com.sandra.game.repositories;

import com.sandra.game.entities.Decisions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecisionsRepository extends MongoRepository<Decisions, String>{


}
