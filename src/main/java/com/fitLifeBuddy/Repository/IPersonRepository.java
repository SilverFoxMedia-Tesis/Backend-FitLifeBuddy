package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {
    public List<Person> findByEmailAddress(String emailAddress);
    public List<Person> findByFullname(String fullname);
    public List<Person> findByLastname(String lastname);
}
