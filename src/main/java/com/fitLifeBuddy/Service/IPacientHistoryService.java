package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.PacientHistory;

import java.util.Optional;

public interface IPacientHistoryService extends CrudService<PacientHistory>{
    Optional<PacientHistory> findLatestByPacientId(Long pacientId);
}
