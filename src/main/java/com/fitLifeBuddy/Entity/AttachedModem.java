package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "attachedModem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachedModem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="ClientCode", nullable = false, unique = true, length = 20)
    private Long clientCode;
    @Column(name="Plan", nullable = false, length = 20)
    private String plan;
    @Column(name="Capacity", nullable = false, length = 50)
    private Long capacity;
    @Column(name="Price", nullable = false, length = 20)
    private Long price;
    @Column(name = "establishmentDate",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date establishmentDate;
    @Column(name = "billingDate",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date billingDate;

    //RELACIONES
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Client client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modem_ponSn",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Modem modem;
}
