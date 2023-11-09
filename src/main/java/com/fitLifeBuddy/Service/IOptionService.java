package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Option;

import java.util.List;

public interface IOptionService extends CrudService<Option>{
    public List<Option> findByNameOption(String nameOption) throws Exception;
}
