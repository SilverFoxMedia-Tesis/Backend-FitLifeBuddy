package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.User;
import com.fitLifeBuddy.Repository.IUserRepository;
import com.fitLifeBuddy.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        userRepository.deleteById(id);

    }

    @Override
    public List<User> getAll() throws Exception {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) throws Exception {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmailAddress(String emailAddress) throws Exception {
        return userRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public List<User> findByFullname(String fullname) throws Exception {
        return userRepository.findByFullname(fullname);
    }

    @Override
    public List<User> findByLastname(String lastname) throws Exception {
        return userRepository.findByLastname(lastname);
    }
}
