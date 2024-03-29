package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pacient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pacient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPacient;
    @Column(name = "Height",nullable = false)
    private Float height;
    @Column(name = "Weight",nullable = false)
    private Float weight;
    @Column(name = "Gender",nullable = false,length = 10)
    private String gender;
    @Column(name = "BirthDate",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @JsonIgnore
    @OneToMany(mappedBy = "pacient", fetch = FetchType.LAZY)
    private Set<PacientHistory> pacientHistories = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "pacient", fetch = FetchType.LAZY)
    private Set<Plan> plans = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "pacient", fetch = FetchType.LAZY)
    private Set<FoodCondition> foodConditions = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "pacient", fetch = FetchType.LAZY)
    private Set<HealthCondition> healthConditions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNutritionist", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Nutritionist nutritionist;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPerson", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Person person;
}
