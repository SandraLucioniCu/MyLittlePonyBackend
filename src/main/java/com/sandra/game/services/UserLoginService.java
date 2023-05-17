package com.sandra.game.services;

import com.sandra.game.entities.User;
import com.sandra.game.exceptions.InsertFailedException;
import com.sandra.game.exceptions.NotFoundException;
import com.sandra.game.repositories.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService {

    private final UserRepository userRepository;

    public UserLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User update(User user){
        try {
            return userRepository.save(user);
        } catch (Exception ex){
            throw new InsertFailedException("Could not insert new user into the database");
        }
    }

    public Optional<User> searchUser(String id){
        return userRepository.findById(id);
    }

    public User searchLoginUser(String user, String encodedPassword) {
        if (user.contains("@"))  return searchByEmail(user);
        return searchByName(user);
    }

    private User searchByEmail(String email) {
        var optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElseThrow(() -> new NotFoundException("User not found"));
    }

    private User searchByName(String username) {
        var optionalUser = userRepository.findByEmail(username);
        return optionalUser.orElseThrow(() -> new NotFoundException("User not found"));
    }
}
