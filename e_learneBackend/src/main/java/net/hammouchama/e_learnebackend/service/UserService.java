package net.hammouchama.e_learnebackend.service;

import lombok.AllArgsConstructor;
import net.hammouchama.e_learnebackend.model.User;
import net.hammouchama.e_learnebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    public ResponseEntity<?> login(User loginUser) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());

        if (userOptional.isPresent()) {
            User userFromDB = userOptional.get();
            if (userFromDB.getPassword().equals(loginUser.getPassword())) {
                // Authentication successful
                return ResponseEntity.ok(userFromDB);
            } else {
                // Invalid password
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
            }
        } else {
            // User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    public ResponseEntity<?> registerUser(User user) {
        // Check if the username is already taken
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }
        // Save the user to the database
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}
