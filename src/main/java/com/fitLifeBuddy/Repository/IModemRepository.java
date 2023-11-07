package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Modem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IModemRepository extends JpaRepository<Modem, Long> {
    public Modem findByPonSn(String ponSn);
    public List<Modem> findBySupplier(String supplier);
    public List<Modem> findByPon(Long pon);
}
