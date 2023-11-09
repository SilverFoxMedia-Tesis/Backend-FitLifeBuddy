package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Option;
import com.fitLifeBuddy.Entity.Question;


import java.util.List;

public interface IQuestionService extends CrudService<Question>{
    public List<Question> findByNameQuestion(String nameQuestion) throws Exception;
    public List<Option> findOptionsByIdQuestion(Long idQuestion) throws Exception;
}
