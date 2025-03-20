package com.example.BasicCrudOperations.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String name;

    private int age;

    private String email;

    private String password;

    private String role;

    //Custom getter
    public String getNameWithRole() {
        return name + " (" + getRole() + ")";
    }


    public String getEmailAndPassword(){
        return email+" "+getPassword()+" ";
    }


}
