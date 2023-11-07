package com.fitLifeBuddy.Repository;


import com.fitLifeBuddy.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IClientRepository extends JpaRepository<Client, Long> {
    public Client findByNumberId(Long numberId);
    public List<Client> findByName(String name);
    public List<Client> findByLastName(String lastName);
    public List<Client> findByNameAndLastName(String name, String lastName);
}
