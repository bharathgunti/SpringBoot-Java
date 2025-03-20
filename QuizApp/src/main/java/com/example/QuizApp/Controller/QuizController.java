package com.example.QuizApp.Controller;

import com.example.QuizApp.Model.Response;
import com.example.QuizApp.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int num_que,@RequestParam String title){
            return quizService.createQuiz(category,num_que,title);

   }

   @GetMapping("get/{id}")
    public ResponseEntity<?> getQuestions(@PathVariable int id){
        return quizService.getQuestions(id);
   }

   @PostMapping("submit/{quiz_id}")
    public ResponseEntity<Integer> getScore(@PathVariable int quiz_id ,@RequestBody  List<Response> responseList){
        return quizService.getScore(quiz_id,responseList);
   }
}
