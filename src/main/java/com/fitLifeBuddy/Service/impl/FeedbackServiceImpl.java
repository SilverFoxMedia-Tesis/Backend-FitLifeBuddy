package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Feedback;
import com.fitLifeBuddy.Entity.Question;
import com.fitLifeBuddy.Repository.IFeedbackRepository;
import com.fitLifeBuddy.Service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FeedbackServiceImpl implements IFeedbackService {
    @Autowired
    private IFeedbackRepository feedbackRepository;

    @Override
    @Transactional
    public Feedback save(Feedback feedback) throws Exception {
        return feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        feedbackRepository.deleteById(id);

    }

    @Override
    public List<Feedback> getAll() throws Exception {
        return feedbackRepository.findAll().stream()
                .sorted((f1, f2) -> f1.getIdFeedback().compareTo(f2.getIdFeedback()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Feedback> getById(Long id) throws Exception {
        return feedbackRepository.findById(id);
    }

    @Override
    public List<Question> findQuestionsByIdFeedback(Long idFeedback) throws Exception {
        return feedbackRepository.findQuestionsByIdFeedback(idFeedback);
    }
}
