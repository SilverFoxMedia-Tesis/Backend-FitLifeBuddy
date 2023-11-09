package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Feedback;
import com.fitLifeBuddy.Entity.Question;

import java.util.List;

public interface IFeedbackService extends CrudService<Feedback>{
    public List<Question> findQuestionsByIdFeedback(Long idFeedback) throws Exception;
}
