package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOptionRepository extends JpaRepository<Option, Long> {
    public List<Option> findByNameOption(String nameOption);

}
