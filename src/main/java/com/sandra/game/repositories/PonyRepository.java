package com.sandra.game.repositories;

import com.sandra.game.entities.Pony;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PonyRepository extends MongoRepository<Pony, String> {

    Pony findByPosition(int position);

    List<Pony> findByFriendshipBar();

}
