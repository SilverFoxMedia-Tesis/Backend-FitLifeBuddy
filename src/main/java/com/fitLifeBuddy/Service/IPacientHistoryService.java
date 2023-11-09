package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.PacientHistory;

import java.util.List;

public interface IPacientHistoryService extends CrudService<PacientHistory>{
    public List<PacientHistory> findByMtrans(String mtrans) throws Exception;

}
