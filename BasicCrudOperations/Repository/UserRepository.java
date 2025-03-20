package com.example.BasicCrudOperations.Repository;

import com.example.BasicCrudOperations.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
        Optional<User> findByEmail(String email);
}
