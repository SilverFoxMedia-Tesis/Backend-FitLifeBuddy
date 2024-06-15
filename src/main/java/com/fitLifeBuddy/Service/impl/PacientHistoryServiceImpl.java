package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.PacientHistory;
import com.fitLifeBuddy.Repository.IPacientHistoryRepository;
import com.fitLifeBuddy.Service.IPacientHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PacientHistoryServiceImpl implements IPacientHistoryService {
    @Autowired
    private IPacientHistoryRepository pacientHistoryRepository;

    @Override
    @Transactional
    public PacientHistory save(PacientHistory pacientHistory) throws Exception {
        return pacientHistoryRepository.save(pacientHistory);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        pacientHistoryRepository.findById(id);

    }

    @Override
    public List<PacientHistory> getAll() throws Exception {
        return pacientHistoryRepository.findAll().stream()
                .sorted((p1, p2) -> p1.getIdPacientHistory().compareTo(p2.getIdPacientHistory()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PacientHistory> getById(Long id) throws Exception {
        return pacientHistoryRepository.findById(id);
    }

    @Override
    public Optional<PacientHistory> findLatestByPacientId(Long pacientId) {
        return pacientHistoryRepository.findLatestByPacientId(pacientId);
    }
}
