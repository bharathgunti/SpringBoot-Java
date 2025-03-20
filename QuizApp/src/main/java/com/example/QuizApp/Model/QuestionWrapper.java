package com.example.QuizApp.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionWrapper {

    private int qid;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String category;

}
