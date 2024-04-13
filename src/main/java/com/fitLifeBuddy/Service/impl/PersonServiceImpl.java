package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Person;
import com.fitLifeBuddy.Repository.IPersonRepository;
import com.fitLifeBuddy.Service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements IPersonService {
    @Autowired
    private IPersonRepository personRepository;

    @Override
    @Transactional
    public Person save(Person person) throws Exception {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        personRepository.deleteById(id);

    }

    @Override
    public List<Person> getAll() throws Exception {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getById(Long id) throws Exception {
        return personRepository.findById(id);
    }

    @Override
    public List<Person> findByEmailAddress(String emailAddress) throws Exception {
        return personRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public List<Person> findByFullname(String fullname) throws Exception {
        return personRepository.findByFullname(fullname);
    }

    @Override
    public List<Person> findByLastname(String lastname) throws Exception {
        return personRepository.findByLastname(lastname);
    }
}
