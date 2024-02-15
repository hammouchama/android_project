package net.oussa.backend.controller;

import lombok.AllArgsConstructor;
import net.oussa.backend.model.User;
import net.oussa.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    UserService userService;
    @PostMapping({"/login"})
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        try {
            return this.userService.login(loginUser);
        } catch (Exception var3) {
            var3.printStackTrace();
            return new ResponseEntity("Something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping({"/register"})
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            return this.userService.registerUser(user);
        } catch (Exception var3) {
            var3.printStackTrace();
            return new ResponseEntity("something was wrong ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
