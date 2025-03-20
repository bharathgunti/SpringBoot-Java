package com.example.BasicCrudOperations.Controller;

import com.example.BasicCrudOperations.DTO.UserDTO;
import com.example.BasicCrudOperations.Model.User;
import com.example.BasicCrudOperations.Service.UserService;
import com.example.BasicCrudOperations.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping
    private ResponseEntity<List<UserDTO>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id,@RequestBody User user){
        return userService.updateUser(id,user);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody User user){
        return userService.login(user);
    }
}
