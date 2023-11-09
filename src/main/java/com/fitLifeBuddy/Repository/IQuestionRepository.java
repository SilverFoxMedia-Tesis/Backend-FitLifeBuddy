package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Option;
import com.fitLifeBuddy.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
    public List<Question> findByNameQuestion(String nameQuestion);
    @Query("select q.options from Question q where q.idQuestion =: questionId")
    public List<Option> findOptionsByIdQuestion(@Param("questionId")Long idQuestion);
}
