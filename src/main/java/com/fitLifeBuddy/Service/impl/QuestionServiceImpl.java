package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Option;
import com.fitLifeBuddy.Entity.Question;
import com.fitLifeBuddy.Repository.IQuestionRepository;
import com.fitLifeBuddy.Service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class QuestionServiceImpl implements IQuestionService {
    @Autowired
    private IQuestionRepository questionRepository;

    @Override
    @Transactional
    public Question save(Question question) throws Exception {
        return questionRepository.save(question);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        questionRepository.deleteById(id);

    }

    @Override
    public List<Question> getAll() throws Exception {
        return questionRepository.findAll().stream()
                .sorted((q1, q2) -> q1.getIdQuestion().compareTo(q2.getIdQuestion()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Question> getById(Long id) throws Exception {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> findByNameQuestion(String nameQuestion) throws Exception {
        return questionRepository.findByNameQuestion(nameQuestion);
    }

    @Override
    public List<Option> findOptionsByIdQuestion(Long idQuestion) throws Exception {
        return questionRepository.findOptionsByIdQuestion(idQuestion);
    }
}
