package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    public User findByEmailAddress(String emailAddress);
    public List<User> findByFullname(String fullname);
    public List<User> findByLastname(String lastname);
}
