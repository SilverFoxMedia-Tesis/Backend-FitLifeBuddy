package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Modem;
import com.fitLifeBuddy.Repository.IModemRepository;
import com.fitLifeBuddy.Service.IModemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ModemServiceImpl implements IModemService {

    @Autowired
    private IModemRepository modemRepository;

    @Override
    @Transactional
    public Modem save(Modem modem) throws Exception {
        return modemRepository.save(modem);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        modemRepository.deleteById(id);
    }

    @Override
    public List<Modem> getAll() throws Exception {
        return modemRepository.findAll();
    }

    @Override
    public Optional<Modem> getById(Long id) throws Exception {
        return modemRepository.findById(id);
    }

    @Override
    public Modem findByPonSn(String ponSn) throws Exception {
        return modemRepository.findByPonSn(ponSn);
    }

    @Override
    public List<Modem> findBySupplier(String supplier) throws Exception {
        return modemRepository.findBySupplier(supplier);
    }

    @Override
    public List<Modem> findByPon(Long pon) throws Exception {
        return modemRepository.findByPon(pon);
    }
}
