package com.example.BasicCrudOperations.Service;

import com.example.BasicCrudOperations.DTO.UserDTO;
import com.example.BasicCrudOperations.Exception.EmailNotFoundException;
import com.example.BasicCrudOperations.Exception.UserNotFoundException;
import com.example.BasicCrudOperations.Model.User;
import com.example.BasicCrudOperations.Repository.UserRepository;
import com.example.BasicCrudOperations.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getAge(),user.getEmail());
    }

    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    public ResponseEntity<UserDTO> getUserById(int id) {
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }

        User newuser = user.get();
        UserDTO userDTO = convertToDTO(newuser);

        return new ResponseEntity<>(userDTO, HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<String> createUser(User user) {
        Optional<User>newuser=userRepository.findByEmail(user.getEmail());
        if(newuser.isPresent()){
            throw new EmailNotFoundException("Email already exists");
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User Saved Sucessfully");
    }


    public ResponseEntity<String> deleteUser(int id) {
        Optional<User> user=userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
        }
        else{
            throw new UserNotFoundException("No User Found with such id");
        }
        return ResponseEntity.status(HttpStatus.OK).body("User Deleted Sucessfully");
    }

    public ResponseEntity<String> updateUser(int id, User user) {
        Optional<User>updatedUser=userRepository.findById(id);
        if(updatedUser.isPresent()){
            User newUser=updatedUser.get();
            newUser.setName(user.getName());
            newUser.setAge(user.getAge());
            newUser.setEmail(user.getEmail());
            userRepository.save(newUser);
        }
        else{
            throw new UserNotFoundException("No User found With such id"+":"+id);
        }

        return ResponseEntity.ok("User updated successfully.");
    }

    public ResponseEntity<String> registerUser(User user) {
        Optional<User>newuser=userRepository.findByEmail(user.getEmail());
        if(newuser.isPresent()){
            throw new EmailNotFoundException("Email already exists");
        }

        String encodedPassword=passwordEncoder.encode(user.getPassword());
        User updateUser=new User();
        updateUser.setPassword(encodedPassword);
        updateUser.setRole(user.getRole());
        updateUser.setAge(user.getAge());
        updateUser.setEmail(user.getEmail());
        updateUser.setName(user.getName());
        userRepository.save(updateUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User Registration Sucessfull");

    }


    public ResponseEntity<Map<String, String>> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            UserDetails userDetails=userDetailsService.loadUserByUsername(user.getEmail());
            String jwt=jwtUtil.generateToken(userDetails);
            Map<String,String> response=new HashMap<>();
            response.put(jwt,"login Sucessfull");

            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            Map<String,String>response=new HashMap<>();
            response.put("error",e.getMessage());
            return ResponseEntity.ok(response);
        }

    }


}
