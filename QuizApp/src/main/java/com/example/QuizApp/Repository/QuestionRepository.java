package com.example.QuizApp.Repository;

import com.example.QuizApp.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
//    Optional<Question> findById();
    List<Question> findByCategory(String category);

    @Query(nativeQuery = true,value="Select * from question q where q.category=:category Limit :numQue")
    List<Question> findByCategoryAndLimit(String category, int numQue);
}
