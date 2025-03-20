package com.example.BasicCrudOperations.Service;

import com.example.BasicCrudOperations.Exception.EmailNotFoundException;
import com.example.BasicCrudOperations.Model.User;
import com.example.BasicCrudOperations.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImplementaion implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        Optional<User> user=userRepository.findByEmail(email);

        if(user.isEmpty()){
            throw new EmailNotFoundException("Email Not Found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.get().getEmail())
                .password(user.get().getPassword())
                .authorities(List.of(new SimpleGrantedAuthority("Role_User")))
                .build();
    }

}
