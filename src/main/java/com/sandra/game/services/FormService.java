package com.sandra.game.services;

import com.sandra.game.entities.User;
import com.sandra.game.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormService {

    private final UserRepository userRepository;

    public FormService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> searchUser(String id){
        return userRepository.findById(id);
    }

    public User update(User user){
        return userRepository.save(user);
    }
}
