package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Feedback;
import com.fitLifeBuddy.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("select fe.questions from Feedback fe where fe.idFeedback =: feedbackId")
    public List<Question> findQuestionsByIdFeedback(@Param("feedbackId")Long idFeedback);

}
