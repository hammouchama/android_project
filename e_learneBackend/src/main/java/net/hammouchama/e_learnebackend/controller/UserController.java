package net.hammouchama.e_learnebackend.controller;

import lombok.AllArgsConstructor;
import net.hammouchama.e_learnebackend.model.User;
import net.hammouchama.e_learnebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser){
        System.out.println(loginUser.toString());
        try {
            return userService.login(loginUser);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something was worg", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            return userService.registerUser(user);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("somthig was worgn ",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
