package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Option;
import com.fitLifeBuddy.Repository.IOptionRepository;
import com.fitLifeBuddy.Service.IOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OptionServiceImpl implements IOptionService {
    @Autowired
    private IOptionRepository optionRepository;

    @Override
    @Transactional
    public Option save(Option option) throws Exception {
        return optionRepository.save(option);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        optionRepository.deleteById(id);

    }

    @Override
    public List<Option> getAll() throws Exception {
        return optionRepository.findAll();
    }

    @Override
    public Optional<Option> getById(Long id) throws Exception {
        return optionRepository.findById(id);
    }

    @Override
    public List<Option> findByNameOption(String nameOption) throws Exception {
        return optionRepository.findByNameOption(nameOption);
    }
}
