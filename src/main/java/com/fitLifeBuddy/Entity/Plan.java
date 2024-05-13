package com.fitLifeBuddy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitLifeBuddy.Entity.Enum.ClassificationIMC;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "TMB", nullable = false)
    private Float tmb;
    @Column(name = "Carbohydrates_g", nullable = false)
    private Float carbohydrates_g;
    @Column(name = "Proteins_g", nullable = false)
    private Float proteins_g;
    @Column(name = "Fats_g", nullable = false)
    private Float fats_g;
    @Column(name = "WeightLose", nullable = false)
    private Float weightLose;
    //lo que se va a enviar
    @Column(name = "CalorieDeficit", nullable = false)
    private Float calorieDeficit;
    //desayunos
    @Column(name = "Desayuno_carbs", nullable = false)
    private Float desayuno_carbs;
    @Column(name = "Desayuno_proten", nullable = false)
    private Float desayuno_proten;
    @Column(name = "Desayuno_grasas", nullable = false)
    private Float desayuno_grasas;
    //almuerzos
    @Column(name = "Almuerzo_carbs", nullable = false)
    private Float almuerzo_carbs;
    @Column(name = "Almuerzo_proten", nullable = false)
    private Float almuerzo_proten;
    @Column(name = "Almuerzo_grasas", nullable = false)
    private Float almuerzo_grasas;
    //cenas
    @Column(name = "Cena_carbs", nullable = false)
    private Float cena_carbs;
    @Column(name = "Cena_proten", nullable = false)
    private Float cena_proten;
    @Column(name = "Cena_grasas", nullable = false)
    private Float cena_grasas;
    //ultimos datos
    @Column(name = "AbdominalCircumference", nullable = false)
    private Float abdominal_circumference;
    @Column(name = "PhysicalActivity", nullable = false)
    private Long physical_activity;

    @Enumerated(EnumType.STRING)
    @Column(name = "ClassificationIMC", nullable = false, length = 20)
    private ClassificationIMC classificationIMC;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 10)
    private Status status = Status.ACTIVED;
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = Status.ACTIVED;
        }
    }

    @JsonIgnore
    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
    private Set<Daily> dailies = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPacient", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Pacient pacient;

}
