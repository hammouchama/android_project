package net.oussa.backend.service;

import lombok.AllArgsConstructor;
import net.oussa.backend.model.User;
import net.oussa.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    public ResponseEntity<?> login(User loginUser) {
        Optional<User> userOptional = this.userRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
        if (userOptional.isPresent()) {
            User userFromDB = (User)userOptional.get();
            return userFromDB.getPassword().equals(loginUser.getPassword()) ? ResponseEntity.ok(userFromDB) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    public ResponseEntity<?> registerUser(User user) {
        if (this.userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        } else {
            this.userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");
        }
    }

}
