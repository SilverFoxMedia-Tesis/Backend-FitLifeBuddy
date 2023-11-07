package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pacientHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacientHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPacientHistory;
    @Column(name = "LatestWeight",nullable = false)
    private Float latestWeight;
    @Column(name = "SCC",nullable = false)
    private String scc;
    @Column(name = "FAF",nullable = false)
    private Integer faf;
    @Column(name = "TUE",nullable = false)
    private Integer tue;
    @Column(name = "MTRANS",nullable = false)
    private String mtrans;
    @Column(name = "FAVC",nullable = false)
    private String favc;
    @Column(name = "FCVC",nullable = false)
    private Integer fcvc;
    @Column(name = "NCP",nullable = false)
    private Integer ncp;
    @Column(name = "CAEC",nullable = false)
    private String caec;
    @Column(name = "CH2O",nullable = false)
    private Integer ch2o;
    @Column(name = "CALC",nullable = false)
    private String calc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPacient",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Pacient pacient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlan",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Plan plan;

}
