package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;
    @Enumerated(EnumType.STRING)
    @Column(name = "Frecuently", nullable = false, length = 20)
    private Frecuently frecuently;
    @Enumerated(EnumType.STRING)
    @Column(name = "DietType", nullable = false, length = 20)
    private DietType dietType;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPacient",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Pacient pacient;

}
