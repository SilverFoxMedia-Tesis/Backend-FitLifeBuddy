package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.AttachedModem;
import com.fitLifeBuddy.Repository.IAttachedModemRepository;
import com.fitLifeBuddy.Service.IAttachedModemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AttachedModemServiceImpl implements IAttachedModemService {

    @Autowired
    private IAttachedModemRepository attachedModemRepository;


    @Override
    @Transactional
    public AttachedModem save(AttachedModem attachedModem) throws Exception {
        return attachedModemRepository.save(attachedModem);
    }

    @Override
    public void delete(Long id) throws Exception {
        attachedModemRepository.deleteById(id);
    }

    @Override
    public List<AttachedModem> getAll() throws Exception {
        return attachedModemRepository.findAll();
    }

    @Override
    public Optional<AttachedModem> getById(Long id) throws Exception {
        return attachedModemRepository.findById(id);
    }

    @Override
    public AttachedModem findByClientCode(Long clientCode) throws Exception {
        return attachedModemRepository.findByClientCode(clientCode);
    }

    @Override
    public List<AttachedModem> findByPlan(String plan) throws Exception {
        return attachedModemRepository.findByPlan(plan);
    }

    @Override
    public List<AttachedModem> find(Date establishmentDate) throws Exception {
        return attachedModemRepository.find(establishmentDate);
    }
}
