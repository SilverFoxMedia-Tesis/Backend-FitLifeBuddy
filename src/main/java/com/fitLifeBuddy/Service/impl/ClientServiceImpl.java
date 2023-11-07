package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Client;
import com.fitLifeBuddy.Repository.IClientRepository;
import com.fitLifeBuddy.Service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientRepository clientRepository;


    @Override
    @Transactional
    public Client save(Client client) throws Exception {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getAll() throws Exception {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getById(Long id) throws Exception {
        return clientRepository.findById(id);
    }

    @Override
    public Client findByNumberId(Long numberId) throws Exception {
        return clientRepository.findByNumberId(numberId);
    }

    @Override
    public List<Client> findByName(String name) throws Exception {
        return clientRepository.findByName(name);
    }

    @Override
    public List<Client> findByLastName(String lastName) throws Exception {
        return clientRepository.findByLastName(lastName);
    }

    @Override
    public List<Client> findByNameAndLastName(String name, String lastName) throws Exception {
        return clientRepository.findByNameAndLastName(name,lastName);
    }
}
