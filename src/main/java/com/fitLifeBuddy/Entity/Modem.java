package com.fitLifeBuddy.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="modem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Modem.findByPonSn",query = "select m from Modem m where m.ponSn=?1")

public class Modem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="ip",nullable = false, unique = true,length = 20)
    private String ip;
    @Column(name="ponSn",nullable = false, unique = true, length = 20)
    private String ponSn;
    @Column(name="supplier",nullable = false,length = 20)
    private String supplier;
    @Column(name="pon",nullable = false, length = 20)
    private Long pon;
}
