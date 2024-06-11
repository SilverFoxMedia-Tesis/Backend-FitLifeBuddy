package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService extends CrudService<Person>{
    public List<Person> findByEmailAddress(String emailAddress)throws Exception;
    public List<Person> findByFullname(String fullname)throws Exception;
    public List<Person> findByLastname(String lastname)throws Exception;
    Optional<Person>findPersonByEmailAddress(String emailAddress) throws Exception;  // Nuevo método
}
