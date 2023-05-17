package com.sandra.game.services;

import com.sandra.game.entities.User;
import com.sandra.game.exceptions.InsertFailedException;
import com.sandra.game.exceptions.NotFoundException;
import com.sandra.game.exceptions.UserAlreadyExistsException;
import com.sandra.game.repositories.UserRepository;
import com.sandra.game.requests.NewUserForm;
import com.sandra.game.requests.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(NewUserForm form) {

        String name = form.getNickname();
        String email = form.getEmail();
        String password = form.getPassword();
        String ponyImage = form.getPonyImg();

        if(userRepository.findByEmail(email).isPresent()){
            throw new UserAlreadyExistsException(email);
        }

        var encodedPassword = passwordEncoder.encode(password);

        User user = new User(name, email, encodedPassword, ponyImage);

        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new InsertFailedException("Could not insert new user into the database");
        }
    }

    public User tryDoLogin(UserLogin form){

        String username = form.getUser();
        String password = form.getPassword();

        User user = searchLoginUser(username);

        if(!passwordEncoder.matches(password, user.getPassword()))
        {
            throw new NotFoundException();
        }
        
        return user;
    }

    public Optional<User> searchUser(String id) {
        return userRepository.findById(id);
    }

    private User searchLoginUser(String user) {
        if (user.contains("@")) return searchByEmail(user);
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
