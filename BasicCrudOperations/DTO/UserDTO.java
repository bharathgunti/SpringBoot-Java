package com.example.BasicCrudOperations.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int id;
    private String name;
    private int age;
    private String email;
}
