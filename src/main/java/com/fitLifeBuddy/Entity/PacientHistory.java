package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Enum.Gender;
import com.fitLifeBuddy.Entity.Enum.PhysicalActivity;
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
    private Long idPacientHistory;
    @Column(name = "Height",nullable = false)
    private Float height;
    @Column(name = "Weight",nullable = false)
    private Float weight;
    @Enumerated(EnumType.STRING)
    @Column(name = "Gender",nullable = false,length = 10)
    private Gender gender;
    @Column(name = "Age", nullable = false)
    private Long age;
    @Column(name = "AbdominalCircumference", nullable = false)
    private Long abdominalCircumference;
    @Enumerated(EnumType.STRING)
    @Column(name = "PhysicalActivity", nullable = true,length = 12)
    private PhysicalActivity physicalActivity;
    @Enumerated(EnumType.STRING)
    @Column(name = "Frecuently", nullable = false, length = 20)
    private Frecuently frecuently;
    @Enumerated(EnumType.STRING)
    @Column(name = "DietType", nullable = false, length = 20)
    private DietType dietType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPacient", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Pacient pacient;

}
