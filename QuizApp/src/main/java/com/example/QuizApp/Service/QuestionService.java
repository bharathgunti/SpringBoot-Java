package com.example.QuizApp.Service;

import com.example.QuizApp.Model.Question;
import com.example.QuizApp.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService implements CommandLineRunner {

    @Autowired
    private  QuestionRepository questionRepository;



    @Override
    public void run(String... args) throws Exception {

//        Question obj1 = Question.builder().option1("wew").option2("2").option3("3").option4("4").question_name("History").category("easy").build();
//        questionRepository.save(obj1);
//
//        System.out.println(obj1);


    }
    public List<Question> findAll() {
        return questionRepository.findAll();
    }
//We can use a wildcard to return any type that gets returned instead of a particular datatype

    public ResponseEntity<?> getByCategory(String category){
        try{
            new ResponseEntity<>(questionRepository.findByCategory(category),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found this category: "+category);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionRepository.save(question);
        return new ResponseEntity<>("sucess ", HttpStatus.CREATED);
    }


    public String deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
        return "Question Deleted";
    }
}
