package com.example.QuizApp.Service;

import com.example.QuizApp.Model.Question;
import com.example.QuizApp.Model.QuestionWrapper;
import com.example.QuizApp.Model.Quiz;
import com.example.QuizApp.Model.Response;
import com.example.QuizApp.Repository.QuestionRepository;
import com.example.QuizApp.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService implements CommandLineRunner {

    @Autowired
    private  QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;



    @Override
    public void run(String... args) throws Exception {
        System.out.println("hi");

    }
    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);
    public  ResponseEntity<String> createQuiz(String category, int numQue, String title) {
        List<Question> questions=questionRepository.findByCategoryAndLimit(category,numQue);

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
//        System.out.println(questions);
//        System.out.println(quiz.getId());

//       Quiz obj1=Quiz.builder().id(1).title(title).questions(questions).build();

        System.out.println("hi");
//        logger.info("Questions: {}", questions);
//        logger.info("Quiz Title: {}", quiz.getTitle());
        quizRepository.save(quiz);
        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz Created");
    }

    public ResponseEntity<?> getQuestions(int id) {
//        List<Question> que=questionRepository.findAll();
        Optional<Quiz> quiz=quizRepository.findById(id);
        if (quiz.isEmpty()) {
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }
        System.out.println(quiz);
        List<Question> ques=quiz.get().getQuestions();
        List<QuestionWrapper> wrapped=new ArrayList<>();


        for(Question q:ques){
            QuestionWrapper questionWrapper=new QuestionWrapper(q.getQid(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4(),q.getCategory());
            wrapped.add(questionWrapper);
        }
        System.out.println(wrapped);

        return new ResponseEntity<>(wrapped,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(int quizId, List<Response> responseList) {
        int score=0;
        Optional<Quiz> quiz=quizRepository.findById(quizId);
        List<Question> ques=quiz.get().getQuestions();

//        int i=0;
//        for(Question q:ques){
//            if(q.getCorrect_answer().equals(responseList.get(i).getAnswer())){
//                score++;
//            }
//            i++;
//        }

        for (int i = 0; i < ques.size(); i++) {
            Question q = ques.get(i);
            if (q.getCorrect_answer() != null &&
                    q.getCorrect_answer().equals(responseList.get(i).getAnswer())) {
                System.out.println(q.getCorrect_answer()+' '+responseList.get(i).getAnswer()+" ");
                score++;
            }
            System.out.println(q.getCorrect_answer()+' '+responseList.get(i).getAnswer()+" ");
        }



        return new ResponseEntity<>(score,HttpStatus.OK);
    }
}


