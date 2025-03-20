package com.example.QuizApp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String title;

    @ManyToMany
    private List<Question> questions;

}
