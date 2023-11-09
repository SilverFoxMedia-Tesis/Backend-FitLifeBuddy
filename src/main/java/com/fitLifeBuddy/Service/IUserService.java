package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.User;

import java.util.List;

public interface IUserService extends CrudService<User>{
    public User findByEmailAddress(String emailAddress)throws Exception;
    public List<User> findByFullname(String fullname)throws Exception;
    public List<User> findByLastname(String lastname)throws Exception;
}
